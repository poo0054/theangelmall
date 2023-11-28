package com.themall.seckill.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.serializer.GenericJackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.session.data.redis.config.annotation.web.http.EnableRedisHttpSession;
import org.springframework.session.web.http.CookieSerializer;
import org.springframework.session.web.http.DefaultCookieSerializer;

/**
 * session 分布式 使用redis
 *
 * @ProjectName: theangelmall
 * @Package: com.theangel.themall.auth.comfig
 * @ClassName: SessionConfig
 * @Author: theangel
 * @Date: 2021/8/28 13:56
 */
@EnableRedisHttpSession
@Configuration
public class SessionConfig {

    @Bean
    public CookieSerializer cookieSerializer() {
        DefaultCookieSerializer cookieSerializer = new DefaultCookieSerializer();
        cookieSerializer.setDomainName("poo0054.top");
        return cookieSerializer;
    }

    /**
     * 必须使用这个名称
     * session存放redis的序列化
     *
     * @return
     */
    @Bean
    public RedisSerializer<Object> springSessionDefaultRedisSerializer() {
        return new GenericJackson2JsonRedisSerializer();
    }


}
