package com.themall.member.web;

import com.alibaba.fastjson.JSON;
import com.themall.common.utils.R;
import com.themall.member.config.ThreadConfig;
import com.themall.member.openfeign.OrderFeignService;
import jodd.util.ThreadUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ThreadPoolExecutor;

/**
 * @ProjectName: theangelmall
 * @Package: com.theangel.themall.member.web
 * @ClassName: MerberWebController
 * @Author: theangel
 * @Date: 2021/10/1 13:23
 */
@Controller
@RequestMapping("member")
public class MerberWebController {

    @Autowired
    OrderFeignService orderFeignService;
    @Autowired
    ThreadConfig threadConfig;

    /**
     * /member/memberOrderList.html
     *
     * @return
     */
    @GetMapping("/memberOrderList.html")
    public String memberOrderList(@RequestParam(value = "page", defaultValue = "1") String page, Model model, @RequestParam Map map) {

        //不为空，说明支付宝回调来的
        if (!ObjectUtils.isEmpty(map)) {
            //TODO 没有内网穿透  为了方便，伪造自己是支付宝 给order的controller里面发送信息 ，支付成功修改订单状态
            ThreadPoolExecutor threadPoolExecutor = threadConfig.poolExecutor();
            //交给第三方服务完成
            threadPoolExecutor.execute(() -> {
                String s = JSON.toJSONString(map);
                System.out.println("themall-member支付宝通知信息为:" + s);
                String s1 = null;
                try {
                    s1 = orderFeignService.handleAliPay(map);
                } catch (Exception e) {
                    System.out.println("支付宝回调推送失败");
                    //推送失败，重新推送3次
                    int i = 0;
                    do {
                        try {
                            s1 = orderFeignService.handleAliPay(map);
                        } catch (Exception ex) {
                            System.out.println("支付宝回调推送失败，重试3次，当前第" + i + "次！");
                            i++;
                            ThreadUtil.sleep(500);
                        }
                    } while (StringUtils.isEmpty(s1) && i <= 3);
                }
                System.out.println("支付宝回调信息：" + s1);
            });
        }


        HashMap<String, Object> hashMap = new HashMap<>();
        hashMap.put("page", page);
        R r = orderFeignService.listWithItem(hashMap);
        if (0 == r.getCode()) {
            System.out.println(JSON.toJSONString(r));
            model.addAttribute("orders", r);
        }
        return "orderList";
    }
}
