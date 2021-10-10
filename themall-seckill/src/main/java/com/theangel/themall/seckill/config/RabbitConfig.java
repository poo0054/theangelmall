package com.theangel.themall.seckill.config;

import io.netty.util.internal.UnstableApi;
import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.annotation.EnableRabbit;
import org.springframework.amqp.rabbit.connection.CorrelationData;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.annotation.PostConstruct;
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

}
