package com.themall.sso.authentication;

import org.apache.commons.lang3.ObjectUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.oauth2.resource.OAuth2ResourceServerProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.core.convert.converter.Converter;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.Collection;

/**
 * @author poo0054
 */
@EnableConfigurationProperties(OAuth2ResourceServerProperties.class)
public class CustomAuthenticationConverter implements Converter<Jwt, Collection<GrantedAuthority>> {
    private static final String PATH = "/getUserDetails";

    @Autowired
    RestTemplate rest;

    @Autowired
    OAuth2ResourceServerProperties oAuth2ResourceServerProperties;

    @Override
    public Collection<GrantedAuthority> convert(Jwt source) {
        String issuerUri = oAuth2ResourceServerProperties.getJwt().getIssuerUri();
        ResponseEntity<UserDetails> forEntity = rest.getForEntity(issuerUri + PATH, UserDetails.class);
        HttpStatus statusCode = forEntity.getStatusCode();
        if (statusCode.is2xxSuccessful()) {
            UserDetails body = forEntity.getBody();
            if (ObjectUtils.isNotEmpty(body) && ObjectUtils.isNotEmpty(body.getAuthorities())) {
                return new ArrayList<>(body.getAuthorities());
            }
        }
        return null;
    }
}
