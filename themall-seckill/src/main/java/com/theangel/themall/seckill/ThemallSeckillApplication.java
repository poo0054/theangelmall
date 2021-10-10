package com.theangel.themall.seckill;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.session.data.redis.config.annotation.web.http.EnableRedisHttpSession;

/**
 * sentinel:
 * 配置控制台的信息：参考官网
 * 默认是保存在当前项目的内存中 重启失效！！！
 */
@SpringBootApplication(exclude = DataSourceAutoConfiguration.class)
@EnableDiscoveryClient
@EnableFeignClients(basePackages = "com.theangel.themall.seckill.openfeign")
public class ThemallSeckillApplication {

    public static void main(String[] args) {
        SpringApplication.run(ThemallSeckillApplication.class, args);
    }

}
