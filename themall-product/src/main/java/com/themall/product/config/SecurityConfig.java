package com.themall.product.config;

import com.themall.common.config.ResourceSecurityConfig;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

/**
 * @author poo0054
 */
@Configuration(proxyBeanMethods = false)
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfig extends ResourceSecurityConfig {

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http.authorizeHttpRequests(authorize -> authorize
                .antMatchers(
                        "/item/*.html",
                        "/", "/index.html", "/index", "/index/catalog.json",
                        "/lock", "/write", "/read", "/lockdoor", "/gogogo/{id}", "/park", "/go"
                ).permitAll()
                .anyRequest().authenticated()
        )
        ;
        return httpSecurity(http).build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return PasswordEncoderFactories.createDelegatingPasswordEncoder();
    }

    @Bean
    public ReloadableResourceBundleMessageSource reloadableResourceBundleMessageSource() {
        ReloadableResourceBundleMessageSource reloadableResourceBundleMessageSource = new ReloadableResourceBundleMessageSource();
        reloadableResourceBundleMessageSource.setBasename("classpath:org/springframework/security/messages");
        return reloadableResourceBundleMessageSource;
    }
}
