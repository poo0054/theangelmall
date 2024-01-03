/**
 * Copyright (c) 2016-2019 人人开源 All rights reserved.
 * <p>
 * https://www.renren.io
 * <p>
 * 版权所有，侵权必究！
 */

package com.themall.admin.service;


import com.baomidou.mybatisplus.extension.service.IService;
import com.themall.admin.pojo.entity.sysLog;
import com.themall.admin.utils.PageUtils;

import java.util.Map;


/**
 * 系统日志
 *
 * @author Mark sunlightcs@gmail.com
 */
public interface SysLogService extends IService<sysLog> {

    PageUtils queryPage(Map<String, Object> params);

}
