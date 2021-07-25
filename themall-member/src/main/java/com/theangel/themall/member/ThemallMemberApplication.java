package com.theangel.themall.member;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;


@SpringBootApplication
@MapperScan("com/theangel/themall/member/dao")
@EnableDiscoveryClient
@EnableFeignClients(basePackages = "com.theangel.themall.member.openfeign")
public class ThemallMemberApplication {

    public static void main(String[] args) {
        SpringApplication.run(ThemallMemberApplication.class, args);
    }

}
