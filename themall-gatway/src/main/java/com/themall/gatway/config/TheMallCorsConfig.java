package com.themall.gatway.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.reactive.CorsWebFilter;
import org.springframework.web.cors.reactive.UrlBasedCorsConfigurationSource;

@Configuration
public class TheMallCorsConfig {

    @Bean
    public CorsWebFilter corsConfig() {
        UrlBasedCorsConfigurationSource corsConfiguration = new UrlBasedCorsConfigurationSource();
        CorsConfiguration corsConfigurationSource = new CorsConfiguration();
        corsConfigurationSource.addAllowedHeader("*");
        corsConfigurationSource.addAllowedMethod("*");
        corsConfigurationSource.addAllowedOrigin("*");
        corsConfigurationSource.setAllowCredentials(true);
        corsConfiguration.registerCorsConfiguration("/**", corsConfigurationSource);
        return new CorsWebFilter(corsConfiguration);
    }
}
