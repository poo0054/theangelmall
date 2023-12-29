/**
 * Copyright (c) 2016-2019 人人开源 All rights reserved.
 * <p>
 * https://www.renren.io
 * <p>
 * 版权所有，侵权必究！
 */

package io.renren.controller;

import com.themall.model.constants.Constant;
import com.themall.model.entity.R;
import com.themall.model.entity.SysUser;
import com.themall.model.enums.HttpStatusEnum;
import com.themall.model.validator.Assert;
import com.themall.model.validator.group.AddGroup;
import com.themall.model.validator.group.UpdateGroup;
import io.renren.annotation.SysLog;
import io.renren.pojo.form.PasswordForm;
import io.renren.service.SysUserRoleService;
import io.renren.service.SysUserService;
import io.renren.utils.PageUtils;
import org.apache.commons.lang.ArrayUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
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

    @Autowired
    public void setSysUserService(SysUserService sysUserService) {
        this.sysUserService = sysUserService;
    }

    @Autowired
    public void setSysUserRoleService(SysUserRoleService sysUserRoleService) {
        this.sysUserRoleService = sysUserRoleService;
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
        return R.ok().setData(page);
    }

    /**
     * 获取登录的用户信息
     */
    @GetMapping("/info")
    public R info() {
        return R.ok().setData(getUser());
    }

    /**
     * 修改登录用户密码
     */
    @SysLog("修改密码")
    @PostMapping("/password")
    public R password(@RequestBody @Validated PasswordForm form) {
        Assert.isBlank(form.getNewPassword(), "新密码不为能空");
        //更新密码
        boolean flag = sysUserService.updatePassword(getUserId(), form.getPassword(), form.getNewPassword());
        if (!flag) {
            return R.error(HttpStatusEnum.USER_ERROR_A0120);
        }
        return R.ok();
    }

    /**
     * 用户信息
     */
    @GetMapping("/info/{userId}")
    @PreAuthorize("hasAuthority('sys:user:list')")
    public R info(@PathVariable("userId") Long userId) {
        SysUser user = sysUserService.getById(userId);

        //获取用户所属的角色列表
        List<Long> roleIdList = sysUserRoleService.queryRoleIdList(userId);
        user.setRoleIdList(roleIdList);

        return R.ok().setData(user);
    }

    /**
     * 保存用户
     */
    @SysLog("保存用户")
    @PostMapping("/save")
    @PreAuthorize("hasAuthority('sys:user:save')")
    public R save(@RequestBody @Validated(AddGroup.class) SysUser user) {
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
    public R update(@RequestBody @Validated(UpdateGroup.class) SysUser user) {
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
            return R.error(HttpStatusEnum.USER_ERROR_A0440, "系统管理员不能删除");
        }
        if (ArrayUtils.contains(userIds, getUserId())) {
            return R.error(HttpStatusEnum.USER_ERROR_A0440, "当前用户不能删除");
        }
        sysUserService.deleteBatch(userIds);
        return R.ok();
    }
}
