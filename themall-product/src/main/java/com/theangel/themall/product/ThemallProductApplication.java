package com.theangel.themall.product;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@MapperScan("com/theangel/themall/product/dao")
@SpringBootApplication
public class ThemallProductApplication {

    public static void main(String[] args) {
        SpringApplication.run(ThemallProductApplication.class, args);
    }

}
