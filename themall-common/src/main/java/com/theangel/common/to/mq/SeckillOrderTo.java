package com.theangel.common.to.mq;

import lombok.Data;

import java.math.BigDecimal;

/**
 * 秒杀单创建to
 *
 * @ProjectName: theangelmall
 * @Package: com.theangel.common.to.mq
 * @ClassName: SeckillOrderTo
 * @Author: theangel
 * @Date: 2021/10/10 15:14
 */
@Data
public class SeckillOrderTo {
    private String OrderSn;
    /**
     * 活动场次id
     */
    private Long promotionSessionId;
    /**
     * 商品id
     */
    private Long skuId;

    /**
     * 秒杀价格
     */
    private BigDecimal seckillPrice;
    /**
     * 秒杀数量
     */
    private Integer num;

    private Long memberId;
}
