package com.theangel.themall.ware.to;

import lombok.Data;

import java.util.List;

/**
 * @ProjectName: theangelmall
 * @Package: com.theangel.themall.order.to
 * @ClassName: WareSkuLockTo
 * @Author: theangel
 * @Date: 2021/9/14 22:28
 */
@Data
public class WareSkuLockTo {
    /**
     * 订单号
     */
    private String orderSn;

    /**
     * 需要锁的所有库存信息
     */
    private List<OrderItemTo> locks;


}
