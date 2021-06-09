package com.theangel.themall.product.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.theangel.common.utils.PageUtils;
import com.theangel.themall.product.entity.UndoLogEntity;

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

