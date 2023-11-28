package com.themall.order.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.themall.order.entity.OrderSettingEntity;
import org.apache.ibatis.annotations.Mapper;

/**
 * 订单配置信息
 *
 * @author theangel
 * @email poo0054@outlook.com
 * @date 2021-06-10 19:36:04
 */
@Mapper
public interface OrderSettingDao extends BaseMapper<OrderSettingEntity> {

}
