package com.theangel.themall.product.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.theangel.common.utils.PageUtils;
import com.theangel.themall.product.entity.SpuInfoDescEntity;

import java.util.Map;

/**
 * spu信息介绍
 *
 * @author theangel
 * @email poo0054@outlook.com
 * @date 2021-06-09 20:47:30
 */
public interface SpuInfoDescService extends IService<SpuInfoDescEntity> {

    PageUtils queryPage(Map<String, Object> params);
}

