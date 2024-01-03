/**
 * Copyright (c) 2016-2019 人人开源 All rights reserved.
 * <p>
 * https://www.renren.io
 * <p>
 * 版权所有，侵权必究！
 */

package com.themall.admin.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.themall.admin.dao.SysRoleDao;
import com.themall.admin.service.SysMenuService;
import com.themall.admin.service.SysRoleMenuService;
import com.themall.admin.service.SysRoleService;
import com.themall.admin.service.SysUserRoleService;
import com.themall.admin.utils.PageUtils;
import com.themall.admin.utils.Query;
import com.themall.model.constants.Constant;
import com.themall.model.entity.SysRole;
import com.themall.model.exception.RRException;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

/**
 * 角色
 *
 * @author Mark sunlightcs@gmail.com
 */
@Service("sysRoleService")
public class SysRoleServiceImpl extends ServiceImpl<SysRoleDao, SysRole> implements SysRoleService {

    private SysRoleMenuService sysRoleMenuService;

    private SysMenuService sysMenuService;
    private SysUserRoleService sysUserRoleService;

    @Autowired
    public void setSysRoleMenuService(SysRoleMenuService sysRoleMenuService) {
        this.sysRoleMenuService = sysRoleMenuService;
    }

    @Autowired
    public void setSysMenuService(SysMenuService sysMenuService) {
        this.sysMenuService = sysMenuService;
    }

    @Autowired
    public void setSysUserRoleService(SysUserRoleService sysUserRoleService) {
        this.sysUserRoleService = sysUserRoleService;
    }

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        Object roleName = params.get("roleName");
        Object createUserId = params.get("createUserId");
        LambdaQueryWrapper<SysRole> lambdaQueryWrapper = Wrappers.lambdaQuery(SysRole.class);
        lambdaQueryWrapper.like(ObjectUtils.isNotEmpty(roleName), SysRole::getRoleName, roleName);
        lambdaQueryWrapper.eq(ObjectUtils.isNotEmpty(createUserId), SysRole::getCreateUserId, createUserId);
        IPage<SysRole> page = this.page(
                new Query<SysRole>().getPage(params),
                lambdaQueryWrapper
        );
        return new PageUtils(page);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void saveRole(SysRole role) {
        role.setCreateTime(new Date());
        this.save(role);

        //检查权限是否越权
        checkPrems(role);

        //保存角色与菜单关系
        sysRoleMenuService.saveOrUpdate(role.getRoleId(), role.getMenuIdList());
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void update(SysRole role) {
        this.updateById(role);

        //检查权限是否越权
        checkPrems(role);

        //更新角色与菜单关系
        sysRoleMenuService.saveOrUpdate(role.getRoleId(), role.getMenuIdList());
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void deleteBatch(Long[] roleIds) {
        //删除角色
        this.removeByIds(Arrays.asList(roleIds));

        //删除角色与菜单关联
        sysRoleMenuService.deleteBatch(roleIds);

        //删除角色与用户关联
        sysUserRoleService.deleteBatch(roleIds);
    }


    @Override
    public List<Long> queryRoleIdList(Long createUserId) {
        return baseMapper.queryRoleIdList(createUserId);
    }


    @Override
    public List<SysRole> listByUserId(Long userId) {
        if (Objects.equals(Constant.SUPER_ADMIN, userId)) {
            return this.list();
        }
        return baseMapper.listByUserId(userId);
    }

    /**
     * 检查权限是否越权
     */
    private void checkPrems(SysRole role) {
        //如果不是超级管理员，则需要判断角色的权限是否超过自己的权限
        if (Objects.equals(Constant.SUPER_ADMIN, role.getCreateUserId())) {
            return;
        }

        //查询用户所拥有的菜单列表
        List<Long> menuIdList = sysMenuService.queryAllMenuId(role.getCreateUserId());

        //判断是否越权
        if (!menuIdList.containsAll(role.getMenuIdList())) {
            throw new RRException("新增角色的权限，已超出你的权限范围");
        }
    }
}
