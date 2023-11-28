package com.themall.coupon.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.themall.coupon.entity.CouponEntity;
import org.apache.ibatis.annotations.Mapper;

/**
 * 优惠券信息
 *
 * @author theangel
 * @email poo0054@outlook.com
 * @date 2021-06-09 20:52:38
 */
@Mapper
public interface CouponDao extends BaseMapper<CouponEntity> {

}
