package com.themall.ware.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.themall.common.utils.PageUtils;
import com.themall.model.to.SkuHasStockVo;
import com.themall.model.to.mq.OrderTo;
import com.themall.model.to.mq.StockLockedTo;
import com.themall.ware.entity.WareSkuEntity;
import com.themall.ware.to.WareSkuLockTo;

import java.util.List;
import java.util.Map;

/**
 * 商品库存
 *
 * @author theangel
 * @email poo0054@outlook.com
 * @date 2021-06-10 20:02:13
 */
public interface WareSkuService extends IService<WareSkuEntity> {

    PageUtils queryPage(Map<String, Object> params);

    void addStack(Long skuId, Long wareId, Integer skuNum);

    List<SkuHasStockVo> getHasStock(List<Long> skuIds);

    /**
     * 锁定库存
     *
     * @param skuLockTo
     */
    boolean OrderLock(WareSkuLockTo skuLockTo);

    /**
     * 解锁库存
     *
     * @param to
     */
    void unLockStock(StockLockedTo to);

    /**
     * 解锁订单释放的库存
     *
     * @param to
     */
    void unLockStock(OrderTo to);
}

