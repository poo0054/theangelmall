package com.theangel.themall.order.dao;

import com.theangel.themall.order.entity.OrderItemEntity;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
 * 订单项信息
 * 
 * @author theangel
 * @email poo0054@outlook.com
 * @date 2021-06-10 19:36:04
 */
@Mapper
public interface OrderItemDao extends BaseMapper<OrderItemEntity> {
	
}
