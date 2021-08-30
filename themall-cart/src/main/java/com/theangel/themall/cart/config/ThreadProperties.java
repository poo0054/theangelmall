package com.theangel.themall.cart.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@ConfigurationProperties(prefix = "themall.thread")
@Component
@Data
public class ThreadProperties {
    /**
     * 核心线程池
     */
    private int coreSize = 10;
    /**
     * 最大线程池
     */
    private int maxSize = 200;
    /**
     * 过期时间
     */
    private int keepAliveTime = 10;
}
