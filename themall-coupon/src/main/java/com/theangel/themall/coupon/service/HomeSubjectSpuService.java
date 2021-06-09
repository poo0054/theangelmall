package com.theangel.themall.coupon.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.theangel.common.utils.PageUtils;
import com.theangel.themall.coupon.entity.HomeSubjectSpuEntity;

import java.util.Map;

/**
 * 专题商品
 *
 * @author theangel
 * @email poo0054@outlook.com
 * @date 2021-06-09 20:52:38
 */
public interface HomeSubjectSpuService extends IService<HomeSubjectSpuEntity> {

    PageUtils queryPage(Map<String, Object> params);
}

