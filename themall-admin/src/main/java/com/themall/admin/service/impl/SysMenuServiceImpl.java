/**
 * Copyright (c) 2016-2019 人人开源 All rights reserved.
 * <p>
 * https://www.renren.io
 * <p>
 * 版权所有，侵权必究！
 */

package com.themall.admin.service.impl;


import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.google.common.collect.Lists;
import com.themall.admin.dao.SysMenuDao;
import com.themall.admin.enums.DropType;
import com.themall.admin.pojo.form.NodeDropMenuVo;
import com.themall.admin.pojo.vo.MenuVo;
import com.themall.admin.service.SysMenuService;
import com.themall.common.utils.JsonUtils;
import com.themall.model.constants.Constant;
import com.themall.model.entity.SysMenu;
import com.themall.model.enums.HttpStatusEnum;
import com.themall.model.enums.MenuTypeEnum;
import com.themall.model.exception.RRException;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;


@Service("sysMenuService")
public class SysMenuServiceImpl extends ServiceImpl<SysMenuDao, SysMenu> implements SysMenuService {


    @Override
    public List<MenuVo> getUserMenuList(Long userId) {
        //系统管理员，获取所有权限
        if (Objects.equals(Constant.SUPER_ADMIN, userId)) {
            LambdaQueryWrapper<SysMenu> queryWrapper = Wrappers.lambdaQuery(SysMenu.class);
            queryWrapper.ne(SysMenu::getType, MenuTypeEnum.BUTTON);
            List<SysMenu> list = this.list(queryWrapper);
            return toMenuVos(list);
        }
        List<Long> menuIds = baseMapper.queryAllMenuId(userId);
        if (ObjectUtils.isEmpty(menuIds)) {
            return Collections.emptyList();
        }
        List<SysMenu> sysMenuEntities = this.listByIds(menuIds);
        if (ObjectUtils.isEmpty(sysMenuEntities)) {
            return Collections.emptyList();
        }
        return toMenuVos(sysMenuEntities);
    }

    @Override
    public List<Long> queryAllMenuId(Long createUserId) {
        return baseMapper.queryAllMenuId(createUserId);
    }

    @Override
    public List<MenuVo> getList() {
        return toMenuVos(this.list());
    }

    @Override
    public String saveMenuVo(MenuVo menuVo) {
        SysMenu sysMenu = toSysMenu(menuVo);
        this.save(sysMenu);
        return sysMenu.getId().toString();
    }

