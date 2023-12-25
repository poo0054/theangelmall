/*
 * Copyright 2020-2022 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.themall.oauthserver.security;

import com.themall.model.entity.SysUserEntity;
import com.themall.model.entity.SysUserRoleEntity;
import com.themall.oauthserver.service.SysUserRoleService;
import com.themall.oauthserver.service.SysUserService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.function.Consumer;

/**
 * Example {@link Consumer} to perform JIT provisioning of an {@link OAuth2User}.
 *
 * @author Steve Riesenberg
 * @since 0.2.3
 */
@Slf4j
@Component
public class UserRepositoryOAuth2UserHandler implements Consumer<OAuth2User> {

    private SysUserService userService;

    private SysUserRoleService sysUserRoleService;

    @Autowired
    public void setUserService(SysUserService userService) {
        this.userService = userService;
    }

    @Autowired
    public void setSysUserRoleService(SysUserRoleService sysUserRoleService) {
        this.sysUserRoleService = sysUserRoleService;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void accept(OAuth2User user) {
        log.info("Saving user: name=" + user.getName() + ", claims=" + user.getAttributes() + ", authorities=" + user.getAuthorities());
        SysUserEntity sysUserEntity = new SysUserEntity();
        sysUserEntity.setEmail(user.getAttribute("email"));
        sysUserEntity.setOauthId(user.getName());
        String name = user.getAttribute("name");
        //github中name不存在就使用login
        if (StringUtils.isBlank(name)) {
            name = user.getAttribute("login");
        }
        sysUserEntity.setOauthName(name);
        //分为谷歌和github
        SysUserEntity userEntity = this.userService.getByLoginName(user.getName());
        if (ObjectUtils.isEmpty(userEntity)) {
            userEntity.setCreateUserId(1L);
            userEntity.setCreateTime(new Date());
            this.userService.save(sysUserEntity);
            SysUserRoleEntity sysUserRoleEntity = new SysUserRoleEntity();
            sysUserRoleEntity.setUserId(sysUserEntity.getUserId());
            //默认用户读取权限
            sysUserRoleEntity.setRoleId(1L);
            //给予默认权限
            sysUserRoleService.save(sysUserRoleEntity);
        } else {
            //修改
            sysUserEntity.setUserId(userEntity.getUserId());
            this.userService.updateById(sysUserEntity);
        }

    }
}
