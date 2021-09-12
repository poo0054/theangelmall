package com.theangel.themall.order.service.impl;

import com.alibaba.fastjson.TypeReference;
import com.baomidou.mybatisplus.core.toolkit.IdWorker;
import com.rabbitmq.client.Channel;
import com.theangel.common.constant.OrderConstant;
import com.theangel.common.to.MemberVo;
import com.theangel.common.to.SkuHasStockVo;
import com.theangel.common.utils.R;
import com.theangel.common.utils.fileutils.UUIDUtils;
import com.theangel.themall.order.entity.MqMessageEntity;
import com.theangel.themall.order.entity.OrderItemEntity;
import com.theangel.themall.order.entity.OrderReturnApplyEntity;
import com.theangel.themall.order.enume.OrderStatusEnum;
import com.theangel.themall.order.interceptor.LoginInterceptor;
import com.theangel.themall.order.openfeign.CartFeignService;
import com.theangel.themall.order.openfeign.MemberFeignService;
import com.theangel.themall.order.openfeign.ProductFeignService;
import com.theangel.themall.order.openfeign.WareFeignService;
import com.theangel.themall.order.to.FareVo;
import com.theangel.themall.order.to.OrderCreateTo;
import com.theangel.themall.order.to.SpuInfoTo;
import com.theangel.themall.order.vo.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.script.DefaultRedisScript;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.theangel.common.utils.PageUtils;
import com.theangel.common.utils.Query;

import com.theangel.themall.order.dao.OrderDao;
import com.theangel.themall.order.entity.OrderEntity;
import com.theangel.themall.order.service.OrderService;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;

@Slf4j
@Service("orderService")
@RabbitListener(queues = "hello-query")
public class OrderServiceImpl extends ServiceImpl<OrderDao, OrderEntity> implements OrderService {
    @Autowired
    MemberFeignService memberService;
    @Autowired
    ThreadPoolExecutor poolExecutor;
    @Autowired
    CartFeignService cartService;
    @Autowired
    WareFeignService wareService;
    @Autowired
    StringRedisTemplate redisTemplate;
    @Autowired
    ProductFeignService productFeignService;

    /**
     * 下单
     *
     * @param vo
     */
    @Override
    public SubmitResultVo submitOrder(OrderSubmitVo vo) {
        SubmitResultVo submitResultVo = new SubmitResultVo();
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
            submitResultVo.setCode(1);
            return submitResultVo;
        }
        //创建订单  订单项
        OrderCreateTo orderCreateTo = orderCreateTo(vo);
        BigDecimal payAmount = orderCreateTo.getOrder().getPayAmount();
        BigDecimal payPrice = vo.getPayPrice();
        //只要价格不超过0.01
        if (Math.abs(payPrice.subtract(payAmount).doubleValue()) > 0.01) {
            submitResultVo.setCode(2);
            return submitResultVo;
        }


        return submitResultVo;

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
        if (0 == cartItem.getCode()) {
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
        if (0 == spuInfo.getCode()) {
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
        R fare = wareService.getFare(vo.getAddrId());
        if (0 == fare.getCode()) {
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
            if (address.getCode() == 0) {
                List<MemberAddressVo> data = address.getData(new TypeReference<List<MemberAddressVo>>() {
                });
                orderConfirmVo.setAddress(data);
            }
        }, poolExecutor);

        //远程查询购物项
        CompletableFuture<Void> future = CompletableFuture.runAsync(() -> {
            RequestContextHolder.setRequestAttributes(requestAttributes);
            R address = cartService.getCartItem();
            if (address.getCode() == 0) {
                List<OrderItemVo> data = address.getData(new TypeReference<List<OrderItemVo>>() {
                });
                orderConfirmVo.setItem(data);
            }
        }, poolExecutor).thenRunAsync(() -> {
            List<OrderItemVo> itemVos = orderConfirmVo.getItem();
            List<Long> collect = itemVos.stream().map(item -> {
                return item.getSkuId();
            }).collect(Collectors.toList());
            R hasStock = wareService.getHasStock(collect);
            if (hasStock.getCode() == 0) {
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
     * <p>
     * <p>
     * <p>
     * 以后使用：
     * RabbitListener监听不同的队列
     * RabbitHandler重载区分不同的方法
     * <p>
     * <p>
     * <p>
     * 场景二：
     * Queue:很多人监听，只能有一个人接收到消息，接收到就删除
     * 以上是自动确认
     * 问题：收到很多消息，都自动回复，只有一个消息处理成功。所有消息都会确认（消息丢失）
     * 手动确认：ack
     * 开启listener.simple.acknowledge-mode=manual  手动确认
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


}