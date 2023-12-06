/**
 * Copyright (c) 2016-2019 人人开源 All rights reserved.
 * <p>
 * https://www.renren.io
 * <p>
 * 版权所有，侵权必究！
 */

package io.renren.modules.sys.controller;

import com.themall.common.utils.JwtUtils;
import io.renren.filter.JWTBasicAuthenticationFilter;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.Collections;
import java.util.Map;

/**
 * Controller公共组件
 *
 * @author Mark sunlightcs@gmail.com
 */
@Slf4j
public abstract class AbstractController {

    protected Map getUser() {
        //会转换为map
        HttpServletRequest request = request();
        if (ObjectUtils.isEmpty(request)) {
            return Collections.emptyMap();
        }
        Object attribute = request.getAttribute(JWTBasicAuthenticationFilter.class.getSimpleName() + JwtUtils.HEADER_NAME);
        if (attribute instanceof Map) {
            return (Map) attribute;
        }
        return Collections.emptyMap();
    }

    protected Long getUserId() {
        Map user = getUser();
        if (ObjectUtils.isEmpty(user)) {
            return null;
        }
        return Long.valueOf(user.get("userId").toString());
    }

    protected HttpServletRequest request() {
        RequestAttributes ra = RequestContextHolder.getRequestAttributes();
        if (ra != null) {
            return ((ServletRequestAttributes) ra).getRequest();
        }
        return null;
    }
}
