/**
 * Copyright (c) 2016-2019 人人开源 All rights reserved.
 * <p>
 * https://www.renren.io
 * <p>
 * 版权所有，侵权必究！
 */

package io.renren.modules.sys.service.impl;


import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import io.renren.modules.sys.dao.SysMenuDao;
import io.renren.modules.sys.entity.SysMenuEntity;
import io.renren.modules.sys.service.SysMenuService;
import io.renren.modules.sys.service.SysRoleMenuService;
import io.renren.utils.Constant;
import io.renren.utils.MapUtils;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;


@Service("sysMenuService")
public class SysMenuServiceImpl extends ServiceImpl<SysMenuDao, SysMenuEntity> implements SysMenuService {

//    private SysUserService sysUserService;

    private SysRoleMenuService sysRoleMenuService;

    @Autowired
    public void setSysRoleMenuService(SysRoleMenuService sysRoleMenuService) {
        this.sysRoleMenuService = sysRoleMenuService;
    }


    @Override
    public List<SysMenuEntity> queryListParentId(Long parentId, List<Long> menuIdList) {
        List<SysMenuEntity> menuList = queryListParentId(parentId);
        if (menuIdList == null) {
            return menuList;
        }

        List<SysMenuEntity> userMenuList = new ArrayList<>();
        for (SysMenuEntity menu : menuList) {
            if (menuIdList.contains(menu.getMenuId())) {
                userMenuList.add(menu);
            }
        }
        return userMenuList;
    }

    @Override
    public List<SysMenuEntity> queryListParentId(Long parentId) {
        return baseMapper.queryListParentId(parentId);
    }

    @Override
    public List<SysMenuEntity> queryNotButtonList() {
        return baseMapper.queryNotButtonList();
    }

    @Override
    public List<SysMenuEntity> getUserMenuList(Long userId) {
        //系统管理员，拥有最高权限
        if (Objects.equals(Constant.SUPER_ADMIN, userId)) {
            return getAllMenuList(null);
        }

        //用户菜单列表
        List<Long> menuIdList = this.queryAllMenuId(userId);
        return getAllMenuList(menuIdList);
    }

    @Override
    public void delete(Long menuId) {
        //删除菜单
        this.removeById(menuId);
        //删除菜单与角色关联
        sysRoleMenuService.removeByMap(new MapUtils().put("menu_id", menuId));
    }

    @Override
    public List<SysMenuEntity> listByUserId(Long userId) {
        if (Objects.equals(userId, Constant.SUPER_ADMIN)) {
            return this.list();
        }
        //用户菜单列表
        List<Long> menuIdList = this.queryAllMenuId(userId);
        return listById(menuIdList);
    }

    @Override
    public List<Long> queryAllMenuId(Long createUserId) {
        return baseMapper.queryAllMenuId(createUserId);
    }

    private List<SysMenuEntity> listById(List<Long> menuIdList) {
        List<SysMenuEntity> sysMenuEntities = this.listByIds(menuIdList);
        if (ObjectUtils.isNotEmpty(sysMenuEntities)) {
            for (SysMenuEntity sysMenuEntity : sysMenuEntities) {
                //
                if (sysMenuEntity.getType() <= Constant.MenuType.BUTTON.getValue()) {
                    sysMenuEntities.addAll(listById(Collections.singletonList(sysMenuEntity.getParentId())));
                }
            }
        }
        return sysMenuEntities;
    }

    /**
     * 获取所有菜单列表
     */
    private List<SysMenuEntity> getAllMenuList(List<Long> menuIdList) {
        //查询根菜单列表
        List<SysMenuEntity> menuList = queryListParentId(0L, menuIdList);
        //递归获取子菜单
        getMenuTreeList(menuList, menuIdList);

        return menuList;
    }

    /**
     * 递归
     */
    private List<SysMenuEntity> getMenuTreeList(List<SysMenuEntity> menuList, List<Long> menuIdList) {
        List<SysMenuEntity> subMenuList = new ArrayList<SysMenuEntity>();

        for (SysMenuEntity entity : menuList) {
            //目录
            if (entity.getType() == Constant.MenuType.CATALOG.getValue()) {
                entity.setList(getMenuTreeList(queryListParentId(entity.getMenuId(), menuIdList), menuIdList));
            }
            subMenuList.add(entity);
        }

        return subMenuList;
    }
}
