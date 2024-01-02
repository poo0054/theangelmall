/**
 * Copyright (c) 2016-2019 人人开源 All rights reserved.
 *
 * https://www.renren.io
 *
 * 版权所有，侵权必究！
 */

package io.renren.service;


import com.baomidou.mybatisplus.extension.service.IService;
import io.renren.pojo.entity.sysLog;
import io.renren.utils.PageUtils;

import java.util.Map;


/**
 * 系统日志
 *
 * @author Mark sunlightcs@gmail.com
 */
public interface SysLogService extends IService<sysLog> {

    PageUtils queryPage(Map<String, Object> params);

}