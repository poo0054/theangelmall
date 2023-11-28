package com.themall.coupon.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.themall.coupon.entity.MemberPriceEntity;
import org.apache.ibatis.annotations.Mapper;

/**
 * 商品会员价格
 *
 * @author theangel
 * @email poo0054@outlook.com
 * @date 2021-06-09 20:52:37
 */
@Mapper
public interface MemberPriceDao extends BaseMapper<MemberPriceEntity> {

}
