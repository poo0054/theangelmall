package com.theangel.themall.product.vo;

import lombok.Data;

import java.util.List;

@Data
public class SkuItemAttrVo {
    /**
     * 属性id
     */
    private Long attrId;
    /**
     * 属性名
     */
    private String attrName;
    /**
     * 每个分组对应的属性值 属性值对应的skuid
     */
    private List<attrValueWithSkuIdVo> valueWithSkuId;
}
