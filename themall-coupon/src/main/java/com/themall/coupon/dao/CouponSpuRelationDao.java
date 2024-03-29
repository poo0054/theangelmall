package com.themall.coupon.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.themall.coupon.entity.CouponSpuRelationEntity;
import org.apache.ibatis.annotations.Mapper;

/**
 * 优惠券与产品关联
 *
 * @author theangel
 * @email poo0054@outlook.com
 * @date 2021-06-09 20:52:38
 */
@Mapper
public interface CouponSpuRelationDao extends BaseMapper<CouponSpuRelationEntity> {

}
