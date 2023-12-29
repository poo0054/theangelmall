/**
 * Copyright (c) 2016-2019 人人开源 All rights reserved.
 * <p>
 * https://www.renren.io
 * <p>
 * 版权所有，侵权必究！
 */

package io.renren.service.impl;


import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.themall.common.utils.JsonUtils;
import com.themall.model.constants.Constant;
import com.themall.model.entity.SysMenu;
import com.themall.model.enums.MenuTypeEnum;
import io.renren.dao.SysMenuDao;
import io.renren.pojo.vo.MenuVo;
import io.renren.service.SysMenuService;
import org.apache.commons.lang3.ObjectUtils;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;


@Service("sysMenuService")
public class SysMenuServiceImpl extends ServiceImpl<SysMenuDao, SysMenu> implements SysMenuService {

    @NotNull
    private static MenuVo toMenuVo(SysMenu sysMenuEntity) {
        MenuVo convert = JsonUtils.convert(sysMenuEntity, MenuVo.class);
        MenuVo.Meta meta = JsonUtils.convert(sysMenuEntity, MenuVo.Meta.class);
        convert.setMeta(meta);
        return convert;
    }

    @NotNull
    private static SysMenu toSysMenu(MenuVo menuVo) {
        SysMenu sysMenu = JsonUtils.convert(menuVo, SysMenu.class);
        BeanUtils.copyProperties(menuVo.getMeta(), sysMenu);
        return sysMenu;
    }

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
    public boolean saveMenuVo(MenuVo menuVo) {
        SysMenu sysMenu = toSysMenu(menuVo);
        return this.save(sysMenu);
    }

    @Override
    public boolean updateMenuVo(MenuVo menuVo) {
        SysMenu sysMenu = toSysMenu(menuVo);
        return this.updateById(sysMenu);
    }

    @NotNull
    private List<MenuVo> toMenuVos(List<SysMenu> list) {
        return list.stream()
                .filter(sysMenuEntity -> ObjectUtils.isEmpty(sysMenuEntity.getParentId()))
                .filter(sysMenu -> !Objects.equals(sysMenu.getType(), MenuTypeEnum.BUTTON))
                .map(sysMenuEntity -> {
                    MenuVo convert = toMenuVo(sysMenuEntity);
                    convert.setChildren(children(sysMenuEntity.getId(), list));
                    return convert;
                }).collect(Collectors.toList());
    }

    private List<MenuVo> children(Long id, List<SysMenu> list) {
        return list.stream()
                .filter(sysMenuEntity -> Objects.equals(sysMenuEntity.getParentId(), id))
                .map(sysMenuEntity -> {
                    MenuVo convert = toMenuVo(sysMenuEntity);
                    convert.setChildren(children(sysMenuEntity.getId(), list));
                    return convert;
                })
                .collect(Collectors.toList());
    }


}
