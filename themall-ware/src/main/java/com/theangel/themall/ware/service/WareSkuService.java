package com.theangel.themall.ware.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.theangel.common.utils.PageUtils;
import com.theangel.themall.ware.entity.WareSkuEntity;
import com.theangel.common.to.SkuHasStockVo;
import com.theangel.themall.ware.to.LockStockResult;
import com.theangel.themall.ware.to.WareSkuLockTo;

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
}

