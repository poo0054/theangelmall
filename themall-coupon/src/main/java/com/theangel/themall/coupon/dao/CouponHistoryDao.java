package com.theangel.themall.coupon.dao;

import com.theangel.themall.coupon.entity.CouponHistoryEntity;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
 * 优惠券领取历史记录
 * 
 * @author theangel
 * @email poo0054@outlook.com
 * @date 2021-06-09 20:52:38
 */
@Mapper
public interface CouponHistoryDao extends BaseMapper<CouponHistoryEntity> {
	
}
