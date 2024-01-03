/**
 * Copyright (c) 2016-2019 人人开源 All rights reserved.
 * <p>
 * https://www.renren.io
 * <p>
 * 版权所有，侵权必究！
 */

package com.themall.admin.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.themall.admin.dao.SysConfigDao;
import com.themall.admin.pojo.entity.SysConfig;
import com.themall.admin.service.SysConfigService;
import com.themall.admin.utils.PageUtils;
import com.themall.admin.utils.Query;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.Map;

@Service
public class SysConfigServiceImpl extends ServiceImpl<SysConfigDao, SysConfig> implements SysConfigService {


    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        String paramKey = (String) params.get("paramKey");

        IPage<SysConfig> page = this.page(
                new Query<SysConfig>().getPage(params),
                new QueryWrapper<SysConfig>()
                        .like(StringUtils.isNotBlank(paramKey), "param_key", paramKey)
                        .eq("status", 1)
        );

        return new PageUtils(page);
    }

    @Override
    public void saveConfig(SysConfig config) {
        this.save(config);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void update(SysConfig config) {
        this.updateById(config);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void updateValueByKey(String key, String value) {
        baseMapper.updateValueByKey(key, value);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void deleteBatch(Long[] ids) {
        for (Long id : ids) {
            SysConfig config = this.getById(id);
        }

        this.removeByIds(Arrays.asList(ids));
    }

}
