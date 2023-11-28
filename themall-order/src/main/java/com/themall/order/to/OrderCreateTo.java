package com.themall.order.to;

import com.themall.order.entity.OrderEntity;
import com.themall.order.entity.OrderItemEntity;
import lombok.Data;

import java.math.BigDecimal;
import java.util.List;


/**
 * 创建订单
 *
 * @ProjectName: theangelmall
 * @Package: com.theangel.themall.order.to
 * @ClassName: OrderCreateTo
 * @Author: theangel
 * @Date: 2021/9/12 13:43
 */
@Data
public class OrderCreateTo {
    //订单
    private OrderEntity order;
    //订单项信息
    private List<OrderItemEntity> orderItems;
    /**
     * 计算应付价格
     */
    private BigDecimal payPrice;
    /**
     * 运费
     */
    private BigDecimal fare;
}
