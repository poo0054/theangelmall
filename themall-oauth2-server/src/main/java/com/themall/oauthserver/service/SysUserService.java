/**
 * Copyright (c) 2016-2019 人人开源 All rights reserved.
 * <p>
 * https://www.renren.io
 * <p>
 * 版权所有，侵权必究！
 */

package com.themall.oauthserver.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.themall.model.entity.SysUserEntity;
import org.springframework.security.core.userdetails.UserDetails;


/**
 * 系统用户
 *
 * @author Mark sunlightcs@gmail.com
 */
public interface SysUserService extends IService<SysUserEntity> {


    /**
     * 获取当前用户信息
     *
     * @param username 用户名
     * @return 结果
     */
    UserDetails getUserDetails(String username);


}
