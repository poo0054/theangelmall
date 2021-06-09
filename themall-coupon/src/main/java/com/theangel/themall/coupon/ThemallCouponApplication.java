package com.theangel.themall.coupon;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan("com/theangel/themall/coupon/dao")
public class ThemallCouponApplication {
    
    public static void main(String[] args) {
        SpringApplication.run(ThemallCouponApplication.class, args);
    }
    
}
