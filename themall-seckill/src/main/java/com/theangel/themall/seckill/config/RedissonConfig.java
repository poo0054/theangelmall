package com.theangel.themall.seckill.config;

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
    @Bean(destroyMethod = "shutdown")
    public RedissonClient redissonClient() {
        Config config = new Config();
        config.useSingleServer()
                //Redis url should start with redis:// or rediss:// (for SSL connection)
                .setAddress("redis://192.168.56.10:6379");
        return Redisson.create(config);
    }


}