package com.themall.order.config;

import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @ProjectName: theangelmall
 * @Package: com.theangel.themall.order.config
 * @ClassName: InitRabbitConfig
 * @Author: theangel
 * @Date: 2021/10/11 0:38
 */
@Configuration
public class RabbitmessageConverterConfig {

    /**
     * 系列化mq
     *
     * @return
     */
    @Bean
    public MessageConverter messageConverter() {
        return new Jackson2JsonMessageConverter();
    }
}
