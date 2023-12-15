package com.themall.common.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;

/**
 * @author poo0054
 */
@Slf4j
public class ResourceSecurityConfig {

    protected HttpSecurity httpSecurity(HttpSecurity http) throws Exception {
        return http
                .oauth2ResourceServer((oauth2) ->
                        oauth2.jwt(Customizer.withDefaults()
                        ))
                .logout(Customizer.withDefaults());
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