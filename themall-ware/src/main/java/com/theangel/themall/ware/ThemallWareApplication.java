package com.theangel.themall.ware;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication
@MapperScan("com.theangel.themall.ware.dao")
@EnableDiscoveryClient
public class ThemallWareApplication {
    
    public static void main(String[] args) {
        SpringApplication.run(ThemallWareApplication.class, args);
    }
    
}
