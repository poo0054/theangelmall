package com.themall.seckill.scheduling;

import com.themall.common.constant.SeckillConstant;
import com.themall.seckill.service.SeckillService;
import lombok.extern.slf4j.Slf4j;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;

/**
 * @ProjectName: theangelmall
 * @Package: com.theangel.themall.seckill.scheduling
 * @ClassName: seckillSkuScheduling
 * @Author: theangel
 * @Date: 2021/10/3 20:21
 */
@Slf4j
@Service
public class SeckillSkuScheduling {
    @Autowired
    SeckillService seckillService;
    @Autowired
    RedissonClient redissonClient;

    /**
     * 每天晚上3点上架商品 幂等性处理
     */
    @Async
    //    @Scheduled(cron = "0 30,0 * * * ? ")
    @Scheduled(cron = "30,0 * * * * ? ")
    public void uploadSeckillSku() {
        log.info("上架商品");
        RLock lock = redissonClient.getLock(SeckillConstant.UPlOAD_LOCK);
        lock.lock(10, TimeUnit.MINUTES);
        try {
            seckillService.uploadSeckillSku();
        } finally {
            lock.unlock();
        }
    }

}
