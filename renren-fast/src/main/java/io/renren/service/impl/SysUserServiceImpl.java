/**
 * Copyright (c) 2016-2019 人人开源 All rights reserved.
 * <p>
 * https://www.renren.io
 * <p>
 * 版权所有，侵权必究！
 */

package io.renren.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.themall.model.constants.Constant;
import com.themall.model.entity.SysUser;
import com.themall.model.exception.RRException;
import io.renren.dao.SysUserDao;
import io.renren.service.SysMenuService;
import io.renren.service.SysRoleService;
import io.renren.service.SysUserRoleService;
import io.renren.service.SysUserService;
import io.renren.utils.PageUtils;
import io.renren.utils.Query;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;


/**
 * 系统用户
 *
 * @author Mark sunlightcs@gmail.com
 */
@Service("sysUserService")
public class SysUserServiceImpl extends ServiceImpl<SysUserDao, SysUser> implements SysUserService {

    private SysUserRoleService sysUserRoleService;

    private SysRoleService sysRoleService;

    private SysMenuService sysMenuService;
    private PasswordEncoder passwordEncoder;

    @Autowired
    public void setPasswordEncoder(PasswordEncoder passwordEncoder) {
        this.passwordEncoder = passwordEncoder;
    }

    @Autowired
    public void setSysUserRoleService(SysUserRoleService sysUserRoleService) {
        this.sysUserRoleService = sysUserRoleService;
    }

    @Autowired
    public void setSysRoleService(SysRoleService sysRoleService) {
        this.sysRoleService = sysRoleService;
    }

    @Autowired
    public void setSysMenuService(SysMenuService sysMenuService) {
        this.sysMenuService = sysMenuService;
    }

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        Object username = params.get("username");
        Object createUserId = params.get("createUserId");
        LambdaQueryWrapper<SysUser> lambdaQueryWrapper = Wrappers.lambdaQuery(SysUser.class);
        lambdaQueryWrapper.like(ObjectUtils.isNotEmpty(username), SysUser::getUsername, username);
        lambdaQueryWrapper.eq(ObjectUtils.isNotEmpty(createUserId), SysUser::getCreateUserId, createUserId);
        IPage<SysUser> page = this.page(
                new Query<SysUser>().getPage(params),
                lambdaQueryWrapper
        );
        return new PageUtils(page);
    }

    @Override
    public List<String> queryAllPerms(Long userId) {
        return baseMapper.queryAllPerms(userId);
    }

    @Override
    public List<Long> queryAllMenuId(Long userId) {
        return baseMapper.queryAllMenuId(userId);
    }

    @Override
    public SysUser queryByUserName(String username) {
        return baseMapper.queryByUserName(username);
    }

    @Override
    @Transactional
    public void saveUser(SysUser user) {
        user.setCreateTime(new Date());
        if (StringUtils.isBlank(user.getPassword())) {
            user.setPassword(null);
        } else {
            user.setPassword(passwordEncoder.encode(user.getPassword()));
        }
        this.save(user);

        //检查角色是否越权
        checkRole(user);

        //保存用户与角色关系
        sysUserRoleService.saveOrUpdate(user.getUserId(), user.getRoleIdList());
    }

    @Override
    @Transactional
    public void update(SysUser user) {
        if (StringUtils.isBlank(user.getPassword())) {
            user.setPassword(null);
        } else {
            user.setPassword(passwordEncoder.encode(user.getPassword()));
        }
        this.updateById(user);

        //检查角色是否越权
        checkRole(user);

        //保存用户与角色关系
        sysUserRoleService.saveOrUpdate(user.getUserId(), user.getRoleIdList());
    }

    @Override
    public void deleteBatch(Long[] userId) {
        this.removeByIds(Arrays.asList(userId));
    }

    @Override
    public boolean updatePassword(Long userId, String password, String newPassword) {
        SysUser sysUserEntity = this.getById(userId);
        if (ObjectUtils.isEmpty(sysUserEntity)) {
            return false;
        }
        if (!passwordEncoder.matches(password, sysUserEntity.getPassword())) {
            return false;
        }
        SysUser userEntity = new SysUser();
        userEntity.setPassword(passwordEncoder.encode(newPassword));
        userEntity.setUserId(sysUserEntity.getUserId());
        return this.updateById(userEntity);
    }

    @Override
    public SysUser getByUserName(String username) {
        LambdaQueryWrapper<SysUser> queryWrapper = Wrappers.lambdaQuery(SysUser.class);
        queryWrapper.eq(SysUser::getUsername, username);
        return this.getOne(queryWrapper);
    }


    /**
     * 检查角色是否越权
     */
    private void checkRole(SysUser user) {
        if (user.getRoleIdList() == null || user.getRoleIdList().isEmpty()) {
            return;
        }

        //如果不是超级管理员，则需要判断用户的角色是否自己创建
        if (Objects.equals(Constant.SUPER_ADMIN, user.getCreateUserId())) {
            return;
        }

        //查询用户创建的角色列表
        List<Long> roleIdList = sysRoleService.queryRoleIdList(user.getCreateUserId());

        //判断是否越权
        if (!roleIdList.containsAll(user.getRoleIdList())) {
            throw new RRException("新增用户所选角色，不是本人创建");
        }
    }
}