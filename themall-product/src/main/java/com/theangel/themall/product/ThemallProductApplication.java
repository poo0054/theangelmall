package com.theangel.themall.product;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@MapperScan("com/theangel/themall/product/dao")
@SpringBootApplication
@EnableDiscoveryClient
public class ThemallProductApplication {
    
    public static void main(String[] args) {
        SpringApplication.run(ThemallProductApplication.class, args);
    }
    
}
