/**
 * Copyright (c) 2016-2019 人人开源 All rights reserved.
 * <p>
 * https://www.renren.io
 * <p>
 * 版权所有，侵权必究！
 */

package com.themall.admin.controller;

import com.themall.model.entity.SysUser;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.jwt.Jwt;

/**
 * Controller公共组件
 *
 * @author Mark sunlightcs@gmail.com
 */
@Slf4j
public abstract class AbstractController {

    protected SysUser getUser() {
        SysUser sysUserEntity = new SysUser();
        SecurityContext context = SecurityContextHolder.getContext();
        if (context.getAuthentication().getPrincipal() instanceof Jwt) {
            Jwt principal = (Jwt) context.getAuthentication().getPrincipal();
            sysUserEntity.setUserId(Long.valueOf(principal.getId()));
            sysUserEntity.setUserName(principal.getClaims().get("name").toString());
            sysUserEntity.setOauthId(principal.getSubject());
            //邮件不放出去
            //            sysUserEntity.setEmail(claims.get("email").toString());
            return sysUserEntity;
        }
        return null;
    }

    protected Long getUserId() {
        SysUser user = getUser();
        if (ObjectUtils.isEmpty(user)) {
            return null;
        }
        return user.getUserId();
    }
}
