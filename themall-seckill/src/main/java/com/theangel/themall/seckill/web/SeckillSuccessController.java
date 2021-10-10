package com.theangel.themall.seckill.web;

import com.theangel.themall.seckill.service.SeckillService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * @ProjectName: theangelmall
 * @Package: com.theangel.themall.seckill.web
 * @ClassName: SeckillSuccessController
 * @Author: theangel
 * @Date: 2021/10/10 20:26
 */
@Controller
public class SeckillSuccessController {

    @Autowired
    SeckillService seckillService;

    /**
     * 秒杀  立即抢购接口
     * 抢购-》登录判断-》验证合法（秒杀时间，随机码保证安全，幂等性） -》信号量
     * -》成功（成功添加入mq，监控mq创建订单. 前端返回秒杀成功，正在准备订单。 收货地址确认 -》支付）  -》结束
     *
     * @param id
     * @param code
     * @param num
     * @return
     */
    @GetMapping("/seckill")
    public String seckill(@RequestParam("id") String id, @RequestParam("code") String code, @RequestParam("num") Integer num, Model model) {
        String orderSn = seckillService.seckill(id, code, num);
        System.out.println(orderSn);
        model.addAttribute("orderSn", orderSn);
        return "success";
    }
}
