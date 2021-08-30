package com.theangel.themall.cart;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.session.data.redis.config.annotation.web.http.EnableRedisHttpSession;

/**
 * @ProjectName: theangelmall
 * @Package: com.theangel.themall.cart
 * @ClassName: ThemallCartApplication
 * @Author: theangel
 * @Date: 2021/8/29 12:38
 */
@EnableDiscoveryClient
@EnableRedisHttpSession
@EnableFeignClients("com.theangel.themall.cart.openfeign")
@SpringBootApplication(exclude = DataSourceAutoConfiguration.class)
public class ThemallCartApplication {
    public static void main(String[] args) {
        SpringApplication.run(ThemallCartApplication.class, args);
    }
}
