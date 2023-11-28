package com.themall.product.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.themall.common.utils.PageUtils;
import com.themall.product.entity.SpuInfoEntity;
import com.themall.product.vo.SpuSaveVo;

import java.util.Map;

/**
 * spu信息
 *
 * @author theangel
 * @email poo0054@outlook.com
 * @date 2021-06-09 20:47:30
 */
public interface SpuInfoService extends IService<SpuInfoEntity> {

    PageUtils queryPage(Map<String, Object> params);

    void saveSpuInfo(SpuSaveVo spuInfo);

    void saveBaseSpuInfo(SpuInfoEntity spuInfoEntity);

    PageUtils queryPageByCondition(Map<String, Object> params);

    void up(Long spuId);

    /**
     * 根据skuid获取spuid
     *
     * @param skuId
     * @return
     */
    SpuInfoEntity getSpuInfo(Long skuId);
}

