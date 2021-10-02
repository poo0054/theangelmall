package com.theangel.themall.order.listener;

import com.rabbitmq.client.Channel;
import com.theangel.themall.order.entity.OrderEntity;
import com.theangel.themall.order.service.OrderService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;

/**
 * 订单关单功能
 *
 * @ProjectName: theangelmall
 * @Package: com.theangel.themall.order.listener
 * @ClassName: OrderCloseListener
 * @Author: theangel
 * @Date: 2021/9/24 23:05
 */
@Slf4j
@Service
@RabbitListener(queues = "order.release.order.queue")
public class OrderCloseListener {

    @Autowired
    OrderService orderService;

    @RabbitHandler
    public void listener(OrderEntity orderEntity, Channel channel, Message message) throws IOException {
        try {
            log.info("收到过期的订单，准备关闭订单===========" + orderEntity.toString());
            //TODO 订单过期，手动调用支付宝的关单功能
            orderService.orderClose(orderEntity);
            channel.basicAck(message.getMessageProperties().getDeliveryTag(), false);

        } catch (Exception e) {
            e.printStackTrace();
            channel.basicReject(message.getMessageProperties().getDeliveryTag(), true);
        }
    }
}
