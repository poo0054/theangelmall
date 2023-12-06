package com.themall.model.constants;

/**
 * @ProjectName: theangelmall
 * @Package: com.theangel.common.constant
 * @ClassName: SeckillConstant
 * @Author: theangel
 * @Date: 2021/10/6 23:40
 */
public class SeckillConstant {
    //分布式锁
    public static final String UPlOAD_LOCK = "seckill:upload:lock";

    //秒杀组
    public static final String SESSION_CACHE_PREFIX = "seckill:sessions:";

    //秒杀商品前缀
    public static final String SKU_CACHE_PREFIX = "seckill:skus";

    //秒杀信号量前缀
    public static final String SKU_STOCK_SEMAPHORE = "seckill:stock:";
}
