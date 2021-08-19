package com.theangel.themall.auth;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;

@EnableDiscoveryClient
@EnableFeignClients(basePackages = "com.theangel.themall.auth.openfeign")
@SpringBootApplication(exclude = {DataSourceAutoConfiguration.class})
public class ThemallAuthApplication {
    public static void main(String[] args) {
        SpringApplication.run(ThemallAuthApplication.class, args);
    }
}
