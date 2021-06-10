package com.theangel.themall.ware.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.theangel.common.utils.PageUtils;
import com.theangel.themall.ware.entity.PurchaseDetailEntity;

import java.util.Map;

/**
 * 
 *
 * @author theangel
 * @email poo0054@outlook.com
 * @date 2021-06-10 20:02:13
 */
public interface PurchaseDetailService extends IService<PurchaseDetailEntity> {

    PageUtils queryPage(Map<String, Object> params);
}

