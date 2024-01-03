/**
 * Copyright (c) 2016-2019 人人开源 All rights reserved.
 * <p>
 * https://www.renren.io
 * <p>
 * 版权所有，侵权必究！
 */

package com.themall.admin.controller;


import com.themall.admin.annotation.SysLog;
import com.themall.admin.pojo.entity.SysConfig;
import com.themall.admin.service.SysConfigService;
import com.themall.admin.utils.PageUtils;
import com.themall.model.entity.R;
import com.themall.model.validator.ValidatorUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

/**
 * 系统配置信息
 *
 * @author Mark sunlightcs@gmail.com
 */
@RestController
@RequestMapping("/sys/config")
public class SysConfigController extends AbstractController {
    private SysConfigService sysConfigService;

    @Autowired
    public void setSysConfigService(SysConfigService sysConfigService) {
        this.sysConfigService = sysConfigService;
    }

    /**
     * 所有配置列表
     */
    @GetMapping("/list")
    @PreAuthorize("hasAuthority('sys:config:list')")
    public R list(@RequestParam Map<String, Object> params) {
        PageUtils page = sysConfigService.queryPage(params);
        return R.ok().put("page", page);
    }


    /**
     * 配置信息
     */
    @GetMapping("/info/{id}")
    @PreAuthorize("hasAuthority('sys:config:list')")
    public R info(@PathVariable("id") Long id) {
        SysConfig config = sysConfigService.getById(id);

        return R.ok().put("config", config);
    }

    /**
     * 保存配置
     */
    @SysLog("保存配置")
    @PostMapping("/save")
    @PreAuthorize("hasAuthority('sys:config:save')")
    public R save(@RequestBody SysConfig config) {
        ValidatorUtils.validateEntity(config);

        sysConfigService.saveConfig(config);

        return R.ok();
    }

    /**
     * 修改配置
     */
    @SysLog("修改配置")
    @PostMapping("/update")
    @PreAuthorize("hasAuthority('sys:config:update')")
    public R update(@RequestBody SysConfig config) {
        ValidatorUtils.validateEntity(config);

        sysConfigService.update(config);

        return R.ok();
    }

    /**
     * 删除配置
     */
    @SysLog("删除配置")
    @PostMapping("/delete")
    @PreAuthorize("hasAuthority('sys:config:delete')")
    public R delete(@RequestBody Long[] ids) {
        sysConfigService.deleteBatch(ids);
        return R.ok();
    }

}
