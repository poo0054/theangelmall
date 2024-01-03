/**
 * Copyright (c) 2016-2019 人人开源 All rights reserved.
 * <p>
 * https://www.renren.io
 * <p>
 * 版权所有，侵权必究！
 */

package com.themall.admin.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.themall.model.entity.SysRole;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * 角色管理
 *
 * @author Mark sunlightcs@gmail.com
 */
@Mapper
public interface SysRoleDao extends BaseMapper<SysRole> {

    /**
     * 查询用户创建的角色ID列表
     */
    List<Long> queryRoleIdList(String createUserId);

    List<SysRole> listByUserId(Long userId);
}
