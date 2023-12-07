/**
 * Copyright (c) 2016-2019 人人开源 All rights reserved.
 * <p>
 * https://www.renren.io
 * <p>
 * 版权所有，侵权必究！
 */

package io.renren.modules.sys.controller;

import com.themall.model.constants.HttpStatusEnum;
import com.themall.model.entity.R;
import com.themall.model.entity.SysUserEntity;
import com.themall.model.validator.Assert;
import com.themall.model.validator.ValidatorUtils;
import com.themall.model.validator.group.AddGroup;
import com.themall.model.validator.group.UpdateGroup;
import io.renren.annotation.SysLog;
import io.renren.modules.sys.form.PasswordForm;
import io.renren.modules.sys.service.SysUserRoleService;
import io.renren.modules.sys.service.SysUserService;
import io.renren.utils.Constant;
import io.renren.utils.PageUtils;
import org.apache.commons.lang.ArrayUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.Objects;

/**
 * 系统用户
 *
 * @author Mark sunlightcs@gmail.com
 */
@RestController
@RequestMapping("/sys/user")
public class SysUserController extends AbstractController {
    private SysUserService sysUserService;
    private SysUserRoleService sysUserRoleService;
    private PasswordEncoder passwordEncoder;

    @Autowired
    public void setSysUserService(SysUserService sysUserService) {
        this.sysUserService = sysUserService;
    }

    @Autowired
    public void setSysUserRoleService(SysUserRoleService sysUserRoleService) {
        this.sysUserRoleService = sysUserRoleService;
    }

    @Autowired
    public void setPasswordEncoder(PasswordEncoder passwordEncoder) {
        this.passwordEncoder = passwordEncoder;
    }

    /**
     * 所有用户列表
     */
    @GetMapping("/list")
    @PreAuthorize("hasAuthority('sys:user:list')")
    public R list(@RequestParam Map<String, Object> params) {
        //只有超级管理员，才能查看所有管理员列表
        if (Objects.equals(Constant.SUPER_ADMIN, getUserId())) {
            params.put("createUserId", getUserId());
        }
        PageUtils page = sysUserService.queryPage(params);

        return R.ok().put("page", page);
    }

    /**
     * 获取登录的用户信息
     */
    @GetMapping("/info")
    @PreAuthorize("hasAuthority('sys:user:list')")
    public R info() {
        return R.ok().put("user", getUser());
    }

    /**
     * 修改登录用户密码
     */
    @SysLog("修改密码")
    @PostMapping("/password")
    @PreAuthorize("hasAuthority('sys:user:update')")
    public R password(@RequestBody PasswordForm form) {
        Assert.isBlank(form.getNewPassword(), "新密码不为能空");
        String password = passwordEncoder.encode(form.getPassword());
        String newPassword = passwordEncoder.encode(form.getNewPassword());
        //更新密码
        boolean flag = sysUserService.updatePassword(getUserId(), password, newPassword);
        if (!flag) {
            return R.error(HttpStatusEnum.USER_ERROR_A0130.getCode(), "原密码不正确");
        }

        return R.ok();
    }

    /**
     * 用户信息
     */
    @GetMapping("/info/{userId}")
    @PreAuthorize("hasAuthority('sys:user:list')")
    public R info(@PathVariable("userId") Long userId) {
        SysUserEntity user = sysUserService.getById(userId);

        //获取用户所属的角色列表
        List<Long> roleIdList = sysUserRoleService.queryRoleIdList(userId);
        user.setRoleIdList(roleIdList);

        return R.ok().put("user", user);
    }

    /**
     * 保存用户
     */
    @SysLog("保存用户")
    @PostMapping("/save")
    @PreAuthorize("hasAuthority('sys:user:inset')")
    public R save(@RequestBody SysUserEntity user) {
        ValidatorUtils.validateEntity(user, AddGroup.class);

        user.setCreateUserId(getUserId());
        sysUserService.saveUser(user);

        return R.ok();
    }

    /**
     * 修改用户
     */
    @SysLog("修改用户")
    @PostMapping("/update")
    @PreAuthorize("hasAuthority('sys:user:update')")
    public R update(@RequestBody SysUserEntity user) {
        ValidatorUtils.validateEntity(user, UpdateGroup.class);
        user.setCreateUserId(getUserId());
        sysUserService.update(user);
        return R.ok();
    }

    /**
     * 删除用户
     */
    @SysLog("删除用户")
    @PostMapping("/delete")
    @PreAuthorize("hasAuthority('sys:user:delete')")
    public R delete(@RequestBody Long[] userIds) {
        if (ArrayUtils.contains(userIds, 1L)) {
            return R.error(HttpStatusEnum.USER_ERROR_A0440.getCode(), "系统管理员不能删除");
        }
        if (ArrayUtils.contains(userIds, getUserId())) {
            return R.error(HttpStatusEnum.USER_ERROR_A0440.getCode(), "当前用户不能删除");
        }
        sysUserService.deleteBatch(userIds);
        return R.ok();
    }
}
