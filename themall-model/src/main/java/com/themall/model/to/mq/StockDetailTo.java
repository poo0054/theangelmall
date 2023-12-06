package com.themall.model.to.mq;

import lombok.Data;

/**
 * 库存锁定记录详情
 *
 * @ProjectName: theangelmall
 * @Package: com.theangel.common.to.mq
 * @ClassName: StockDetailTo
 * @Author: theangel
 * @Date: 2021/9/21 15:50
 */
@Data
public class StockDetailTo {
    /**
     * 工作单详情
     */
    private Long id;
    /**
     * sku_id
     */
    private Long skuId;
    /**
     * sku_name
     */
    private String skuName;
    /**
     * 购买个数
     */
    private Integer skuNum;
    /**
     * 工作单id
     */
    private Long taskId;
    /**
     * 仓库id
     */
    private Long wareId;
    /**
     * 1-已锁定  2-已解锁  3-扣减
     */
    private Integer lockStatus;
}
