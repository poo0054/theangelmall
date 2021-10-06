package com.theangel.themall.coupon;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication
@MapperScan("com/theangel/themall/coupon/dao")
@EnableDiscoveryClient
public class ThemallCouponApplication {

    public static void main(String[] args) {
        SpringApplication.run(ThemallCouponApplication.class, args);
    }

}
