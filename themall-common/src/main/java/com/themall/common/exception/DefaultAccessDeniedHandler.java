package com.themall.common.exception;

import cn.hutool.http.ContentType;
import com.alibaba.fastjson2.JSON;
import com.themall.model.entity.R;
import com.themall.model.enums.HttpStatusEnum;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author poo0054
 */
@Slf4j
public class DefaultAccessDeniedHandler implements AccessDeniedHandler {
    @Override
    public void handle(HttpServletRequest request, HttpServletResponse response, AccessDeniedException accessDeniedException) throws IOException {
        log.warn("处理拒绝访问失败。", accessDeniedException);
        response.setCharacterEncoding("utf-8");
        response.setContentType(ContentType.JSON.toString());
        response.getWriter().print(JSON.toJSONString(R.error(HttpStatusEnum.USER_ERROR_A0301)));
    }
}
