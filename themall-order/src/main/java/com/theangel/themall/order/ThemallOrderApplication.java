package com.theangel.themall.order;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.amqp.rabbit.annotation.EnableRabbit;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.session.data.redis.config.annotation.web.http.EnableRedisHttpSession;

@SpringBootApplication
@MapperScan("com/theangel/themall/order/dao")
@EnableDiscoveryClient
@EnableRedisHttpSession
public class ThemallOrderApplication {

    public static void main(String[] args) {
        SpringApplication.run(ThemallOrderApplication.class, args);
    }

}
