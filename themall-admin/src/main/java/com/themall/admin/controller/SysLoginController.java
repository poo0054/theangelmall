/**
 * Copyright (c) 2016-2019 人人开源 All rights reserved.
 * <p>
 * https://www.renren.io
 * <p>
 * 版权所有，侵权必究！
 */

package com.themall.admin.controller;

import com.themall.model.entity.R;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.security.oauth2.resource.OAuth2ResourceServerProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.core.endpoint.OAuth2ParameterNames;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.stereotype.Controller;
import org.springframework.util.Base64Utils;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Map;

/**
 * 登录相关
 *
 * @author Mark sunlightcs@gmail.com
 */
@Slf4j
@Controller
@RequestMapping("/sys")
@EnableConfigurationProperties(OAuth2ResourceServerProperties.class)
public class SysLoginController extends AbstractController {

    String messagesBaseUri;
    String vueUri;
    String clientId;
    String clientSecret;

    @Autowired
    OAuth2ResourceServerProperties oAuth2ResourceServerProperties;
    @Autowired
    RestTemplate rest;
    SecurityContextLogoutHandler logoutHandler = new SecurityContextLogoutHandler();

    public SysLoginController(@Value("${resource.base-uri}") String messagesBaseUri,
                              @Value("${resource.vue-uri}") String vueUri,
                              @Value("${resource.client_id}") String clientId,
                              @Value("${resource.client_secret}") String clientSecret) {
        this.messagesBaseUri = messagesBaseUri;
        this.vueUri = vueUri;
        this.clientId = clientId;
        this.clientSecret = clientSecret;
    }

    /**
     * 登录
     */
    @GetMapping("/login")
    public void login(HttpServletResponse response) throws IOException {
        String issuerUri = oAuth2ResourceServerProperties.getJwt().getIssuerUri();
        String url = issuerUri + "/oauth2/authorize?client_id=" + clientId + "&response_type=code&scope=themall&redirect_uri=" + messagesBaseUri;
        response.sendRedirect(url);
    }

    @GetMapping("/authorized")
    @ResponseBody
    public void authorized(@RequestParam("code") String code, HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        MultiValueMap<String, Object> bodyParams = new LinkedMultiValueMap<>();
        bodyParams.add(OAuth2ParameterNames.CODE, code);
        bodyParams.add(OAuth2ParameterNames.GRANT_TYPE, "authorization_code");
        bodyParams.add(OAuth2ParameterNames.REDIRECT_URI, messagesBaseUri);
        String issuerUri = oAuth2ResourceServerProperties.getJwt().getIssuerUri();
        String base64 = clientId + ":" + clientSecret;
        String basic = Base64Utils.encodeToString(base64.getBytes(StandardCharsets.UTF_8));
        RequestEntity<MultiValueMap<String, Object>> body = RequestEntity
                .post(issuerUri + "/oauth2/token")
                .contentType(MediaType.MULTIPART_FORM_DATA)
                .accept(MediaType.ALL)
                .header(HttpHeaders.AUTHORIZATION, "Basic " + basic)
                .body(bodyParams);
        ResponseEntity<Map> responseEntity = rest.exchange(body, Map.class);
        if (responseEntity.getStatusCode().is2xxSuccessful()) {
            response.sendRedirect(vueUri + responseEntity.getBody().get("access_token") + "&expire=" + responseEntity.getBody().get("expires_in"));
            return;
        }
        log.warn(responseEntity.toString());
        request.getRequestDispatcher("/sys/login").forward(request, response);
    }

    /**
     * 退出
     */
    @PostMapping("/logout")
    @ResponseBody
    public R logout(Authentication authentication, HttpServletRequest request, HttpServletResponse response) {
        String issuerUri = oAuth2ResourceServerProperties.getJwt().getIssuerUri();
        this.logoutHandler.logout(request, response, authentication);
        SecurityContextHolder.clearContext();
        return R.ok().put("logoutUrl", issuerUri + "/logout");
    }

}
