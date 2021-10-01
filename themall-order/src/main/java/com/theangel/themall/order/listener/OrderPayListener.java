package com.theangel.themall.order.listener;

import com.alibaba.fastjson.JSON;
import com.theangel.themall.order.service.OrderService;
import com.theangel.themall.order.vo.PayAsyncVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

/**
 * 支付宝回调
 *
 * @ProjectName: theangelmall
 * @Package: com.theangel.themall.order.listener
 * @ClassName: OrderPayListener
 * @Author: theangel
 * @Date: 2021/10/2 0:56
 */
@RestController
public class OrderPayListener {
    @Autowired
    OrderService orderService;

    /**
     * 必须给支付宝返回success
     *
     * @return
     */
    @PostMapping("/pay/notify")
    public String handleAliPay(@RequestBody PayAsyncVo payAsyncVo) {
        System.out.println("themall-order支付宝通知信息为：" + JSON.toJSONString(payAsyncVo));
        return orderService.handleAliPay(payAsyncVo);

    }
}
