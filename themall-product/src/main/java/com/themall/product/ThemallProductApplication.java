package com.themall.product;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.session.data.redis.config.annotation.web.http.EnableRedisHttpSession;


/**
 * @Cacheable：触发缓存群。 触发将数据保存到缓存的操作
 * @CacheEvict：触发缓存驱逐。 将缓存删除
 * @CachePut：在不干扰方法执行的情况下更新缓存。
 * @Caching：重新组合多个缓存操作，以应用于方法。 组合多个缓存操作，组合以上多个缓存
 * @CacheConfig： 在类级别共享一些与缓存相关的常见设置。  在类级别共享相同的缓存
 * @EnableCaching： 开启缓存注解，只要使用注解就可以完成缓存操作
 */
@EnableCaching
@SpringBootApplication
@EnableDiscoveryClient
@EnableFeignClients(basePackages = "com.themall.product.openfeign")
@EnableRedisHttpSession
public class ThemallProductApplication {
    public static void main(String[] args) {
        SpringApplication.run(ThemallProductApplication.class, args);
    }
}
