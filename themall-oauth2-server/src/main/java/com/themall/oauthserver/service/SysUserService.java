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
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Set;


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


    /**
     * 根据name查询
     *
     * @param username name
     * @return 结果
     */
    SysUserEntity getByUserName(String username);

    /**
     * 根据id查询所有权限
     *
     * @param userId 用户id
     * @return 所有权限
     */
    Set<GrantedAuthority> getAuth(Long userId);

    /**
     * 可能是id 和 name
     *
     * @param principalName id 或者 name
     * @return 结果
     */
    SysUserEntity getPrincipalName(String principalName);
}
