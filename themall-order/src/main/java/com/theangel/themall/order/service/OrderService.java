package com.theangel.themall.order.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.theangel.common.utils.PageUtils;
import com.theangel.themall.order.entity.OrderEntity;
import com.theangel.themall.order.vo.OrderConfirmVo;

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

    /**
     * 订单确认页展示的数据
     *
     * @return
     */
    OrderConfirmVo confirmOrder();
}

