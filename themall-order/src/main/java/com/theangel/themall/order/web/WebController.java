package com.theangel.themall.order.web;

import com.theangel.common.utils.fileutils.UUIDUtils;
import com.theangel.themall.order.entity.OrderEntity;
import com.theangel.themall.order.service.OrderService;
import com.theangel.themall.order.vo.OrderConfirmVo;
import com.theangel.themall.order.vo.OrderSubmitVo;
import com.theangel.themall.order.vo.SubmitResponseVo;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.Date;
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
    RabbitTemplate rabbitTemplate;

    @ResponseBody
    @GetMapping("test/creatOrder")
    public String createOrderTest() {
        OrderEntity orderEntity = new OrderEntity();
        orderEntity.setReceiverName(UUIDUtils.getUUID());
        orderEntity.setModifyTime(new Date());
        rabbitTemplate.convertAndSend("order-event-exchange", "order.create.order", orderEntity);
        return "ok";
    }


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
    public String submitOrder(OrderSubmitVo vo, Model model, RedirectAttributes redirectAttributes) {
        SubmitResponseVo submitResultVo = orderService.submitOrder(vo);
        if (submitResultVo.getCode() == 0) {
            //成功
            model.addAttribute("submitResultVo", submitResultVo);
            return "pay";
        } else {
            String msg = null;
            switch (submitResultVo.getCode()) {
                case 1:
                    msg = "订单信息过期，请刷新再次提交";
                    break;
                case 2:
                    msg = "订单商品价格发送变化，请确认后再次提交";
                    break;
                case 3:
                    msg = "库存锁定失败，商品库存不足";
                    break;
                default:
                    break;
            }
            redirectAttributes.addFlashAttribute("msg", msg);
            return "redirect:http://order.theangel.com/totrade";
        }

    }

}
