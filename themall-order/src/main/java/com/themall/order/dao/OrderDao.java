package com.themall.order.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.themall.order.entity.OrderEntity;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

/**
 * 订单
 *
 * @author theangel
 * @email poo0054@outlook.com
 * @date 2021-06-10 19:36:04
 */
@Mapper
public interface OrderDao extends BaseMapper<OrderEntity> {

    void updateOrderStatus(@Param("tradeNo") String tradeNo, @Param("code") Integer code);
}
