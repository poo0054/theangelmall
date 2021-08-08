package com.theangel.themall.product.config;

import org.redisson.Redisson;
import org.redisson.api.RedissonClient;
import org.redisson.config.Config;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 *
 */
@Configuration
public class RedissonConfig {

    /**
     * 所有对redisson的使用 都是使用RedissonClient
     *
     * @return
     */
    @Bean(destroyMethod = "shutdown", value = "singRedissonClient")
    public RedissonClient redissonClient() {
        Config config = new Config();
        config.useSingleServer()
                //Redis url should start with redis:// or rediss:// (for SSL connection)
                .setAddress("redis://127.0.0.1:6379");
        return Redisson.create(config);
    }
}
