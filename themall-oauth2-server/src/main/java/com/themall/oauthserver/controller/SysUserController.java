/**
 * Copyright (c) 2016-2019 人人开源 All rights reserved.
 * <p>
 * https://www.renren.io
 * <p>
 * 版权所有，侵权必究！
 */

package com.themall.oauthserver.controller;

import com.themall.oauthserver.service.SysUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collections;
import java.util.Set;

/**
 * 系统用户
 *
 * @author Mark sunlightcs@gmail.com
 */
@RestController
public class SysUserController {


    private SysUserService sysUserService;

    @Autowired
    public void setSysUserService(SysUserService sysUserService) {
        this.sysUserService = sysUserService;
    }

    @GetMapping("/getUserDetails")
    public Set<GrantedAuthority> getUserDetails() {
        Long userId = sysUserService.getByOauthId(SecurityContextHolder.getContext().getAuthentication().getName()).getUserId();
        if (SecurityContextHolder.getContext().getAuthentication().getAuthorities().contains(new SimpleGrantedAuthority("SCOPE_all"))) {
            //只有获取所有权限才加入
            return sysUserService.getAuth(userId);
        }
        return Collections.emptySet();

    }


}
