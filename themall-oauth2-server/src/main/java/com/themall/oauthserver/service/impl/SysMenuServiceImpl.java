/**
 * Copyright (c) 2016-2019 人人开源 All rights reserved.
 * <p>
 * https://www.renren.io
 * <p>
 * 版权所有，侵权必究！
 */

package com.themall.oauthserver.service.impl;


import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.themall.model.constants.Constant;
import com.themall.model.entity.SysMenuEntity;
import com.themall.oauthserver.dao.SysMenuDao;
import com.themall.oauthserver.service.SysMenuService;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;


@Service("sysMenuService")
public class SysMenuServiceImpl extends ServiceImpl<SysMenuDao, SysMenuEntity> implements SysMenuService {


    @Override
    public List<SysMenuEntity> listByUserId(Long userId) {
        if (Objects.equals(userId, Constant.SUPER_ADMIN)) {
            return this.list();
        }
        //用户菜单列表
        List<Long> menuIdList = this.queryAllMenuId(userId);
        return listById(menuIdList);
    }

    private List<Long> queryAllMenuId(Long createUserId) {
        return baseMapper.queryAllMenuId(createUserId);
    }

    private List<SysMenuEntity> listById(List<Long> menuIdList) {
        List<SysMenuEntity> sysMenuEntities = null;
        if (ObjectUtils.isNotEmpty(menuIdList)) {
            sysMenuEntities = this.listByIds(menuIdList);
            if (ObjectUtils.isNotEmpty(sysMenuEntities)) {
                //获取下级菜单
                List<Long> menuId = sysMenuEntities.stream().map(SysMenuEntity::getMenuId).collect(Collectors.toList());
                //子集 当前权限必须为选中才能给予
//                sysMenuEntities.addAll(getChild(menuId));
            }
        }
        return sysMenuEntities;
    }

    private Collection<? extends SysMenuEntity> getChild(List<Long> longs) {
        if (ObjectUtils.isEmpty(longs)) {
            return Collections.emptyList();
        }
        LambdaQueryWrapper<SysMenuEntity> queryWrapper = Wrappers.lambdaQuery(SysMenuEntity.class);
        queryWrapper.in(SysMenuEntity::getParentId, longs);
        List<SysMenuEntity> listed = this.list(queryWrapper);
        if (ObjectUtils.isEmpty(listed)) {
            return Collections.emptyList();
        }
        listed.addAll(getChild(listed.stream().map(SysMenuEntity::getMenuId).collect(Collectors.toList())));
        return listed;
    }
}
