package com.theangel.themall.ware.openfeign;

import com.theangel.common.utils.R;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

/**
 * @ProjectName: theangelmall
 * @Package: com.theangel.themall.ware.openfeign
 * @ClassName: OrderFeignService
 * @Author: theangel
 * @Date: 2021/9/21 16:53
 */

@FeignClient("themall-order")
public interface OrderFeignService {
    /**
     * 根据订单sn，查询订单状态
     */
    @GetMapping("order/order/status/{orderSn}")
    R getOrderStock(@PathVariable("orderSn") String orderSn);
}
