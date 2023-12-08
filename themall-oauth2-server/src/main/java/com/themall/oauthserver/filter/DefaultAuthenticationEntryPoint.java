package com.themall.oauthserver.filter;

import com.alibaba.fastjson2.JSON;
import com.themall.model.constants.HttpStatusEnum;
import com.themall.model.entity.R;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author poo0054
 */
@Slf4j
@Component
public class DefaultAuthenticationEntryPoint implements AuthenticationEntryPoint {

    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) throws IOException, ServletException {
        log.warn("认证失败。", authException);
        response.setCharacterEncoding("utf-8");
        response.setContentType("application/json");
        response.getWriter().print(JSON.toJSONString(R.error(HttpStatusEnum.USER_ERROR_A0300)));
    }
}
