package com.themall.product.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.themall.common.utils.PageUtils;
import com.themall.product.entity.SkuInfoEntity;
import com.themall.product.vo.SkuItemVo;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutionException;

/**
 * sku信息
 *
 * @author theangel
 * @email poo0054@outlook.com
 * @date 2021-06-09 20:47:30
 */
public interface SkuInfoService extends IService<SkuInfoEntity> {

    PageUtils queryPage(Map<String, Object> params);

    PageUtils queryPageByCondition(Map<String, Object> params);

    List<SkuInfoEntity> getSkuInfoBySpuId(Long spuId);

    /**
     * 根据skuid查询sku商品信息展示
     *
     * @param skyId
     * @return
     */
    SkuItemVo itemBySkuId(Long skyId) throws ExecutionException, InterruptedException;

    /**
     * 查询当前skuid的价格
     *
     * @param skuid
     * @return
     */
    BigDecimal getPrice(Long skuid);
}

