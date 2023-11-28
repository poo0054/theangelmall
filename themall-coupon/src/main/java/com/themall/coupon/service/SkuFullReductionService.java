package com.themall.coupon.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.themall.common.to.SkuReductionTo;
import com.themall.common.utils.PageUtils;
import com.themall.coupon.entity.SkuFullReductionEntity;

import java.util.Map;

/**
 * 商品满减信息
 *
 * @author theangel
 * @email poo0054@outlook.com
 * @date 2021-06-09 20:52:37
 */
public interface SkuFullReductionService extends IService<SkuFullReductionEntity> {

    PageUtils queryPage(Map<String, Object> params);

    void saveInfo(SkuReductionTo skuReductionTo);
}

