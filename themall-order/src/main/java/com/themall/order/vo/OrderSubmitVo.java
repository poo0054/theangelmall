package com.themall.order.vo;

import lombok.Data;

import java.math.BigDecimal;

/**
 * 封装订单提交的数据
 *
 * @ProjectName: theangelmall
 * @Package: com.theangel.themall.order.vo
 * @ClassName: OrderSubmitVo
 * @Author: theangel
 * @Date: 2021/9/11 23:39
 */
@Data
public class OrderSubmitVo {
    //收货地址id
    private Long addrId;
    //支付方式
    private Integer payType;
    //无需提交购物项，最后结算的时候，去购物车中获取被选中的商品
    //TODO 优惠，发票
    //防重令牌
    private String orderToken;

    //应付总额，验证价格
    private BigDecimal payPrice;

    //用户在redis中，从session中获取

    //备注
    private String note;
}
