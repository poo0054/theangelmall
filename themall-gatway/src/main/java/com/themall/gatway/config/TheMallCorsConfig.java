package com.themall.gatway.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.reactive.CorsWebFilter;
import org.springframework.web.cors.reactive.UrlBasedCorsConfigurationSource;
import org.springframework.web.util.pattern.PathPatternParser;

import java.util.Collections;

/**
 * @author poo0054
 */
@Configuration(proxyBeanMethods = false)
public class TheMallCorsConfig {

    @Bean
    public CorsWebFilter corsConfig() {
        CorsConfiguration corsConfigurationSource = new CorsConfiguration();
        corsConfigurationSource.setAllowedOriginPatterns(Collections.singletonList("*"));
        corsConfigurationSource.addAllowedHeader("*");
        corsConfigurationSource.addAllowedMethod("*");
        corsConfigurationSource.setAllowCredentials(true);
        UrlBasedCorsConfigurationSource corsConfiguration = new UrlBasedCorsConfigurationSource(new PathPatternParser());
        corsConfiguration.registerCorsConfiguration("/**", corsConfigurationSource);
        return new CorsWebFilter(corsConfiguration);
    }
}
