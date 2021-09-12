package com.theangel.themall.order.web;

import com.theangel.themall.order.openfeign.ProductFeignService;
import com.theangel.themall.order.service.OrderService;
import com.theangel.themall.order.vo.OrderConfirmVo;
import com.theangel.themall.order.vo.OrderSubmitVo;
import com.theangel.themall.order.vo.SubmitResultVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.concurrent.ExecutionException;

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
    public String toTrade(Model model) throws ExecutionException, InterruptedException {
        OrderConfirmVo orderConfirmVo = orderService.confirmOrder();
        model.addAttribute("confirmVo", orderConfirmVo);
        return "confirm";
    }

    /**
     * 下单成功，去支付页
     * 下单失败，去确认页，重新下单
     *
     * @param vo
     * @return
     */
    @PostMapping("/submitOrder")
    public String submitOrder(OrderSubmitVo vo) {
        SubmitResultVo submitResultVo = orderService.submitOrder(vo);
        if (submitResultVo.getCode() == 0) {
            //成功

            return "pay";
        } else {
            return "redirect:order.theangel.com/totrade";
        }

    }

}
