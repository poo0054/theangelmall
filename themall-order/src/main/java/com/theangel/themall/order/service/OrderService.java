package com.theangel.themall.order.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.theangel.common.utils.PageUtils;
import com.theangel.themall.order.entity.OrderEntity;
import com.theangel.themall.order.vo.OrderConfirmVo;
import com.theangel.themall.order.vo.OrderSubmitVo;
import com.theangel.themall.order.vo.SubmitResponseVo;

import java.util.Map;
import java.util.concurrent.ExecutionException;

/**
 * 订单
 *
 * @author theangel
 * @email poo0054@outlook.com
 * @date 2021-06-10 19:36:04
 */
public interface OrderService extends IService<OrderEntity> {

    /**
     * 下单
     *
     * @param vo
     */
    SubmitResponseVo submitOrder(OrderSubmitVo vo);

    PageUtils queryPage(Map<String, Object> params);

    /**
     * 订单确认页展示的数据
     *
     * @return
     */
    OrderConfirmVo confirmOrder() throws ExecutionException, InterruptedException;

    /**
     * 根据订单id，查询订单
     *
     * @param orderSn
     * @return
     */
    OrderEntity getOrderStockByOrderSn(String orderSn);
}

