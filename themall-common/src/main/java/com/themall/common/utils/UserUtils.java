package com.themall.common.utils;

import com.themall.model.entity.SysUser;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.jwt.Jwt;

public class UserUtils {

    public static SysUser getUser() {
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
        return sysUserEntity;
    }

    public static Long getUserId() {
        SysUser user = getUser();
        if (ObjectUtils.isEmpty(user)) {
            return null;
        }
        return user.getUserId();
    }

    public static String getUserName() {
        SysUser user = getUser();
        if (ObjectUtils.isEmpty(user)) {
            return null;
        }
        return user.getUserName();
    }

}
