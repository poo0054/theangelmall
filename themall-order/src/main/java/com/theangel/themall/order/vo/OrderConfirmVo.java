package com.theangel.themall.order.vo;

import lombok.Data;
import org.springframework.util.ObjectUtils;

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
//    private BigDecimal total;

    /**
     * 应付
     */
    private BigDecimal payPrice;

    /**
     * TODO 防重令牌
     */
    private String orderToken;

    //总件数
    private Integer count;


    public Integer getCount() {
        Integer integer = 0;
        if (!ObjectUtils.isEmpty(item)) {
            integer = item.size();
        }
        return count = integer;
    }

    /**
     * 计算总额
     *
     * @return
     */
    public BigDecimal getTotal() {
        //合计
        BigDecimal bigDecimal = new BigDecimal(0);
        if (!ObjectUtils.isEmpty(item)) {
            for (OrderItemVo orderItemVo : item) {
                //单价
                BigDecimal price = orderItemVo.getPrice();
                //当前商品总价
                price = price.multiply(new BigDecimal(orderItemVo.getCount()));
                //累计相加
                bigDecimal = bigDecimal.add(price);
            }
        }
        return bigDecimal;
    }

    /**
     * 应付价格
     *
     * @return
     */
    public BigDecimal getPayPrice() {
        return getTotal();
    }
}
