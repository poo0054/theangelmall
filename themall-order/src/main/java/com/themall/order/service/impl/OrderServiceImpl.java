package com.themall.order.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.IdWorker;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.rabbitmq.client.Channel;
import com.themall.common.constant.OrderConstant;
import com.themall.common.to.MemberVo;
import com.themall.common.to.SkuHasStockVo;
import com.themall.common.to.mq.OrderTo;
import com.themall.common.to.mq.SeckillOrderTo;
import com.themall.common.utils.PageUtils;
import com.themall.common.utils.Query;
import com.themall.common.utils.R;
import com.themall.common.utils.fileutils.UUIDUtils;
import com.themall.order.dao.OrderDao;
import com.themall.order.entity.*;
import com.themall.order.enume.OrderStatusEnum;
import com.themall.order.interceptor.LoginInterceptor;
import com.themall.order.openfeign.CartFeignService;
import com.themall.order.openfeign.MemberFeignService;
import com.themall.order.openfeign.ProductFeignService;
import com.themall.order.openfeign.WareFeignService;
import com.themall.order.service.OrderItemService;
import com.themall.order.service.OrderService;
import com.themall.order.service.PaymentInfoService;
import com.themall.order.to.FareVo;
import com.themall.order.to.OrderCreateTo;
import com.themall.order.to.SpuInfoTo;
import com.themall.order.to.WareSkuLockTo;
import com.themall.order.vo.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.AmqpException;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.script.DefaultRedisScript;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;

import java.io.IOException;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

@Slf4j
@Service("orderService")
//@RabbitListener(queues = "hello-query")
public class OrderServiceImpl extends ServiceImpl<OrderDao, OrderEntity> implements OrderService {
    @Autowired
    MemberFeignService memberService;
    @Autowired
    ThreadPoolExecutor poolExecutor;
    @Autowired
    CartFeignService cartService;
    @Autowired
    WareFeignService wareFeignService;
    @Autowired
    StringRedisTemplate redisTemplate;
    @Autowired
    ProductFeignService productFeignService;
    @Autowired
    OrderItemService orderItemService;
    @Autowired
    RabbitTemplate rabbitTemplate;
    @Autowired
    PaymentInfoService paymentInfoService;


    /**
     * 根据订单id，查询订单
     *
     * @param orderSn
     * @return
     */
    @Override
    public OrderEntity getOrderStockByOrderSn(String orderSn) {
        return this.getOne(new QueryWrapper<OrderEntity>().eq("order_sn", orderSn));
    }

    /**
     * 关闭订单
     *
     * @param orderEntity
     */
    @Override
    public void orderClose(OrderEntity orderEntity) {
        Long id = orderEntity.getId();
        //查询当前订单状态
        OrderEntity byId = this.getById(id);
        if (OrderStatusEnum.CREATE_NEW.getCode().equals(byId.getStatus())) {
            OrderEntity orderEntity1 = new OrderEntity();
            orderEntity1.setId(id);
            orderEntity1.setStatus(OrderStatusEnum.CANCLED.getCode());
            this.updateById(orderEntity1);
            OrderTo orderTo = new OrderTo();
            BeanUtils.copyProperties(orderEntity, orderTo);
            //订单解锁成功，发送给库存
            try {
                //保证消息一定发送,每一个消息，都做好日志记录
                rabbitTemplate.convertAndSend("order-event-exchange", "order.release.other", orderTo);
            } catch (AmqpException e) {
                e.printStackTrace();
                //重试，定时扫描mq中失败的日志，重新发送
            }
        }
    }

    /**
     * 获取支付信息
     *
     * @param orderSn
     * @return
     */
    @Override
    public PayVo payOrder(String orderSn) {
        OrderEntity orderStockByOrderSn = this.getOrderStockByOrderSn(orderSn);
        BigDecimal bigDecimal = orderStockByOrderSn.getPayAmount().setScale(2, RoundingMode.UP);
        PayVo payVo = new PayVo();
        payVo.setBody("theangel支付信息");
        payVo.setOut_trade_no(orderSn);
        payVo.setSubject("备注");
        payVo.setTotal_amount(bigDecimal.toString());
        return payVo;
    }

