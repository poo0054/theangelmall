package com.themall.search.vo;

import lombok.Data;

import java.util.List;

/**
 * 封装页面所有可能查询的条件
 * catalog3Id=226 keyword=a
 */
@Data
public class SearchParam {
    /**
     * 全文匹配
     */
    private String keyword;

    /**
     * 三级分类id
     */
    private Long catalog3Id;

    /**
     * 排序条件 选择一个
     * 价格  sort=skuPrice_desc/asc  降序或者升序
     * 销量 sort=saleCont_desc/asc
     * 热度评分 sort=hostScore_desc/asc
     */
    private String sort;

    /**
     * 过滤条件
     * hasStock 是否有货 ，skuPrice 价格区间 ，brandId 品牌，attrId 属性Id
     * hasStock=0/1
     * skuPrice=1_500 1到500    _500 500以内 500_ 500以上
     */

    /**
     * 是否有货  0-无货  1-有货
     */
    private Integer hasStock;

    /**
     * 价格区间
     * skuPrice=  1_500 -> 0到500    _500->500以内- 500_ ->500以上
     */
    private String skuPrice;

    /**
     * 品牌id ，可能有多个
     * brandId=1&brandId=2
     */
    private List<Long> brandId;

    /**
     * 属性id ，可能有多个
     * attrs=1_其他:安卓&attrs=2_5寸:6寸
     */
    private List<String> attrs;

    /**
     * 页码
     */
    private Integer pageNum = 1;
    /**
     * 页码
     */
    private String _queryString;
}
