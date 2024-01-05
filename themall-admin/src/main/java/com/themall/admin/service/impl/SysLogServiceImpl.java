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
import com.themall.admin.dao.SysLogDao;
import com.themall.admin.pojo.entity.sysLog;
import com.themall.admin.service.SysLogService;
import com.themall.admin.utils.PageUtils;
import com.themall.admin.utils.Query;
import org.apache.commons.lang3.StringUtils;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.util.Map;


@Service("sysLogService")
public class SysLogServiceImpl extends ServiceImpl<SysLogDao, sysLog> implements SysLogService {

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        String key = (String) params.get("key");

        IPage<sysLog> page = this.page(
                new Query<sysLog>().getPage(params),
                new QueryWrapper<sysLog>().like(StringUtils.isNotBlank(key), "username", key)
        );

        return new PageUtils(page);
    }


    @Override
    @Async
    public void asyncSave(sysLog operLog) {
        this.save(operLog);
    }
}
