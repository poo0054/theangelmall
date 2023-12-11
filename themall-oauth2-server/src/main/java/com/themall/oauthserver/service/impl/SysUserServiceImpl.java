/**
 * Copyright (c) 2016-2019 人人开源 All rights reserved.
 * <p>
 * https://www.renren.io
 * <p>
 * 版权所有，侵权必究！
 */

package com.themall.oauthserver.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.themall.model.entity.SysMenuEntity;
import com.themall.model.entity.SysRoleEntity;
import com.themall.model.entity.SysUserEntity;
import com.themall.oauthserver.dao.SysUserDao;
import com.themall.oauthserver.service.SysMenuService;
import com.themall.oauthserver.service.SysRoleService;
import com.themall.oauthserver.service.SysUserService;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;


/**
 * 系统用户
 *
 * @author Mark sunlightcs@gmail.com
 */
@Service("sysUserService")
public class SysUserServiceImpl extends ServiceImpl<SysUserDao, SysUserEntity> implements SysUserService {


    private SysRoleService sysRoleService;

    private SysMenuService sysMenuService;

    @Autowired
    public void setSysRoleService(SysRoleService sysRoleService) {
        this.sysRoleService = sysRoleService;
    }

    @Autowired
    public void setSysMenuService(SysMenuService sysMenuService) {
        this.sysMenuService = sysMenuService;
    }


    @Override
    public UserDetails getUserDetails(String username) {
        if (null == username) {
            return null;
        }
        SysUserEntity sysUserEntity = this.getByUserName(username);
        if (null == sysUserEntity) {
            return null;
        }
        User.UserBuilder builder = User.builder();
        builder.username(sysUserEntity.getUsername());
        builder.password(sysUserEntity.getPassword());
        builder.authorities(AuthorityUtils.NO_AUTHORITIES);
        Set<String> auth = new HashSet<>();
        //权限
        List<SysRoleEntity> roleServiceAll = sysRoleService.listByUserId(sysUserEntity.getUserId());
        if (ObjectUtils.isNotEmpty(roleServiceAll)) {
            List<String> roles = roleServiceAll.stream().map(SysRoleEntity::getRoleName).collect(Collectors.toList());
            auth.addAll(roles);
        }
        //菜单
        List<SysMenuEntity> sysMenuEntities = sysMenuService.listByUserId(sysUserEntity.getUserId());
        if (ObjectUtils.isNotEmpty(sysMenuEntities)) {
            auth.addAll(sysMenuEntities.stream().map(SysMenuEntity::getPerms).filter(ObjectUtils::isNotEmpty).collect(Collectors.toList()));
        }
        List<GrantedAuthority> authorities = new ArrayList<>();
        for (String s : auth) {
            authorities.addAll(AuthorityUtils.commaSeparatedStringToAuthorityList(s));
        }
        builder.authorities(authorities);
        return builder.build();
    }

    @Override
    public SysUserEntity getByUserName(String username) {
        LambdaQueryWrapper<SysUserEntity> lambdaQuery = Wrappers.lambdaQuery(SysUserEntity.class);
        lambdaQuery.eq(SysUserEntity::getUsername, username);
        return getOne(lambdaQuery);
    }
}