package com.theangel.themall.seckill.to;

import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

/**
 * @ProjectName: theangelmall
 * @Package: com.theangel.themall.seckill.to
 * @ClassName: SeckillSkuRedisTo
 * @Author: theangel
 * @Date: 2021/10/5 21:08
 */
@Data
public class SeckillSkuRedisTo {
    /**
     * id
     */
    private Long id;
    /**
     * 活动id
     */
    private Long promotionId;
    /**
     * 活动场次id
     */
    private Long promotionSessionId;
    /**
     * 商品id
     */
    private Long skuId;
    /**
     * 商品随机码
     */
    private String randomCode;
    /**
     * 秒杀价格
     */
    private BigDecimal seckillPrice;
    /**
     * 秒杀总量
     */
    private BigDecimal seckillCount;
    /**
     * 每人限购数量
     */
    private BigDecimal seckillLimit;
    /**
     * 排序
     */
    private Integer seckillSort;

    //sku的详细
    private SkuInfoTo skuInfoTo;

    private Long startTime;

    private Long endTime;
}
