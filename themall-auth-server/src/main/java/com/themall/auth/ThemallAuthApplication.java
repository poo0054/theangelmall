package com.themall.auth;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.session.data.redis.config.annotation.web.http.EnableRedisHttpSession;

/**
 * EnableRedisHttpSession原理：
 * 1).RedisIndexedSessionRepository ：操作数据：dao层  操作session
 * 2).SessionRepositoryFilter：过滤器  就是servlet的filter，每个请求过来经过
 * 2.1SessionRepository-》实现类SessionRepository  存放session的地方
 *                      -》  doFilterInternal->springSession核心原理
 *自动延期
 *
 * @author theangel.vip
 */
@EnableDiscoveryClient
@EnableFeignClients(basePackages = "com.theangel.themall.auth.openfeign")
@SpringBootApplication(exclude = {DataSourceAutoConfiguration.class})
//开启redis存储session
@EnableRedisHttpSession
public class ThemallAuthApplication {

    public static void main(String[] args) {
        SpringApplication.run(ThemallAuthApplication.class, args);
    }

}