    /**
     * 查询当前登录的所有订单
     *
     * @param params
     * @return
     */
    @Override
    public PageUtils listWithItem(Map<String, Object> params) {
        MemberVo memberVo = LoginInterceptor.threadLocal.get();

        IPage<OrderEntity> page = this.page(
                new Query<OrderEntity>().getPage(params),
                new QueryWrapper<OrderEntity>().eq("member_id", memberVo.getId()).orderByDesc("id"));

        List<OrderEntity> collect = page.getRecords().stream().map(item -> {
            List<OrderItemEntity> order_sn = orderItemService.list(new QueryWrapper<OrderItemEntity>().eq("order_sn", item.getOrderSn()));
            item.setItemEntities(order_sn);
            return item;
        }).collect(Collectors.toList());

        page.setRecords(collect);
        return new PageUtils(page);
    }


    /**
     * 下单
     * 使用分布式事务： 最大原因 -》网络问题
     * 使用seata的at模式不适合这种大并发  at类似于pc2
     * tcc：最终一致性，可能某个节点在一段时间不一致，只要最后一致 不推荐
     * 柔性事务： 最大努力通知，如果失败了，一直给自己编写的失败回滚方法发送通知，到达指定的通知量或者自己写的方法回调了，通知我接收到了就不发送了  例如支付宝的充值，一直发送充值成功，等你回调后，就不发送   使用mq
     * 柔性事务：可靠消息加最终一致性。。。业务向消息服务发送通知，不做任何处理，等待mq来做处理，反应快。  类似数据库中加一个处理中     使用mq  推荐
     *
     * @param vo
     * @Transactional:是一个本地事务
     */
//    @GlobalTransactional
    @Override
    @Transactional(rollbackFor = Exception.class)
    public SubmitResponseVo submitOrder(OrderSubmitVo vo) {
        SubmitResponseVo responseVo = new SubmitResponseVo();
        MemberVo memberVo = LoginInterceptor.threadLocal.get();
        //创建订单  锁库存  验证防重复
        // 验证防重复
        String orderToken = vo.getOrderToken();
        //验证防重复通过  应该是一个原子操作，这样会有误差
//         String s = redisTemplate.opsForValue().get(OrderConstant.USER_ORDER_TOKEN_PREFIX + memberVo.getId());
      /*  if (!ObjectUtils.isEmpty(s) && orderToken.equals(s)) {
            //删除令牌
            redisTemplate.delete(OrderConstant.USER_ORDER_TOKEN_PREFIX + memberVo.getId());
        }*/
        //应该是一个脚本  原子操作   原子验证和删除
        String script = "if redis.call('get',KEYS[1]) == ARGV[1] then return redis.call('del',KEYS[1]) else return 0 end";
        Long execute = redisTemplate.execute(new DefaultRedisScript<Long>(script, Long.class),
                Arrays.asList(OrderConstant.USER_ORDER_TOKEN_PREFIX + memberVo.getId()),
                orderToken);
        if (0 == execute) {
            //令牌验证失败
            responseVo.setCode(1);
            return responseVo;
        }
        //创建订单和订单项 信息
        OrderCreateTo orderCreateTo = orderCreateTo(vo);
        //自己生成的价格
        BigDecimal payAmount = orderCreateTo.getOrder().getPayAmount();
        //页面提交的价格
        BigDecimal payPrice = vo.getPayPrice();
        //只要价格不超过0.01  超过0.01就是无效的
        if (Math.abs(payPrice.subtract(payAmount).doubleValue()) > 0.01) {
            responseVo.setCode(2);
            return responseVo;
        }
        //存入数据库
        saveOrder(orderCreateTo);
        //锁定库存
        // 需要订单号  skuid  skuname  多少库存
        WareSkuLockTo wareSkuLockTo = new WareSkuLockTo();
        wareSkuLockTo.setOrderSn(orderCreateTo.getOrder().getOrderSn());
        List<OrderItemVo> collect = orderCreateTo.getOrderItems().stream().map(item -> {
            OrderItemVo orderItemVo = new OrderItemVo();
            orderItemVo.setSkuId(item.getSkuId());
            orderItemVo.setCount(item.getSkuQuantity());
            orderItemVo.setTitle(item.getSkuName());
            return orderItemVo;
        }).collect(Collectors.toList());
        wareSkuLockTo.setLocks(collect);
        //远程锁库存
        /**
         *  为了保证高并发，库存自己回滚   给mq发送消息，让mq去把刚刚锁定的库存解锁
         * 也可以让库存自动解锁，
         */
        R r = wareFeignService.OrderLock(wareSkuLockTo);
        if (r.isSuccess()) {
            //锁定失败
            responseVo.setCode(3);
            return responseVo;
        }

        responseVo.setOrderEntity(orderCreateTo.getOrder());
        responseVo.setCode(0);
        //订单创建成功，发送消息给mq
        rabbitTemplate.convertAndSend("order-event-exchange", "order.create.order", orderCreateTo.getOrder());
        return responseVo;
    }

