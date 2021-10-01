package com.theangel.themall.ware.config;

import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.annotation.EnableRabbit;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

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

    @Bean
    public MessageConverter messageConverter() {
        return new Jackson2JsonMessageConverter();
    }

    /**
     * 每个微服务对应一个交换机
     *
     * @return
     */
    @Bean
    public Exchange stockEventExchange() {
        //String name, boolean durable, boolean autoDelete, Map<String, Object> arguments
        return new TopicExchange("stock-event-exchange", true, false);
    }

    /**
     * 队列
     *
     * @return
     */
    @Bean
    public Queue stockReleaseStockQueue() {
        return new Queue("stock.release.stock.queue", true, false, false);
    }

    /**
     * 队列
     * 延时队列
     *
     * @return
     */
    @Bean
    public Queue stockDelayQueue() {
        //String name, boolean durable, boolean exclusive, boolean autoDelete,
        //			@Nullable Map<String, Object> arguments
        Map<String, Object> map = new HashMap<>();
        //死信交换机
        map.put("x-dead-letter-exchange", "stock-event-exchange");
        //死信路由
        map.put("x-dead-letter-routing-key", "stock.release");
        map.put("x-message-ttl", 120000);
        return new Queue("stock.delay.queue", true, false, false, map);
    }


    /**
     * 绑定
     *
     * @return
     */
    @Bean
    public Binding stockReleaseBinding() {
        //String destination, DestinationType destinationType, String exchange, String routingKey,
        //			@Nullable Map<String, Object> arguments
        return new Binding("stock.release.stock.queue", Binding.DestinationType.QUEUE, "stock-event-exchange", "stock.release.#", null);
    }


    /**
     * 绑定死信队列
     *
     * @return
     */
    @Bean
    public Binding stockLockBinding() {
        return new Binding("stock.delay.queue", Binding.DestinationType.QUEUE, "stock-event-exchange", "stock.locked", null);
    }

}
