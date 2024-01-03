/**
 * Copyright (c) 2016-2019 人人开源 All rights reserved.
 * <p>
 * https://www.renren.io
 * <p>
 * 版权所有，侵权必究！
 */

package com.themall.admin.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.themall.admin.pojo.entity.SysConfig;
import com.themall.admin.utils.PageUtils;

import java.util.Map;

/**
 * 系统配置信息
 *
 * @author Mark sunlightcs@gmail.com
 */
public interface SysConfigService extends IService<SysConfig> {

    PageUtils queryPage(Map<String, Object> params);

    /**
     * 保存配置信息
     */
    public void saveConfig(SysConfig config);

    /**
     * 更新配置信息
     */
    public void update(SysConfig config);

    /**
     * 根据key，更新value
     */
    public void updateValueByKey(String key, String value);

    /**
     * 删除配置信息
     */
    public void deleteBatch(Long[] ids);

}