    /**
     * 保存订单
     *
     * @param orderCreateTo
     */
    @Transactional(rollbackFor = Exception.class)
    public void saveOrder(OrderCreateTo orderCreateTo) {
        OrderEntity order = orderCreateTo.getOrder();
        order.setModifyTime(new Date());

        MemberVo memberVo = LoginInterceptor.threadLocal.get();
        order.setMemberId(memberVo.getId());
        this.save(order);

        List<OrderItemEntity> orderItems = orderCreateTo.getOrderItems();
        orderItemService.saveBatch(orderItems);

    }

    /**
     * 创建订单
     * 里面有订单基本信息 收货人  邮费
     * 每个订单都有订单项
     *
     * @return
     */
    private OrderCreateTo orderCreateTo(OrderSubmitVo vo) {
        OrderCreateTo orderCreateTo = new OrderCreateTo();

        //构建订单基本信息，收货人  邮费
        OrderEntity orderEntity = buildOrder(vo);
        orderCreateTo.setOrder(orderEntity);

        //生成订单项  获取所有的订单项
        List<OrderItemEntity> orderItemEntities = buildOrderItems(orderEntity.getOrderSn());
        orderCreateTo.setOrderItems(orderItemEntities);

        // 验价  计算价格
        computePrice(orderEntity, orderItemEntities);

        return orderCreateTo;
    }

    /**
     * 计算价格
     *
     * @param orderEntity
     * @param orderItemEntities
     */
    private void computePrice(OrderEntity orderEntity, List<OrderItemEntity> orderItemEntities) {
        BigDecimal total = new BigDecimal(0);
        BigDecimal coupon = new BigDecimal(0);
        BigDecimal integration = new BigDecimal(0);
        BigDecimal promotion = new BigDecimal(0);

        BigDecimal gift = new BigDecimal(0);
        BigDecimal growth = new BigDecimal(0);


        for (OrderItemEntity orderItemEntity : orderItemEntities) {
            total = total.add(orderItemEntity.getRealAmount());
            coupon = coupon.add(orderItemEntity.getCouponAmount());
            integration = integration.add(orderItemEntity.getIntegrationAmount());
            promotion = promotion.add(orderItemEntity.getPromotionAmount());
            gift = gift.add(new BigDecimal(orderItemEntity.getGiftIntegration()));
            growth = growth.add(new BigDecimal(orderItemEntity.getGiftGrowth()));
        }
        //减去优惠的总额
        orderEntity.setTotalAmount(total);
        //应付金额
        orderEntity.setPayAmount(total.add(orderEntity.getFreightAmount()));

        //总优惠
        orderEntity.setPromotionAmount(promotion);
        orderEntity.setIntegrationAmount(integration);
        orderEntity.setCouponAmount(coupon);

        //积分  成长值
        orderEntity.setGrowth(growth.intValue());
        orderEntity.setIntegration(gift.intValue());

        //默认删除状态为0  未删除
        orderEntity.setDeleteStatus(0);

    }


