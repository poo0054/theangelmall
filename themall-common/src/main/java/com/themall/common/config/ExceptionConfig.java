package com.themall.common.config;

import com.themall.common.exception.DefaultAccessDeniedHandler;
import com.themall.common.exception.DefaultAuthenticationEntryPoint;
import com.themall.common.exception.RRExceptionHandler;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author poo0054
 */
@Configuration(proxyBeanMethods = false)
public class ExceptionConfig {

    @Bean
    public RRExceptionHandler rrExceptionHandler() {
        return new RRExceptionHandler();
    }

    @Bean
    public DefaultAccessDeniedHandler defaultAccessDeniedHandler() {
        return new DefaultAccessDeniedHandler();
    }

    @Bean
    public DefaultAuthenticationEntryPoint defaultAuthenticationEntryPoint() {
        return new DefaultAuthenticationEntryPoint();
    }
}
