/**
 * Copyright (c) 2016-2019 人人开源 All rights reserved.
 * <p>
 * https://www.renren.io
 * <p>
 * 版权所有，侵权必究！
 */

package com.themall.oauthserver.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.themall.model.entity.SysRoleEntity;

import java.util.List;


/**
 * 角色
 *
 * @author Mark sunlightcs@gmail.com
 */
public interface SysRoleService extends IService<SysRoleEntity> {


    List<SysRoleEntity> listByUserId(Long userId);
}
