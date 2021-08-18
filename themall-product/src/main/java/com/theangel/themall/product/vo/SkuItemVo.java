package com.theangel.themall.product.vo;

import com.theangel.themall.product.entity.SkuImagesEntity;
import com.theangel.themall.product.entity.SkuInfoEntity;
import com.theangel.themall.product.entity.SpuInfoDescEntity;
import lombok.Data;

import java.util.List;

@Data
public class SkuItemVo {
    //1 sku基本信息
    private SkuInfoEntity skuInfo;
    //2 sku图片信息
    private List<SkuImagesEntity> skuImages;
    //3 销售属性组合
    private List<SkuItemAttrVo> skuItemAttrVos;
    //4 spu属性
    private SpuInfoDescEntity spuInfoDesc;
    //5 属性组-》属性值
    private List<SpuItemAttrGroupVo> groupAttrs;
    //是否有货
    boolean hasStock = true;
}
