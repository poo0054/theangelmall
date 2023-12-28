/**
 * Copyright (c) 2016-2019 人人开源 All rights reserved.
 * <p>
 * https://www.renren.io
 * <p>
 * 版权所有，侵权必究！
 */

package com.themall.oauthserver.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.themall.model.constants.Constant;
import com.themall.model.entity.SysRole;
import com.themall.oauthserver.dao.SysRoleDao;
import com.themall.oauthserver.service.SysRoleService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

/**
 * 角色
 *
 * @author Mark sunlightcs@gmail.com
 */
@Service("sysRoleService")
public class SysRoleServiceImpl extends ServiceImpl<SysRoleDao, SysRole> implements SysRoleService {

    @Override
    public List<SysRole> listByUserId(Long userId) {
        if (Objects.equals(Constant.SUPER_ADMIN, userId)) {
            return this.list();
        }
        return baseMapper.listByUserId(userId);
    }
}
