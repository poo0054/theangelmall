package com.themall.search.vo;

import com.themall.common.to.es.SkuEsModel;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

/**
 * 返回给页面的所有信息
 */
@Data
public class SearchResult {
    /**
     * 查询到所有商品信息
     */
    private List<SkuEsModel> products;
    /**
     * 当前页
     */
    private Integer pageNum;
    /**
     * 总记录
     */
    private Long total;
    /**
     * 总页码
     */
    private Integer totalPages;
    /**
     * 导航页码
     */
    private List<Integer> pageNavs;

    /**
     * 查询到的结果，所有涉及到的品牌
     */
    private List<BrandVo> brands;
    /**
     * 涉及到的所有属性
     */
    private List<AttrVo> attrs;
    /**
     * 涉及到的所有分类
     */
    private List<CatalogVo> catalogs;
    /**
     * 哪些属性id被筛选了
     */
    private List<Long> attrIds = new ArrayList<>();


    /**
     * 面包屑导航
     */
    private List<NavVo> navs = new ArrayList<>();

    @Data
    public static class NavVo {
        private String navName;
        private String navValue;
        private String link;
    }

    /**
     * 品牌信息
     */
    @Data
    public static class BrandVo {
        private Long brandId;
        private String brandName;
        private String brandImg;
    }

    /**
     * 属性信息
     */
    @Data
    public static class AttrVo {
        private Long attrId;
        private String attrName;
        private List<String> attrValue;
    }

    /**
     * 分类信息
     */
    @Data
    public static class CatalogVo {
        private Long catalogId;
        private String catalogName;
    }


}
