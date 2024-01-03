/**
 * Copyright (c) 2016-2019 人人开源 All rights reserved.
 * <p>
 * https://www.renren.io
 * <p>
 * 版权所有，侵权必究！
 */

package com.themall.admin.controller;

import com.themall.admin.annotation.SysLog;
import com.themall.admin.service.SysRoleMenuService;
import com.themall.admin.service.SysRoleService;
import com.themall.admin.utils.PageUtils;
import com.themall.model.constants.Constant;
import com.themall.model.entity.R;
import com.themall.model.entity.SysRole;
import com.themall.model.validator.ValidatorUtils;
import com.themall.model.validator.group.AddGroup;
import com.themall.model.validator.group.UpdateGroup;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

/**
 * 角色管理
 *
 * @author Mark sunlightcs@gmail.com
 */
@RestController
@RequestMapping("/sys/role")
public class SysRoleController extends AbstractController {

    private SysRoleService sysRoleService;
    private SysRoleMenuService sysRoleMenuService;

    @Autowired
    public void setSysRoleService(SysRoleService sysRoleService) {
        this.sysRoleService = sysRoleService;
    }

    @Autowired
    public void setSysRoleMenuService(SysRoleMenuService sysRoleMenuService) {
        this.sysRoleMenuService = sysRoleMenuService;
    }

    /**
     * 角色列表
     */
    @GetMapping("/list")
    @PreAuthorize("hasAuthority('sys:role:list')")
    public R list(@RequestParam Map<String, Object> params) {
        //如果不是超级管理员，则只查询自己创建的角色列表
        if (Objects.equals(Constant.SUPER_ADMIN, getUserId())) {
            params.put("createUserId", getUserId());
        }
        PageUtils page = sysRoleService.queryPage(params);
        return R.ok().setData(page);
    }

    /**
     * 角色列表
     */
    @GetMapping("/select")
    @PreAuthorize("hasAuthority('sys:role:list')")
    public R select() {
        Map<String, Object> map = new HashMap<>();

        //如果不是超级管理员，则只查询自己所拥有的角色列表
        if (Objects.equals(Constant.SUPER_ADMIN, getUserId())) {
            map.put("create_user_id", getUserId());
        }
        List<SysRole> list = sysRoleService.listByMap(map);

        return R.ok().setData(list);
    }

    /**
     * 角色信息
     */
    @GetMapping("/info/{roleId}")
    @PreAuthorize("hasAuthority('sys:role:list')")
    public R info(@PathVariable("roleId") Long roleId) {
        SysRole role = sysRoleService.getById(roleId);
        //查询角色对应的菜单
        List<Long> menuIdList = sysRoleMenuService.queryMenuIdList(roleId);
        role.setMenuIdList(menuIdList);
        return R.ok().setData(role);
    }

    /**
     * 保存角色
     */
    @SysLog("新增")
    @PostMapping("/save")
    @PreAuthorize("hasAuthority('sys:role:inset')")
    public R save(@RequestBody @Validated(AddGroup.class) SysRole role) {
        role.setCreateUserId(getUserId());
        sysRoleService.saveRole(role);
        return R.ok();
    }

    /**
     * 修改角色
     */
    @SysLog("修改角色")
    @PostMapping("/update")
    @PreAuthorize("hasAuthority('sys:role:update')")
    public R update(@RequestBody @Validated(UpdateGroup.class) SysRole role) {
        ValidatorUtils.validateEntity(role);
        role.setCreateUserId(getUserId());
        sysRoleService.update(role);
        return R.ok();
    }

    /**
     * 删除角色
     */
    @SysLog("删除角色")
    @PostMapping("/delete")
    @PreAuthorize("hasAuthority('sys:role:delete')")
    public R delete(@RequestBody Long[] roleIds) {
        sysRoleService.deleteBatch(roleIds);
        return R.ok();
    }
}
