package com.themall.seckill.service;

import com.themall.seckill.to.SeckillSkuRedisTo;

import java.util.List;

/**
 * @ProjectName: theangelmall
 * @Package: com.theangel.themall.seckill.service
 * @ClassName: SeckillService
 * @Author: theangel
 * @Date: 2021/10/3 20:28
 */
public interface SeckillService {

    void uploadSeckillSku();


    List<SeckillSkuRedisTo> getCurrentSeckillSkus();

    SeckillSkuRedisTo getSkuSeckillInfo(Long skuId);

    /**
     * 抢购-》登录判断-》验证合法（秒杀时间，随机码保证安全，幂等性） -》信号量
     * -》成功（成功添加入mq，监控mq创建订单. 前端返回秒杀成功，正在准备订单。 收货地址确认 -》支付）  -》结束
     *
     * @param id
     * @param code
     * @param num
     * @return
     */
    String seckill(String id, String code, Integer num);
}
