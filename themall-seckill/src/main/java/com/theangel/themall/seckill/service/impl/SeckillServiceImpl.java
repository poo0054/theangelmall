package com.theangel.themall.seckill.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import com.theangel.common.constant.SeckillConstant;
import com.theangel.common.utils.R;
import com.theangel.common.utils.fileutils.UUIDUtils;
import com.theangel.themall.seckill.openfeign.CouponFeignService;
import com.theangel.themall.seckill.openfeign.ProductFeignService;
import com.theangel.themall.seckill.service.SeckillService;
import com.theangel.themall.seckill.to.SeckillSessionTo;
import com.theangel.themall.seckill.to.SeckillSkuRedisTo;
import com.theangel.themall.seckill.to.SeckillSkuRelationTo;
import com.theangel.themall.seckill.to.SkuInfoTo;
import org.redisson.api.RSemaphore;
import org.redisson.api.RedissonClient;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.BoundHashOperations;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * @ProjectName: theangelmall
 * @Package: com.theangel.themall.seckill.service
 * @ClassName: SeckillServiceImpl
 * @Author: theangel
 * @Date: 2021/10/3 20:28
 */
@Service
public class SeckillServiceImpl implements SeckillService {
    @Autowired
    CouponFeignService couponFeignService;
    @Autowired
    StringRedisTemplate redisTemplate;
    @Autowired
    ProductFeignService productFeignService;
    @Autowired
    RedissonClient redissonClient;


    @Override
    public void uploadSeckillSku() {
        //数据库扫描3天需要参加的库存
        R r = couponFeignService.getLates3DaySession();
        if (0 == r.getCode()) {
            List<SeckillSessionTo> data = r.getData(new TypeReference<List<SeckillSessionTo>>() {
            });
            //缓存活动信息
            saveSessionInfos(data);

            //缓存活动关联商品信息
            saveSessionSkuInfos(data);
        }
    }


    private void saveSessionInfos(List<SeckillSessionTo> list) {
        for (SeckillSessionTo seckillSessionTo : list) {
            long start = seckillSessionTo.getStartTime().getTime();
            long end = seckillSessionTo.getEndTime().getTime();
            String key = SeckillConstant.SESSION_CACHE_PREFIX + start + "_" + end;
            if (!redisTemplate.hasKey(key)) {
                List<String> collect = seckillSessionTo.getRelationEntities().stream().map(item -> {
                            return item.getPromotionSessionId() + "_" + item.getSkuId().toString();
                        }
                ).collect(Collectors.toList());
                //缓存活动信息
                redisTemplate.opsForList().leftPushAll(key, collect);
            }

        }

    }

    private void saveSessionSkuInfos(List<SeckillSessionTo> list) {
        for (SeckillSessionTo seckillSessionTo : list) {
            BoundHashOperations<String, Object, Object> hashOps = redisTemplate.boundHashOps(SeckillConstant.SKU_CACHE_PREFIX);

            for (SeckillSkuRelationTo skuRelationTo : seckillSessionTo.getRelationEntities()) {
                String key = skuRelationTo.getPromotionSessionId() + "_" + skuRelationTo.getSkuId();
                if (!hashOps.hasKey(key)) {
                    //缓存商品
                    SeckillSkuRedisTo seckillSkuRedisTo = new SeckillSkuRedisTo();
                    //sku基本信息
                    R info = productFeignService.info(skuRelationTo.getSkuId());
                    if (0 == info.getCode()) {
                        SkuInfoTo skuInfo = info.getData("skuInfo", new TypeReference<SkuInfoTo>() {
                        });
                        seckillSkuRedisTo.setSkuInfoTo(skuInfo);
                    }
                    //sku秒杀信息
                    BeanUtils.copyProperties(skuRelationTo, seckillSkuRedisTo);
                    //时间
                    seckillSkuRedisTo.setStartTime(seckillSessionTo.getStartTime().getTime());
                    seckillSkuRedisTo.setEndTime(seckillSessionTo.getEndTime().getTime());
                    String replace = UUIDUtils.getUUID().replace("-", "");
                    //商品随机码
                    seckillSkuRedisTo.setRandomCode(replace);

                    hashOps.put(key, JSON.toJSONString(seckillSkuRedisTo));
                    //信号量的key
                    String s = SeckillConstant.SKU_STOCK_SEMAPHORE + replace;
                    //分布式信号量
                    RSemaphore semaphore = redissonClient.getSemaphore(s);
                    //商品秒杀数量 作为信号量    限流
                    semaphore.trySetPermits(skuRelationTo.getSeckillCount().intValue());

                }
            }
        }
    }

    /**
     * 返回当前时间可以参与秒杀的商品
     *
     * @return
     */
    @Override
    public List<SeckillSkuRedisTo> getCurrentSeckillSkus() {
        //确定当前时间 属于哪个秒杀场次
        long time = System.currentTimeMillis();
        Set<String> keys = redisTemplate.keys(SeckillConstant.SESSION_CACHE_PREFIX + "*");
        for (String key : keys) {
            String replace = key.replace(SeckillConstant.SESSION_CACHE_PREFIX, "");
            String[] s = replace.split("_");
            long start = Long.parseLong(s[0]);
            long end = Long.parseLong(s[1]);
            if (time >= start && time <= end) {
                //当前场次满足  获取秒杀场次的所有商品信息
                List<String> range = redisTemplate.opsForList().range(key, -100, 100);
                BoundHashOperations<String, String, Object> hashOps = redisTemplate.boundHashOps(SeckillConstant.SKU_CACHE_PREFIX);
                List<Object> list = hashOps.multiGet(range);
                if (!ObjectUtils.isEmpty(list)) {
                    List<SeckillSkuRedisTo> collect = list.stream().map(item -> {
                        SeckillSkuRedisTo seckillSkuRedisTo = JSON.parseObject(item.toString(), SeckillSkuRedisTo.class);
                        //当前秒杀开始了  需要随机码
//                        seckillSkuRedisTo.setRandomCode(null);
                        return seckillSkuRedisTo;
                    }).collect(Collectors.toList());
                    return collect;
                }
            }
        }
        return null;
    }

}
