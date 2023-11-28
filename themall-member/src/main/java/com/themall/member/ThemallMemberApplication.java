package com.themall.member;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication
@MapperScan("com.themall.member.dao")
@EnableDiscoveryClient
public class ThemallMemberApplication {

    public static void main(String[] args) {
        SpringApplication.run(ThemallMemberApplication.class, args);
    }

}
