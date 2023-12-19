package com.themall.common.authentication;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.boot.autoconfigure.security.oauth2.resource.OAuth2ResourceServerProperties;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.core.convert.converter.Converter;
import org.springframework.http.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.Collection;
import java.util.Collections;
import java.util.Set;

/**
 * 权限提升
 *
 * @author poo0054
 */
@Slf4j
@Data
public class CustomAuthenticationConverter implements Converter<Jwt, Collection<GrantedAuthority>> {
    /**
     * 获取当前用户的权限
     */
    private static final String PATH = "/getUserDetails";

    RestTemplate rest;

    OAuth2ResourceServerProperties oAuth2ResourceServerProperties;

    public CustomAuthenticationConverter(RestTemplate rest, OAuth2ResourceServerProperties oAuth2ResourceServerProperties) {
        this.rest = rest;
        this.oAuth2ResourceServerProperties = oAuth2ResourceServerProperties;
    }

    @Override
    public Collection<GrantedAuthority> convert(Jwt source) {
        if (null != request()) {
            HttpServletRequest request = request();
            String issuerUri = oAuth2ResourceServerProperties.getJwt().getIssuerUri();
            RequestEntity<Void> requestEntity = RequestEntity
                    .method(HttpMethod.GET, issuerUri + PATH)
                    .contentType(MediaType.APPLICATION_JSON)
                    .accept(MediaType.APPLICATION_JSON)
                    .header(HttpHeaders.AUTHORIZATION, request.getHeader(HttpHeaders.AUTHORIZATION))
                    .build();
            ResponseEntity<Set<GrantedAuthority>> response;
            try {
                response = rest.exchange(requestEntity, new ParameterizedTypeReference<Set<GrantedAuthority>>() {
                });
            } catch (HttpClientErrorException e) {
                log.warn("获取内部权限失败:", e);
                log.warn("请求地址参数：{}", requestEntity);
                throw new OAuth2AuthenticationException(e.getMessage());
            }
            HttpStatus statusCode = response.getStatusCode();
            if (statusCode.is2xxSuccessful()) {
                Set<GrantedAuthority> body = response.getBody();
                if (ObjectUtils.isNotEmpty(body)) {
                    return Collections.unmodifiableSet(body);
                }
            }
        }
        return null;
    }

    protected HttpServletRequest request() {
        RequestAttributes ra = RequestContextHolder.getRequestAttributes();
        if (ra != null) {
            return ((ServletRequestAttributes) ra).getRequest();
        }
        return null;
    }


}