    /**
     * 构建所有订单项
     *
     * @return
     */
    private List<OrderItemEntity> buildOrderItems(String orderSn) {
        R cartItem = cartService.getCartItem();
        if (cartItem.isSuccess()) {
            //所有购物项
            List<OrderItemVo> data = cartItem.getData(new TypeReference<List<OrderItemVo>>() {
            });
            List<OrderItemEntity> collect = data.stream().map(item -> {
                //构建出所有订单项
                OrderItemEntity orderItemEntity = buildOrderItem(item);
                orderItemEntity.setOrderSn(orderSn);
                return orderItemEntity;
            }).collect(Collectors.toList());

            return collect;
        }
        return null;
    }

    /**
     * 构建每一个订单项    每一个商品都有一个订单项
     *
     * @param orderItemVo
     * @return
     */
    private OrderItemEntity buildOrderItem(OrderItemVo orderItemVo) {
        OrderItemEntity orderItemEntity = new OrderItemEntity();
        //spu信息
        Long skuId = orderItemVo.getSkuId();
        R spuInfo = productFeignService.getSpuInfo(skuId);
        if (spuInfo.isSuccess()) {
            SpuInfoTo data = spuInfo.getData(new TypeReference<SpuInfoTo>() {
            });
            if (!ObjectUtils.isEmpty(data)) {
                orderItemEntity.setSpuId(data.getId());
                orderItemEntity.setSpuBrand(data.getBrandId().toString());
                orderItemEntity.setSpuName(data.getSpuName());
                orderItemEntity.setCategoryId(data.getCatalogId());
            }
        }

        //sku信息
        orderItemEntity.setSkuId(orderItemVo.getSkuId());
        orderItemEntity.setSkuName(orderItemVo.getTitle());
        orderItemEntity.setSkuPrice(orderItemVo.getPrice());
        orderItemEntity.setSkuPic(orderItemVo.getImage());
        String skuAttr = StringUtils.collectionToDelimitedString(orderItemVo.getSkuAttr(), ";");
        orderItemEntity.setSkuAttrsVals(skuAttr);
        orderItemEntity.setSkuQuantity(orderItemVo.getCount());

        //TODO 优惠信息

        int i = orderItemVo.getPrice().multiply(new BigDecimal(orderItemVo.getCount())).intValue();
        //积分信息
        //多少钱送多少积分
        orderItemEntity.setGiftGrowth(i);
        orderItemEntity.setGiftIntegration(i);


        //订单项的价格信息
        orderItemEntity.setIntegrationAmount(new BigDecimal(0));
        orderItemEntity.setPromotionAmount(new BigDecimal(0));
        orderItemEntity.setCouponAmount(new BigDecimal(0));

        //单价*数量
        BigDecimal multiply = orderItemEntity.getSkuPrice().multiply(new BigDecimal(orderItemEntity.getSkuQuantity()));
        //总价减去优惠信息
        BigDecimal subtract = multiply.subtract(orderItemEntity.getCouponAmount()).subtract(orderItemEntity.getPromotionAmount()).subtract(orderItemEntity.getIntegrationAmount());
        orderItemEntity.setRealAmount(subtract);


        return orderItemEntity;
    }

