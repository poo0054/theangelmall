package com.themall.gatway;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;


/**
 * @author poo0054
 */
@SpringBootApplication
@EnableDiscoveryClient
public class ThemallGatewayApplication {
    public static void main(String[] args) {
        SpringApplication.run(ThemallGatewayApplication.class, args);
    }
}
