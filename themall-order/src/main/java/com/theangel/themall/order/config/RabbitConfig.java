package com.theangel.themall.order.config;

import com.rabbitmq.client.Channel;
import com.theangel.themall.order.entity.OrderEntity;
import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.annotation.EnableRabbit;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.rabbit.connection.CorrelationData;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * RabbitListener  监听消息必须加上EnableRabbit注解
 *
 * @ProjectName: theangelmall
 * @Package: com.theangel.themall.order
 * @ClassName: RabbitConfig
 * @Author: theangel
 * @Date: 2021/9/5 1:40
 */
@EnableRabbit
@Configuration
public class RabbitConfig {

    @Autowired
    RabbitTemplate rabbitTemplate;


    /**
     * 定制rabbitmq
     * 发送端确认
     *
     * @PostConstruct 在对象RabbitConfig创建完成以后，执行这个方法
     */
    @PostConstruct
    public void initRabbitTemplate() {
        RabbitTemplate.ConfirmCallback confirmCallback = new RabbitTemplate.ConfirmCallback() {
            /**
             *
             * @param correlationData 当前消息的唯一关联数据（消息的唯一的id）
             * @param ack  消息是否成功
             * @param cause 失败的原因
             */
            @Override
            public void confirm(CorrelationData correlationData, boolean ack, String cause) {
                System.out.println("confirmCallback=====>" + correlationData + "===ack:" + ack + "====cause:" + cause);
            }
        };
        rabbitTemplate.setConfirmCallback(confirmCallback);

        rabbitTemplate.setReturnCallback(new RabbitTemplate.ReturnCallback() {
            /**
             * 消息抵达的确认回调
             * 只要消息没有投递，才会触发
             * @param message 投递失败的消息详细信息
             * @param replyCode 回复的状态码
             * @param replyText 回复的文本内容
             * @param exchange  当时这个消息是发送给哪个交换机
             * @param routingKey 路由key
             */
            @Override
            public void returnedMessage(Message message, int replyCode, String replyText, String exchange, String routingKey) {
                System.out.println("message===" + message + "=====replyCode===" + replyCode + "=====replyText===" + replyText + "=====exchange===" + exchange + "=====routingKey===" + routingKey);
            }
        });
    }

    /**
     * 交换机
     * 每个微服务对应一个交换机
     *
     * @return
     */
    @Bean
    public Exchange orderEventExchange() {
        //String name, boolean durable, boolean autoDelete, Map<String, Object> arguments
        return new TopicExchange("order-event-exchange", true, false);
    }

    /**
     * 队列
     * 创建死信队列
     *
     * @return
     */
    @Bean
    public Queue orderDelayQueue() {
        //String name, boolean durable, boolean exclusive, boolean autoDelete,
        //			@Nullable Map<String, Object> arguments
        Map<String, Object> map = new HashMap<>();
        //死信交换机
        map.put("x-dead-letter-exchange", "order-event-exchange");
        //死信路由
        map.put("x-dead-letter-routing-key", "order.release.order");
        //订单过多久关闭
        map.put("x-message-ttl", 600000);
        return new Queue("order.delay.queue", true, false, false, map);
    }

    /**
     * 队列
     * 接收过期数据
     *
     * @return
     */
    @Bean
    public Queue orderReleaseOrderQueue() {
        return new Queue("order.release.order.queue", true, false, false);
    }


    /**
     * 绑定
     *
     * @return
     */
    @Bean
    public Binding orderCreateOrder() {
        //String destination, DestinationType destinationType, String exchange, String routingKey,
        //			@Nullable Map<String, Object> arguments
        return new Binding("order.delay.queue", Binding.DestinationType.QUEUE, "order-event-exchange", "order.create.order", null);
    }

    /**
     * 绑定
     *
     * @return
     */
    @Bean
    public Binding orderReleaseOrder() {
        return new Binding("order.release.order.queue", Binding.DestinationType.QUEUE, "order-event-exchange", "order.release.order", null);
    }


    /**
     * 订单释放，绑定库存释放
     *
     * @return
     */
    @Bean
    public Binding orderReleaseOtherBinding() {
        return new Binding("stock.release.stock.queue", Binding.DestinationType.QUEUE, "order-event-exchange", "order.release.other.#", null);
    }


    /**
     * 普通队列，创建秒杀订单的队列
     *
     * @return
     */
    @Bean
    public Queue seckillOrderQueue() {
        return new Queue("order.seckill.order.queue", true, false, false);
    }

    /**
     * 交换机绑定创建秒杀订单的队列
     *
     * @return
     */
    @Bean
    public Binding seckillOrderQueueBinding() {
        return new Binding("order.seckill.order.queue",
                Binding.DestinationType.QUEUE,
                "order-event-exchange",
                "order.seckill.order",
                null);
    }
}
