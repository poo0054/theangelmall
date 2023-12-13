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
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Set;

/**
 * 系统用户
 *
 * @author Mark sunlightcs@gmail.com
 */
@RestController
public class SysUserController {

    @Autowired
    private SysUserService sysUserService;


    @GetMapping("/getUserDetails")
    public Set<GrantedAuthority> getUserDetails() {
        Long userId = sysUserService.getByUserName(SecurityContextHolder.getContext().getAuthentication().getName()).getUserId();
        return sysUserService.getAuth(userId);
    }


}
