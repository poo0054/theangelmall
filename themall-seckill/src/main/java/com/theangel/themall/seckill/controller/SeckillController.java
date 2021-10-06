package com.theangel.themall.seckill.controller;

import com.theangel.common.utils.R;
import com.theangel.themall.seckill.service.SeckillService;
import com.theangel.themall.seckill.to.SeckillSkuRedisTo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
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
    @GetMapping("currentSeckillSkus")
    public R getCurrentSeckillSkus() {

        List<SeckillSkuRedisTo> list = seckillService.getCurrentSeckillSkus();
        return R.ok().setData(list);
    }
}
