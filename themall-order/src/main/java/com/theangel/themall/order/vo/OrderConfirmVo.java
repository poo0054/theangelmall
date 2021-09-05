package com.theangel.themall.order.vo;

import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

/**
 * 订单确认页展示的数据
 *
 * @ProjectName: theangelmall
 * @Package: com.theangel.themall.order
 * @ClassName: OrderConfirmVo
 * @Author: theangel
 * @Date: 2021/9/5 21:06
 */
@Data
public class OrderConfirmVo {
    //所有收货地址列表
    private List<MemberAddressVo> address;

    //购物项
    private List<OrderItemVo> item;

    /**
     * 积分
     */
    private Integer integration;

    /**
     * 总额
     */
    private BigDecimal total;

    /**
     * 应付
     */
    private BigDecimal payPrice;


}