    /**
     * 构建一个订单基本信息
     * 构建订单基本信息，收货人  邮费
     *
     * @param vo
     * @return
     */
    private OrderEntity buildOrder(OrderSubmitVo vo) {
        OrderEntity orderEntity = new OrderEntity();
        //订单号
        String orderSn = IdWorker.getTimeId();
        orderEntity.setOrderSn(orderSn);
        //获取收货信息
        R fare = wareFeignService.getFare(vo.getAddrId());
        if (fare.isSuccess()) {
            FareVo data = fare.getData(new TypeReference<FareVo>() {
            });
            //邮费
            orderEntity.setFreightAmount(data.getFare());
            //收货人信息
            orderEntity.setReceiverCity(data.getMemberReceiveAddressVo().getCity());
            orderEntity.setReceiverDetailAddress(data.getMemberReceiveAddressVo().getDetailAddress());
            orderEntity.setReceiverName(data.getMemberReceiveAddressVo().getName());
            orderEntity.setReceiverPhone(data.getMemberReceiveAddressVo().getPhone());
            orderEntity.setReceiverPostCode(data.getMemberReceiveAddressVo().getPostCode());
            orderEntity.setReceiverProvince(data.getMemberReceiveAddressVo().getProvince());
            orderEntity.setReceiverRegion(data.getMemberReceiveAddressVo().getRegion());
        }

        //订单状态
        orderEntity.setStatus(OrderStatusEnum.CREATE_NEW.getCode());

        orderEntity.setAutoConfirmDay(7);

        return orderEntity;
    }


    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        IPage<OrderEntity> page = this.page(
                new Query<OrderEntity>().getPage(params),
                new QueryWrapper<>()
        );
        return new PageUtils(page);
    }

    /**
     * 订单确认页展示的数据
     *
     * @return
     */
    @Override
    public OrderConfirmVo confirmOrder() throws ExecutionException, InterruptedException {
        MemberVo memberVo = LoginInterceptor.threadLocal.get();
        //获取request上下，放入每个线程中
        RequestAttributes requestAttributes = RequestContextHolder.getRequestAttributes();
        OrderConfirmVo orderConfirmVo = new OrderConfirmVo();

        //远程查询收货地址
        CompletableFuture<Void> future1 = CompletableFuture.runAsync(() -> {
            RequestContextHolder.setRequestAttributes(requestAttributes);
            R address = memberService.getAddress(memberVo.getId());
            if (address.isSuccess()) {
                List<MemberAddressVo> data = address.getData(new TypeReference<List<MemberAddressVo>>() {
                });
                orderConfirmVo.setAddress(data);
            }
        }, poolExecutor);

        //远程查询购物项
        CompletableFuture<Void> future = CompletableFuture.runAsync(() -> {
            RequestContextHolder.setRequestAttributes(requestAttributes);
            R address = cartService.getCartItem();
            if (address.isSuccess()) {
                List<OrderItemVo> data = address.getData(new TypeReference<List<OrderItemVo>>() {
                });
                orderConfirmVo.setItem(data);
            }
        }, poolExecutor).thenRunAsync(() -> {
            List<OrderItemVo> itemVos = orderConfirmVo.getItem();
            List<Long> collect = itemVos.stream().map(item -> item.getSkuId()).collect(Collectors.toList());
            R hasStock = wareFeignService.getHasStock(collect);
            if (hasStock.isSuccess()) {
                List<SkuHasStockVo> data = hasStock.getData(new TypeReference<List<SkuHasStockVo>>() {
                });
                Map<Long, Boolean> collect1 = data.stream().collect(Collectors.toMap((SkuHasStockVo::getSkuId), (SkuHasStockVo::getHasStock)));
                orderConfirmVo.setStocks(collect1);
            }
        }, poolExecutor);

        // 防重令牌
        CompletableFuture<Void> future2 = CompletableFuture.runAsync(() -> {
            String uuid = UUIDUtils.getUUID().replace("-", "future2");
            redisTemplate.opsForValue().set(OrderConstant.USER_ORDER_TOKEN_PREFIX + memberVo.getId(), uuid, 30, TimeUnit.MINUTES);
            orderConfirmVo.setOrderToken(uuid);
        });

        //用户积分信息
        orderConfirmVo.setIntegration(memberVo.getIntegration());
        CompletableFuture.allOf(future1, future, future2).get();

        return orderConfirmVo;
    }


    /**
     * @param message
     * @param mqMessageEntity
     * @param channel
     * @RabbitListener 监听消息
     * 场景一：
     * message         可以用Message：原生消息详细信息
     * mqMessageEntity 发送的是什么类型，接收用什么类型就可以
     * channel         当前传输数据的通道
     * <p>
     * RabbitListener可以在类和方法上
     * RabbitHandler 只能标注在方法上
     * 在类上使用RabbitListener监听多个队列
     * RabbitHandler方法重载，接收不同的对象（实体类）  ，每个类上标注注解  重载，获取不同的对象
     * <p>
     * 以后使用：
     * RabbitListener监听不同的队列
     * RabbitHandler重载区分不同的方法
     * <p>
     * 场景二：
     * Queue:很多人监听，只能有一个人接收到消息，接收到就删除
     * 以上是自动确认
     * 问题：收到很多消息，都自动回复，只有一个消息处理成功。所有消息都会确认（消息丢失）
     * 手动确认：ack
     * 开启yml配置中开启listener.simple.acknowledge-mode=manual  手动确认
     */
    @RabbitHandler
    public void RabbitListener(Message message, MqMessageEntity mqMessageEntity, Channel channel) {
        //获取消息体
        log.info("内容" + mqMessageEntity);
        log.info("监听的消息为==========》" + message + "内容" + mqMessageEntity);
        log.info("内容" + channel);
        /**
         * 第二个参数：是否批量模式
         */
        try {
            channel.basicAck(message.getMessageProperties().getDeliveryTag(), false);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @RabbitHandler
    public void RabbitListener1(OrderReturnApplyEntity mqMessageEntity, Channel channel) {
        //获取消息体
        log.info("内容==========》" + mqMessageEntity);
    }

    /**
     * 支付宝回调
     *
     * @param payAsyncVo
     */
    @Override
    @Transactional
    public String handleAliPay(PayAsyncVo payAsyncVo) {
        //查询的订单状态已经修改了就无须修改
        //保存交易流水 oms_payment_info
        buildPayInfo(payAsyncVo);
        //修改订单状态
        String tradeStatus = payAsyncVo.getTrade_status();
        //TODO 把锁定的库存 减去真实库存，该订单完成
        if (StringUtils.isEmpty(tradeStatus)) {
            String tradeNo = payAsyncVo.getOut_trade_no();
            System.out.println("当前需要修改的订单为：" + tradeNo);
            this.baseMapper.updateOrderStatus(tradeNo, OrderStatusEnum.PAYED.getCode());
        } else {
            if (tradeStatus.equals("TRADE_SUCCESS") || ("TRADE_SUCCESS").equals(tradeStatus)) {
                String tradeNo = payAsyncVo.getTrade_no();
                System.out.println("当前需要修改的订单为：" + tradeNo);
                this.baseMapper.updateOrderStatus(tradeNo, OrderStatusEnum.PAYED.getCode());
            }
        }
        return "success";
    }


    public void buildPayInfo(PayAsyncVo payAsyncVo) {

        PaymentInfoEntity order_sn = paymentInfoService.getOne(
                new QueryWrapper<PaymentInfoEntity>().eq("order_sn", payAsyncVo.getOut_trade_no()));
        if (ObjectUtils.isEmpty(order_sn)) {
            PaymentInfoEntity paymentInfoEntity = new PaymentInfoEntity();
            paymentInfoEntity.setOrderSn(payAsyncVo.getOut_trade_no());
            paymentInfoEntity.setTotalAmount(new BigDecimal(payAsyncVo.getTotal_amount()));
            paymentInfoEntity.setAlipayTradeNo(payAsyncVo.getTrade_no());
            paymentInfoEntity.setPaymentStatus(OrderStatusEnum.PAYED.getMsg());
            paymentInfoEntity.setCreateTime(new Date());
            paymentInfoEntity.setConfirmTime(new Date());
            paymentInfoEntity.setCallbackTime(new Date());
            paymentInfoEntity.setCallbackContent(JSON.toJSONString(payAsyncVo));
            paymentInfoService.save(paymentInfoEntity);
        }

    }


    @Override
    public void createSeckillOrder(SeckillOrderTo seckillOrderTo) {
        //报错订单
        OrderEntity orderEntity = new OrderEntity();
        orderEntity.setOrderSn(seckillOrderTo.getOrderSn());
        orderEntity.setMemberId(seckillOrderTo.getMemberId());
        orderEntity.setStatus(OrderStatusEnum.CREATE_NEW.getCode());
        BigDecimal multiply = seckillOrderTo.getSeckillPrice().multiply(new BigDecimal(seckillOrderTo.getNum()));
        orderEntity.setPayAmount(multiply);
        save(orderEntity);

        //报错订单项
        OrderItemEntity orderItemEntity = new OrderItemEntity();
        orderItemEntity.setOrderSn(seckillOrderTo.getOrderSn());
        orderItemEntity.setRealAmount(multiply);
        orderItemEntity.setSkuQuantity(seckillOrderTo.getNum());
        orderItemEntity.setSkuId(seckillOrderTo.getSkuId());
        //TODO 获取sku信息

        orderItemService.save(orderItemEntity);

    }
}