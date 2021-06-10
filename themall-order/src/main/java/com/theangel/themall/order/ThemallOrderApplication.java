package com.theangel.themall.order;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication
@MapperScan("com/theangel/themall/order/dao")
@EnableDiscoveryClient
public class ThemallOrderApplication {
    
    public static void main(String[] args) {
        SpringApplication.run(ThemallOrderApplication.class, args);
    }
    
}
