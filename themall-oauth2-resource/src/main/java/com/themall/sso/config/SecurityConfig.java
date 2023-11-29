package com.themall.sso.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;

/**
 * @author poo0054
 */
@Configuration
@EnableWebSecurity(debug = true)
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfig {

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity httpSecurity) throws Exception {
        // 所有请求都进行拦截
        httpSecurity.authorizeRequests().anyRequest().authenticated();
        // 关闭session
        httpSecurity.sessionManagement().disable();
        // 配置资源服务器的无权限，无认证拦截器等 以及JWT验证
        httpSecurity.oauth2ResourceServer().jwt();
        return httpSecurity.build();
    }

}