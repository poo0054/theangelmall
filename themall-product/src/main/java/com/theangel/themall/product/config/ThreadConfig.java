package com.theangel.themall.product.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

@Configuration
public class ThreadConfig {

    @Autowired
    ThreadPoolConfig threadPoolConfig;


    @Bean
    public ThreadPoolExecutor poolExecutor() {
        return new ThreadPoolExecutor(threadPoolConfig.getCoreSize(), threadPoolConfig.getMaxSize(),
                threadPoolConfig.getKeepAliveTime(), TimeUnit.SECONDS, new LinkedBlockingDeque<>(100000)
                , Executors.defaultThreadFactory(), new ThreadPoolExecutor.DiscardOldestPolicy());
    }
}
