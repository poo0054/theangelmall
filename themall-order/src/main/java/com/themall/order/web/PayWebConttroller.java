package com.themall.order.web;

import com.alipay.api.AlipayApiException;
import com.themall.order.config.AlipayTemplate;
import com.themall.order.service.OrderService;
import com.themall.order.vo.PayVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @ProjectName: theangelmall
 * @Package: com.theangel.themall.order.web
 * @ClassName: PayWebConttroller
 * @Author: theangel
 * @Date: 2021/10/1 12:15
 */
@Controller
public class PayWebConttroller {
    @Autowired
    AlipayTemplate alipayTemplate;

    @Autowired
    OrderService orderService;

    @ResponseBody
    @GetMapping(value = "/payOrder", produces = "text/html")
    public String payOrder(@RequestParam("orderSn") String orderSn) throws AlipayApiException {
        PayVo payVo = orderService.payOrder(orderSn);
        String pay = alipayTemplate.pay(payVo);
        System.out.println(pay);
        return pay;
    }
}