    @Override
    public boolean updateMenuVo(MenuVo menuVo) {
        SysMenu sysMenu = toSysMenu(menuVo);
        return this.updateById(sysMenu);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean delete(List<String> idList) {
        LambdaQueryWrapper<SysMenu> query = Wrappers.lambdaQuery(SysMenu.class);
        query.in(SysMenu::getParentId, idList);
        List<SysMenu> list = this.list(query);
        if (ObjectUtils.isNotEmpty(list)) {
            throw new RRException(HttpStatusEnum.USER_ERROR_A0400, "需要先删除下级目录");
        }
        //TODO 需要先删除权限
        return removeByIds(idList);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean nodeDrop(NodeDropMenuVo dropMenuVo) {
        SysMenu afterNode = this.getById(dropMenuVo.getAfterNodeId());
        SysMenu node = this.getById(dropMenuVo.getNodeId());
        List<SysMenu> sysMenu = buildSysMenu(node, dropMenuVo.getDropType(), afterNode);
        return this.updateBatchById(sysMenu);
    }

    private static MenuVo toMenuVo(SysMenu sysMenuEntity) {
        MenuVo convert = JsonUtils.convert(sysMenuEntity, MenuVo.class);
        MenuVo.Meta meta = JsonUtils.convert(sysMenuEntity, MenuVo.Meta.class);
        convert.setMeta(meta);
        return convert;
    }

    private static SysMenu toSysMenu(MenuVo menuVo) {
        SysMenu sysMenu = JsonUtils.convert(menuVo, SysMenu.class);
        BeanUtils.copyProperties(menuVo.getMeta(), sysMenu);
        return sysMenu;
    }

    private List<SysMenu> buildSysMenu(SysMenu node, DropType dropType, SysMenu afterNode) {
        Integer orderNum = afterNode.getOrderNum();
        LambdaQueryWrapper<SysMenu> queryWrapper = Wrappers.lambdaQuery(SysMenu.class);
        queryWrapper.eq(ObjectUtils.isNotEmpty(afterNode.getParentId()), SysMenu::getParentId, afterNode.getParentId());
        queryWrapper.isNull(ObjectUtils.isEmpty(afterNode.getParentId()), SysMenu::getParentId);
        switch (dropType) {
            case BEFORE:
                //前面
                node.setParentId(afterNode.getParentId());
                queryWrapper.ge(SysMenu::getOrderNum, orderNum);
                node.setOrderNum(orderNum);
                break;
            case AFTER:
                //后面
                node.setParentId(afterNode.getParentId());
                orderNum = orderNum + 1;
                queryWrapper.ge(SysMenu::getOrderNum, orderNum);
                node.setOrderNum(orderNum);
                break;
            case INNER:
                //里面
                node.setParentId(afterNode.getId());
                node.setOrderNum(0);
                break;
            default:
                break;
        }
        List<SysMenu> list = this.list(queryWrapper);
        //  解决为空的问题
        if (ObjectUtils.isEmpty(node.getParentId())) {
            LambdaUpdateWrapper<SysMenu> lambdaUpdateWrapper = Wrappers.lambdaUpdate(SysMenu.class);
            lambdaUpdateWrapper.set(SysMenu::getParentId, null);
            lambdaUpdateWrapper.eq(SysMenu::getId, node.getId());
            this.update(lambdaUpdateWrapper);
        }
        if (ObjectUtils.isEmpty(list)) {
            return Lists.newArrayList(node);
        }
        return tree(node, list, orderNum);
    }

    private List<SysMenu> tree(SysMenu node, List<SysMenu> sysMenu, Integer orderNum) {
        //首先根据orderNum排序
        List<SysMenu> sysMenus = sysMenu.stream()
                .sorted(Comparator.comparing(SysMenu::getOrderNum))
                .filter(sysMenu1 -> !Objects.equals(sysMenu1.getId(), node.getId()))
                .collect(Collectors.toList());
        //当前第一个
        sysMenus.add(0, node);
        for (int i = 0; i < sysMenus.size(); i++) {
            SysMenu sysMenu1 = sysMenus.get(i);
            sysMenu1.setOrderNum(orderNum + i);
        }
        return sysMenus;
    }

    private List<MenuVo> toMenuVos(List<SysMenu> list) {
        return list.stream()
                .filter(sysMenuEntity -> ObjectUtils.isEmpty(sysMenuEntity.getParentId()))
                .filter(sysMenu -> !Objects.equals(sysMenu.getType(), MenuTypeEnum.BUTTON))
                .sorted(Comparator.comparing(SysMenu::getOrderNum))
                .map(sysMenuEntity -> {
                    MenuVo convert = toMenuVo(sysMenuEntity);
                    List<MenuVo> children = children(sysMenuEntity.getId(), list);
                    if (ObjectUtils.isNotEmpty(children)) {
                        convert.setChildren(children);
                    }
                    return convert;
                }).collect(Collectors.toList());
    }

    private List<MenuVo> children(Long id, List<SysMenu> list) {
        return list.stream()
                .filter(sysMenuEntity -> Objects.equals(sysMenuEntity.getParentId(), id))
                .map(sysMenuEntity -> {
                    MenuVo convert = toMenuVo(sysMenuEntity);
                    List<MenuVo> children = children(sysMenuEntity.getId(), list);
                    if (ObjectUtils.isNotEmpty(children)) {
                        convert.setChildren(children);
                    }
                    return convert;
                })
                .collect(Collectors.toList());
    }


}
