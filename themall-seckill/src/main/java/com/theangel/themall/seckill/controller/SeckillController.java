package com.theangel.themall.seckill.controller;

import com.theangel.common.utils.R;
import com.theangel.themall.seckill.service.SeckillService;
import com.theangel.themall.seckill.to.SeckillSkuRedisTo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @ProjectName: theangelmall
 * @Package: com.theangel.themall.seckill
 * @ClassName: controller
 * @Author: theangel
 * @Date: 2021/10/6 23:43
 */
@RestController
public class SeckillController {

    @Autowired
    SeckillService seckillService;

    /**
     * 返回当前时间可以参与秒杀的商品
     *
     * @return
     */
    @GetMapping("/currentSeckillSkus")
    public R getCurrentSeckillSkus() {
        List<SeckillSkuRedisTo> list = seckillService.getCurrentSeckillSkus();
        return R.ok().setData(list);
    }

    /**
     * 根据skuid查询优惠信息
     *
     * @return
     */
    @GetMapping("/sku/seckill/{skuId}")
    public R getSkuSeckillInfo(@PathVariable("skuId") Long skuId) {
        SeckillSkuRedisTo seckillSkuRedisTo = seckillService.getSkuSeckillInfo(skuId);
        return R.ok().setData(seckillSkuRedisTo);
    }

    /**
     * 抢购-》登录判断-》验证合法（秒杀时间，随机码保证安全，幂等性） -》信号量
     * -》成功（成功添加入mq，监控mq创建订单. 前端返回秒杀成功，正在准备订单。 收货地址确认 -》支付）  -》结束
     *
     * @param id
     * @param code
     * @param num
     * @return
     */
    @GetMapping("/seckill")
    public R seckill(@RequestParam("id") String id, @RequestParam("code") String code, @RequestParam("num") Integer num) {
        String orderNo = seckillService.seckill(id, code, num);
        return null;
    }

}
