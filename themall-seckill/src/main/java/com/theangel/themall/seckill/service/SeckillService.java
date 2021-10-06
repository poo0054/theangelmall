package com.theangel.themall.seckill.service;

import com.theangel.themall.seckill.to.SeckillSkuRedisTo;

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
}
