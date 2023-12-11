/**
 * Copyright (c) 2016-2019 人人开源 All rights reserved.
 * <p>
 * https://www.renren.io
 * <p>
 * 版权所有，侵权必究！
 */

package com.themall.oauthserver.service.impl;


import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.themall.model.constants.Constant;
import com.themall.model.entity.SysMenuEntity;
import com.themall.oauthserver.dao.SysMenuDao;
import com.themall.oauthserver.service.SysMenuService;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.Objects;


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
}
