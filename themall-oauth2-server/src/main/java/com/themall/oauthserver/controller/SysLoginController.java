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
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 系统用户
 *
 * @author Mark sunlightcs@gmail.com
 */
//@RestController
public class SysLoginController {


    private SysUserService sysUserService;

    @Autowired
    public void setSysUserService(SysUserService sysUserService) {
        this.sysUserService = sysUserService;
    }

    @GetMapping("/logout")
    public void getUserDetails(@RequestParam("redirect_uri") String redirect_uri, HttpServletResponse response) throws IOException {
        response.sendRedirect(redirect_uri);
    }


}
