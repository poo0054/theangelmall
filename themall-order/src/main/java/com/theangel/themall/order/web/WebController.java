package com.theangel.themall.order.web;

import com.theangel.themall.order.service.OrderService;
import com.theangel.themall.order.vo.OrderConfirmVo;
import com.theangel.themall.order.vo.OrderSubmitVo;
import com.theangel.themall.order.vo.SubmitResponseVo;
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

    /**
     * 用来测试跳转页面
     *
     * @param s
     * @return
     */
    @GetMapping("page/{page}")
    public String confirm(@PathVariable("page") String s) {
        return s;
    }

    /**
     * 订单确认页面
     * 从购物车页面去订单确认页面
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
     * 锁定库存去pay页面
     *
     * @param vo
     * @return
     */
    @PostMapping("/submitOrder")
    public String submitOrder(OrderSubmitVo vo, Model model) {
        SubmitResponseVo submitResultVo = orderService.submitOrder(vo);
        if (submitResultVo.getCode() == 0) {
            //成功
            model.addAttribute("submitResultVo", submitResultVo.getOrderEntity());
            return "pay";
        } else {
            return "redirect:order.theangel.com/totrade";
        }

    }

}
