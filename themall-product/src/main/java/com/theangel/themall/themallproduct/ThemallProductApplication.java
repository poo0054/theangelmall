package com.theangel.themall.themallproduct;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@MapperScan("com/theangel/themall/themallproduct/dao")
@SpringBootApplication
public class ThemallProductApplication {

    public static void main(String[] args) {
        SpringApplication.run(ThemallProductApplication.class, args);
    }

}
