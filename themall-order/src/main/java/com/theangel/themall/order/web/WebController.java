package com.theangel.themall.order.web;

import com.theangel.themall.order.service.OrderService;
import com.theangel.themall.order.vo.OrderConfirmVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import javax.jws.WebParam;

/**
 * @ProjectName: theangelmall
 * @Package: com.theangel.themall.order.web
 * @ClassName: orderController
 * @Author: theangel
 * @Date: 2021/9/5 15:18
 */
@Controller
public class WebController {

    @Autowired
    OrderService orderService;

    @GetMapping("/{page}")
    public String confirm(@PathVariable("page") String s) {
        return s;
    }

    /**
     * 订单确认页面
     *
     * @return
     */
    @GetMapping("/totrade")
    public String toTrade(Model model) {
        OrderConfirmVo orderConfirmVo = orderService.confirmOrder();
        model.addAttribute("confirmVo", orderConfirmVo);
        return "confirm";
    }


}
