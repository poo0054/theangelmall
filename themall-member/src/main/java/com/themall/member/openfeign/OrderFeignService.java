package com.themall.member.openfeign;

import com.themall.model.entity.R;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.Map;

/**
 * @ProjectName: theangelmall
 * @Package: com.theangel.themall.member.openfeign
 * @ClassName: OrderFeignService
 * @Author: theangel
 * @Date: 2021/10/1 23:38
 */
@FeignClient("themall-order")
public interface OrderFeignService {
    /**
     * 查询用户订单
     */
    @PostMapping("/order/order/list/with/item")
    R listWithItem(@RequestBody Map<String, Object> params);

    /**
     * 必须给支付宝返回success
     *
     * @return
     */
    @PostMapping("/pay/notify")
    String handleAliPay(@RequestBody Map payAsyncVo);
}
