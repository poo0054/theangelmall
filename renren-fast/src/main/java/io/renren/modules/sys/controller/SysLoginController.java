/**
 * Copyright (c) 2016-2019 人人开源 All rights reserved.
 * <p>
 * https://www.renren.io
 * <p>
 * 版权所有，侵权必究！
 */

package io.renren.modules.sys.controller;

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
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.core.endpoint.OAuth2ParameterNames;
import org.springframework.stereotype.Controller;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
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

    @Autowired
    OAuth2ResourceServerProperties oAuth2ResourceServerProperties;
    @Autowired
    RestTemplate rest;

    public SysLoginController(@Value("${messages.base-uri}") String messagesBaseUri) {
        this.messagesBaseUri = messagesBaseUri;
    }

    /**
     * 登录
     */
    @GetMapping("/login")
    public void login(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        String issuerUri = oAuth2ResourceServerProperties.getJwt().getIssuerUri();
        String url = issuerUri + "/oauth2/authorize?client_id=themall&response_type=code&scope=all&redirect_uri=" + messagesBaseUri;
//        request.getRequestDispatcher(url).forward(request, response);
        response.setStatus(HttpServletResponse.SC_MOVED_PERMANENTLY);
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
        RequestEntity<MultiValueMap<String, Object>> body = RequestEntity
                .post(issuerUri + "/oauth2/token")
                .contentType(MediaType.MULTIPART_FORM_DATA)
                .accept(MediaType.ALL)
                .header(HttpHeaders.AUTHORIZATION, "Basic dGhlbWFsbDpyZW5yZW4tZmFzdC10aGVtYWxs")
                .body(bodyParams);
        ResponseEntity<Map> responseEntity = rest.exchange(body, Map.class);
        if (responseEntity.getStatusCode().is2xxSuccessful()) {
            response.setStatus(HttpServletResponse.SC_MOVED_PERMANENTLY);
            response.sendRedirect("http://127.0.0.1:8001/#/login?token=" + responseEntity.getBody().get("access_token") + "&expire=" + responseEntity.getBody().get("expires_in"));
            return;
        }
        log.warn(responseEntity.toString());
        response.sendRedirect("/sys/login");
//        return R.ok().put("token", response.getBody().get("access_token")).put("expire", response.getBody().get("expires_in"));
    }

    /**
     * 退出
     */
    @PostMapping("/logout")
    @ResponseBody
    public R logout() {
        SecurityContextHolder.clearContext();
        return R.ok();
    }

}
