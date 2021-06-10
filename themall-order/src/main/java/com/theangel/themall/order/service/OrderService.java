package com.theangel.themall.order.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.theangel.common.utils.PageUtils;
import com.theangel.themall.order.entity.OrderEntity;

import java.util.Map;

/**
 * 订单
 *
 * @author theangel
 * @email poo0054@outlook.com
 * @date 2021-06-10 19:36:04
 */
public interface OrderService extends IService<OrderEntity> {

    PageUtils queryPage(Map<String, Object> params);
}

