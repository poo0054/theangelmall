package com.theangel.themall.coupon.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.theangel.common.utils.PageUtils;
import com.theangel.themall.coupon.entity.SkuLadderEntity;

import java.util.Map;

/**
 * 商品阶梯价格
 *
 * @author theangel
 * @email poo0054@outlook.com
 * @date 2021-06-09 20:52:37
 */
public interface SkuLadderService extends IService<SkuLadderEntity> {

    PageUtils queryPage(Map<String, Object> params);
}

