package com.theangel.themall.product.config;

import org.redisson.Redisson;
import org.redisson.api.RedissonClient;
import org.redisson.config.Config;
import org.springframework.boot.autoconfigure.data.redis.RedisProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.util.ObjectUtils;

import java.util.List;
import java.util.stream.Collectors;

/**
 *
 */
@Configuration
@EnableConfigurationProperties(RedisProperties.class)
public class RedissonConfig {

    /**
     * 所有对redisson的使用 都是使用RedissonClient
     *
     * @return
     */
    @Bean(destroyMethod = "shutdown", value = "stringRedissonClient")
    public RedissonClient redissonClient(RedisProperties redisProperties) {
        Config config = new Config();
        RedisProperties.Cluster cluster = redisProperties.getCluster();
        if (!ObjectUtils.isEmpty(cluster)) {
            List<String> collect =
                cluster.getNodes().stream().map(item -> "redis://" + item).collect(Collectors.toList());
            config.useClusterServers()
                //Redis url should start with redis:// or rediss:// (for SSL connection)
                .setNodeAddresses(collect);
        } else {
            config.useSingleServer()
                //Redis url should start with redis:// or rediss:// (for SSL connection)
                .setAddress("redis://" + redisProperties.getHost() + ":" + redisProperties.getPort())
                .setPassword(redisProperties.getPassword()).setDatabase(redisProperties.getDatabase());
        }
        return Redisson.create(config);
    }

}
