package com.themall.common.config;

import com.themall.common.authentication.CustomAuthenticationConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.oauth2.resource.OAuth2ResourceServerProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.oauth2.server.resource.authentication.DelegatingJwtGrantedAuthoritiesConverter;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationConverter;
import org.springframework.security.oauth2.server.resource.authentication.JwtGrantedAuthoritiesConverter;
import org.springframework.web.client.RestTemplate;

/**
 * @author poo0054
 */
@Configuration(proxyBeanMethods = false)
@EnableConfigurationProperties(OAuth2ResourceServerProperties.class)
public class AuthenticationConfig {

    @Autowired
    OAuth2ResourceServerProperties oAuth2ResourceServerProperties;

    @Autowired
    RestTemplate rest;

    @Bean
    public CustomAuthenticationConverter customAuthenticationConverter() {
        return new CustomAuthenticationConverter(rest, oAuth2ResourceServerProperties);
    }

    @Bean
    public JwtAuthenticationConverter jwtAuthenticationConverter(CustomAuthenticationConverter customAuthenticationConverter) {
        JwtGrantedAuthoritiesConverter jwtGrantedAuthoritiesConverter = new JwtGrantedAuthoritiesConverter();
        DelegatingJwtGrantedAuthoritiesConverter delegatingJwtGrantedAuthoritiesConverter = new DelegatingJwtGrantedAuthoritiesConverter(customAuthenticationConverter, jwtGrantedAuthoritiesConverter);
        JwtAuthenticationConverter jwtAuthenticationConverter = new JwtAuthenticationConverter();
        jwtAuthenticationConverter.setJwtGrantedAuthoritiesConverter(delegatingJwtGrantedAuthoritiesConverter);
        return jwtAuthenticationConverter;
    }
}
