package com.themall.common.config;

import com.themall.common.authentication.CustomAuthenticationConverter;
import com.themall.common.exception.DefaultAccessDeniedHandler;
import com.themall.common.exception.DefaultAuthenticationEntryPoint;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.oauth2.resource.OAuth2ResourceServerProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.oauth2.server.resource.authentication.DelegatingJwtGrantedAuthoritiesConverter;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationConverter;
import org.springframework.security.oauth2.server.resource.authentication.JwtGrantedAuthoritiesConverter;
import org.springframework.web.client.RestTemplate;

/**
 * @author poo0054
 */
@Slf4j
@EnableConfigurationProperties(OAuth2ResourceServerProperties.class)
public class ResourceSecurityConfig {

    OAuth2ResourceServerProperties oAuth2ResourceServerProperties;

    RestTemplate rest;

    @Autowired
    public void setoAuth2ResourceServerProperties(OAuth2ResourceServerProperties oAuth2ResourceServerProperties) {
        this.oAuth2ResourceServerProperties = oAuth2ResourceServerProperties;
    }

    @Autowired
    public void setRest(RestTemplate rest) {
        this.rest = rest;
    }

    protected HttpSecurity httpSecurity(HttpSecurity http) throws Exception {
        return http
                .oauth2ResourceServer((oauth2) ->
                        oauth2.jwt(Customizer.withDefaults())
                                .accessDeniedHandler(new DefaultAccessDeniedHandler()).authenticationEntryPoint(new DefaultAuthenticationEntryPoint())
                )
                .logout(Customizer.withDefaults())
                ;
    }


    @Bean
    public JwtAuthenticationConverter jwtAuthenticationConverter() {
        JwtGrantedAuthoritiesConverter jwtGrantedAuthoritiesConverter = new JwtGrantedAuthoritiesConverter();
        DelegatingJwtGrantedAuthoritiesConverter delegatingJwtGrantedAuthoritiesConverter = new DelegatingJwtGrantedAuthoritiesConverter(customAuthenticationConverter(), jwtGrantedAuthoritiesConverter);
        JwtAuthenticationConverter jwtAuthenticationConverter = new JwtAuthenticationConverter();
        jwtAuthenticationConverter.setJwtGrantedAuthoritiesConverter(delegatingJwtGrantedAuthoritiesConverter);
        return jwtAuthenticationConverter;
    }

    private CustomAuthenticationConverter customAuthenticationConverter() {
        return new CustomAuthenticationConverter(rest, oAuth2ResourceServerProperties);
    }

/*
    @EventListener
    public void onFailure(AuthenticationFailureBadCredentialsEvent badCredentials) {
        if (badCredentials.getAuthentication() instanceof BearerTokenAuthenticationToken) {
            log.warn(badCredentials.toString());
            throw new RRException(HttpStatusEnum.USER_ERROR_A0300);
        }
    }*/

}