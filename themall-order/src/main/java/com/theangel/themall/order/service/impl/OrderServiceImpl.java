package com.theangel.themall.order.service.impl;

import com.alibaba.fastjson.TypeReference;
import com.rabbitmq.client.Channel;
import com.theangel.common.to.MemberVo;
import com.theangel.common.to.SkuHasStockVo;
import com.theangel.common.utils.R;
import com.theangel.themall.order.entity.MqMessageEntity;
import com.theangel.themall.order.entity.OrderReturnApplyEntity;
import com.theangel.themall.order.interceptor.LoginInterceptor;
import com.theangel.themall.order.openfeign.CartService;
import com.theangel.themall.order.openfeign.MemberService;
import com.theangel.themall.order.openfeign.WareService;
import com.theangel.themall.order.vo.MemberAddressVo;
import com.theangel.themall.order.vo.OrderConfirmVo;
import com.theangel.themall.order.vo.OrderItemVo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.stream.Collectors;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.theangel.common.utils.PageUtils;
import com.theangel.common.utils.Query;

import com.theangel.themall.order.dao.OrderDao;
import com.theangel.themall.order.entity.OrderEntity;
import com.theangel.themall.order.service.OrderService;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;

@Slf4j
@Service("orderService")
@RabbitListener(queues = "hello-query")
public class OrderServiceImpl extends ServiceImpl<OrderDao, OrderEntity> implements OrderService {
    @Autowired
    MemberService memberService;
    @Autowired
    ThreadPoolExecutor poolExecutor;
    @Autowired
    CartService cartService;
    @Autowired
    WareService wareService;

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        IPage<OrderEntity> page = this.page(
                new Query<OrderEntity>().getPage(params),
                new QueryWrapper<OrderEntity>()
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

        //用户积分信息
        orderConfirmVo.setIntegration(memberVo.getIntegration());

        CompletableFuture.allOf(future1, future).get();

        //TODO  防重令牌


        return orderConfirmVo;
    }

    /**
     * @param message         可以用Message：原生消息详细信息
     *                        object：和上类似
     * @param mqMessageEntity 发送的是什么类型，接收用什么类型就可以
     * @param channel         当前传输数据的通道
     * @RabbitListener 监听消息
     * <p>
     * <p>
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