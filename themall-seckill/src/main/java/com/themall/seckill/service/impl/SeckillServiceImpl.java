package com.themall.seckill.service.impl;

import com.alibaba.csp.sentinel.annotation.SentinelResource;
import com.alibaba.csp.sentinel.slots.block.BlockException;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import com.baomidou.mybatisplus.core.toolkit.IdWorker;
import com.themall.common.constant.SeckillConstant;
import com.themall.common.to.MemberVo;
import com.themall.common.to.mq.SeckillOrderTo;
import com.themall.common.utils.R;
import com.themall.common.utils.fileutils.UUIDUtils;
import com.themall.seckill.interceptor.LoginInterceptor;
import com.themall.seckill.openfeign.CouponFeignService;
import com.themall.seckill.openfeign.ProductFeignService;
import com.themall.seckill.service.SeckillService;
import com.themall.seckill.to.SeckillSessionTo;
import com.themall.seckill.to.SeckillSkuRedisTo;
import com.themall.seckill.to.SeckillSkuRelationTo;
import com.themall.seckill.to.SkuInfoTo;
import lombok.extern.slf4j.Slf4j;
import org.redisson.api.RSemaphore;
import org.redisson.api.RedissonClient;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.BoundHashOperations;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;

import java.util.List;
import java.util.Set;
import java.util.concurrent.TimeUnit;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

/**
 * @ProjectName: theangelmall
 * @Package: com.theangel.themall.seckill.service
 * @ClassName: SeckillServiceImpl
 * @Author: theangel
 * @Date: 2021/10/3 20:28
 */
@Slf4j
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
    @Autowired
    RabbitTemplate rabbitTemplate;


    @Override
    public void uploadSeckillSku() {
        //数据库扫描3天需要参加的库存
        R r = couponFeignService.getLates3DaySession();
        if (r.isSuccess()) {
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
                    if (info.isSuccess()) {
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

    public List<SeckillSkuRedisTo> blockHandler(BlockException blockException) {
        log.error("当前getCurrentSeckillSkus被降级了，异常信息：{}", blockException.getMessage());
        return null;
    }

    /**
     * 返回当前时间可以参与秒杀的商品
     *
     * @return
     */
    @SentinelResource(value = "currentSeckillSkus", blockHandler = "blockHandler")
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

    /**
     * 获取skuid
     *
     * @param skuId
     * @return
     */
    @Override
    public SeckillSkuRedisTo getSkuSeckillInfo(Long skuId) {
        BoundHashOperations<String, String, String> hashOps = redisTemplate.boundHashOps(SeckillConstant.SKU_CACHE_PREFIX);
        Set<String> keys = hashOps.keys();
        if (!ObjectUtils.isEmpty(keys)) {
            String regx = "\\d_" + skuId;
            for (String key : keys) {
                //有
                if (Pattern.matches(regx, key)) {
                    String s = hashOps.get(key);
                    SeckillSkuRedisTo seckillSkuRedisTo = JSON.parseObject(s, SeckillSkuRedisTo.class);
                    //随机码
                    long timeMillis = System.currentTimeMillis();
                    if (timeMillis >= seckillSkuRedisTo.getStartTime() && timeMillis <= seckillSkuRedisTo.getEndTime()) {

                    } else {
                        seckillSkuRedisTo.setRandomCode(null);
                    }
                    return seckillSkuRedisTo;
                }
            }
        }
        return null;
    }

    /**
     * 抢购-》登录判断-》验证合法（秒杀时间，随机码保证安全，幂等性） -》信号量
     * -》成功（成功添加入mq，监控mq创建订单. 前端返回秒杀成功，正在准备订单。 收货地址确认 -》支付）  -》结束
     *
     * @param id
     * @param code 随机码
     * @param num  多少件
     * @return
     */
    @Override
    public String seckill(String id, String code, Integer num) {
        MemberVo memberVo = LoginInterceptor.threadLocal.get();
        //检验数据合法性  获取秒杀山沟详细信息
        BoundHashOperations<String, String, String> hashOps = redisTemplate.boundHashOps(SeckillConstant.SKU_CACHE_PREFIX);
        String s = hashOps.get(id);
        if (!StringUtils.isEmpty(s)) {
            SeckillSkuRedisTo to = JSON.parseObject(s, SeckillSkuRedisTo.class);
            //1  校验时间
            long timeMillis = System.currentTimeMillis();
            Long endTime = to.getEndTime();
            long ttl = endTime - timeMillis;
            if (timeMillis >= to.getStartTime() && timeMillis <= endTime) {
                //2 校验随机码和商品id
                String randomCode = to.getRandomCode();
                String skuId = to.getPromotionSessionId() + "_" + to.getSkuId();
                if (code.equals(randomCode) && skuId.equals(id)) {
                    //3 校验数量
                    if (num <= to.getSeckillLimit().intValue()) {
                        //检验是否已经购买过了 占位 格式： user_id  session_id  sku_id  组合成key
                        String redisKey = memberVo.getId() + "_" + skuId;
                        Boolean aBoolean = redisTemplate.opsForValue().setIfAbsent(redisKey, num.toString(), ttl, TimeUnit.MILLISECONDS);
                        //setnx   说明没有买过
                        if (aBoolean) {
                            //分布式信号量
                            String s1 = SeckillConstant.SKU_STOCK_SEMAPHORE + code;
                            RSemaphore semaphore = redissonClient.getSemaphore(s1);
                            boolean b = semaphore.tryAcquire(num);
                            //信号量减成功 才能购买
                            if (b) {
                                String timeId = IdWorker.getTimeId();
                                SeckillOrderTo seckillOrderTo = new SeckillOrderTo();
                                seckillOrderTo.setOrderSn(timeId);
                                seckillOrderTo.setMemberId(memberVo.getId());
                                seckillOrderTo.setNum(num);
                                seckillOrderTo.setSkuId(to.getSkuId());
                                seckillOrderTo.setPromotionSessionId(to.getPromotionSessionId());
                                seckillOrderTo.setSeckillPrice(to.getSeckillPrice());
                                rabbitTemplate.convertAndSend("order-event-exchange", "order.seckill.order", seckillOrderTo);
                                return timeId;
                            }
                            System.out.println("信号量获取报错");
                            //信号量报错 获取减信号量失败 都要把刚刚创建出来的锁删除
                            redisTemplate.delete(redisKey);
                        }
                    }
                }
            }
        }
        return null;
    }

}
