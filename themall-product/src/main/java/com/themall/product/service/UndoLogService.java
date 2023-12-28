package com.themall.product.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.themall.common.utils.PageUtils;
import com.themall.product.pojo.entity.UndoLogEntity;

import java.util.Map;

/**
 *
 *
 * @author theangel
 * @email poo0054@outlook.com
 * @date 2021-06-09 20:47:29
 */
public interface UndoLogService extends IService<UndoLogEntity> {

    PageUtils queryPage(Map<String, Object> params);
}

