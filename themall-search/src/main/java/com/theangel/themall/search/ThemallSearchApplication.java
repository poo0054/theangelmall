package com.theangel.themall.search;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;

@EnableDiscoveryClient
@SpringBootApplication(exclude = DataSourceAutoConfiguration.class)
@EnableFeignClients(value = "com.theangel.themall.search.openfeign")
public class ThemallSearchApplication {
    public static void main(String[] args) {
        SpringApplication.run(ThemallSearchApplication.class, args);
    }
}
