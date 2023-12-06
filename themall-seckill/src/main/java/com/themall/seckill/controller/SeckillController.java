package com.themall.seckill.controller;

import com.alibaba.fastjson.JSON;
import com.themall.model.entity.R;
import com.themall.seckill.service.SeckillService;
import com.themall.seckill.to.SeckillSkuRedisTo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
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
        System.out.println("当前时间可以参与秒杀的商品:" + JSON.toJSONString(list));
        return R.httpStatus().setData(list);
    }

    /**
     * 根据skuid查询优惠信息
     *
     * @return
     */
    @GetMapping("/sku/seckill/{skuId}")
    public R getSkuSeckillInfo(@PathVariable("skuId") Long skuId) {
        SeckillSkuRedisTo seckillSkuRedisTo = seckillService.getSkuSeckillInfo(skuId);
        return R.httpStatus().setData(seckillSkuRedisTo);
    }


}
