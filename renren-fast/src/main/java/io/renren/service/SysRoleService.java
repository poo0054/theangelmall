/**
 * Copyright (c) 2016-2019 人人开源 All rights reserved.
 * <p>
 * https://www.renren.io
 * <p>
 * 版权所有，侵权必究！
 */

package io.renren.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.themall.model.entity.SysRole;
import io.renren.utils.PageUtils;

import java.util.List;
import java.util.Map;


/**
 * 角色
 *
 * @author Mark sunlightcs@gmail.com
 */
public interface SysRoleService extends IService<SysRole> {

    PageUtils queryPage(Map<String, Object> params);

    void saveRole(SysRole role);

    void update(SysRole role);

    void deleteBatch(Long[] roleIds);

    /**
     * 查询用户创建的角色ID列表
     */
    List<Long> queryRoleIdList(Long createUserId);


    List<SysRole> listByUserId(Long userId);
}
