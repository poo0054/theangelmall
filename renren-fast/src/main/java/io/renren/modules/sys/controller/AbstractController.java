/**
 * Copyright (c) 2016-2019 人人开源 All rights reserved.
 * <p>
 * https://www.renren.io
 * <p>
 * 版权所有，侵权必究！
 */

package io.renren.modules.sys.controller;

import com.themall.model.entity.SysUserEntity;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.jwt.Jwt;

import java.util.Map;

/**
 * Controller公共组件
 *
 * @author Mark sunlightcs@gmail.com
 */
@Slf4j
public abstract class AbstractController {

    protected SysUserEntity getUser() {
        SysUserEntity sysUserEntity = new SysUserEntity();
        SecurityContext context = SecurityContextHolder.getContext();
        if (context.getAuthentication().getPrincipal() instanceof Jwt) {
            Jwt principal = (Jwt) context.getAuthentication().getPrincipal();
            Map<String, Object> claims = principal.getClaims();
            Object id = claims.getOrDefault("id", null);
            sysUserEntity.setUserId(Long.valueOf(id.toString()));
            sysUserEntity.setUsername(claims.getOrDefault("login", null).toString());
            //邮件不放出去
            //            sysUserEntity.setEmail(claims.getOrDefault("email", null).toString());
            return sysUserEntity;
        }
        return null;
    }

    protected Long getUserId() {
        SysUserEntity user = getUser();
        if (ObjectUtils.isEmpty(user)) {
            return null;
        }
        return user.getUserId();
    }
}
