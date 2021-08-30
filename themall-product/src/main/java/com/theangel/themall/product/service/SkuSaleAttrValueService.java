package com.theangel.themall.product.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.theangel.common.utils.PageUtils;
import com.theangel.themall.product.entity.SkuSaleAttrValueEntity;
import com.theangel.themall.product.vo.SkuItemAttrVo;

import java.util.List;
import java.util.Map;

/**
 * sku销售属性&值
 *
 * @author theangel
 * @email poo0054@outlook.com
 * @date 2021-06-09 20:47:30
 */
public interface SkuSaleAttrValueService extends IService<SkuSaleAttrValueEntity> {

    PageUtils queryPage(Map<String, Object> params);

    /**
     * 根据spuid获取所有属性
     *
     * @return
     * @param spuId
     */
    List<SkuItemAttrVo> getSaleAttrBySpuId(Long spuId);

    List<String> getAttrStrBySkuId(Long skuId);
}

