package com.theangel.themall.coupon.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.theangel.common.utils.PageUtils;
import com.theangel.themall.coupon.entity.CouponSpuCategoryRelationEntity;

import java.util.Map;

/**
 * 优惠券分类关联
 *
 * @author theangel
 * @email poo0054@outlook.com
 * @date 2021-06-09 20:52:38
 */
public interface CouponSpuCategoryRelationService extends IService<CouponSpuCategoryRelationEntity> {

    PageUtils queryPage(Map<String, Object> params);
}

