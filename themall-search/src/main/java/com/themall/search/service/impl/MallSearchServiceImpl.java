package com.themall.search.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import com.themall.common.to.es.SkuEsModel;
import com.themall.common.utils.R;
import com.themall.search.config.ElasticSearchConfig;
import com.themall.search.constant.EsConstant;
import com.themall.search.openfeign.ThemallProduct;
import com.themall.search.service.MallSearchService;
import com.themall.search.vo.AttrResponseVo;
import com.themall.search.vo.BrandVo;
import com.themall.search.vo.SearchParam;
import com.themall.search.vo.SearchResult;
import org.apache.lucene.search.join.ScoreMode;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.index.query.*;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.SearchHits;
import org.elasticsearch.search.aggregations.AggregationBuilders;
import org.elasticsearch.search.aggregations.Aggregations;
import org.elasticsearch.search.aggregations.bucket.nested.NestedAggregationBuilder;
import org.elasticsearch.search.aggregations.bucket.nested.ParsedNested;
import org.elasticsearch.search.aggregations.bucket.terms.ParsedLongTerms;
import org.elasticsearch.search.aggregations.bucket.terms.ParsedStringTerms;
import org.elasticsearch.search.aggregations.bucket.terms.Terms;
import org.elasticsearch.search.aggregations.bucket.terms.TermsAggregationBuilder;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.elasticsearch.search.fetch.subphase.highlight.HighlightBuilder;
import org.elasticsearch.search.fetch.subphase.highlight.HighlightField;
import org.elasticsearch.search.sort.SortOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class MallSearchServiceImpl implements MallSearchService {

    @Autowired
    RestHighLevelClient restHighLevelClient;

    @Autowired
    ThemallProduct themallProduct;

    @Override
    public SearchResult search(SearchParam searchParam) {
        SearchResponse searchResponse = null;
        SearchResult searchResult = null;
        //准备检索请求
        SearchRequest searchRequest = buildSearchRequest(searchParam);
        try {
            //执行请求
            System.out.println(searchRequest.toString());
            searchResponse = restHighLevelClient.search(searchRequest, ElasticSearchConfig.COMMON_OPTIONS);
            //分析请求
            searchResult = buildSearchResult(searchResponse, searchParam);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return searchResult;
    }

    /**
     * 构建返回值
     *
     * @param searchResponse
     * @return
     */
    private SearchResult buildSearchResult(SearchResponse searchResponse, SearchParam searchParam) {
        SearchResult searchResult = new SearchResult();
        Aggregations aggregations = searchResponse.getAggregations();

//        ============= 聚合信息
        //所有属性
        List<SearchResult.AttrVo> attrs = new ArrayList<>();
        ParsedNested attr_agg = aggregations.get("attr_agg");
        ParsedLongTerms attr_id_agg = attr_agg.getAggregations().get("attr_id_agg");
        for (Terms.Bucket bucket : attr_id_agg.getBuckets()) {
            SearchResult.AttrVo attrVo = new SearchResult.AttrVo();
            //属性id
            long l = bucket.getKeyAsNumber().longValue();
            attrVo.setAttrId(l);
            //属性名字
            ParsedStringTerms attr_name_agg = bucket.getAggregations().get("attr_name_agg");
            String keyAsString1 = attr_name_agg.getBuckets().get(0).getKeyAsString();
            attrVo.setAttrName(keyAsString1);
            //属性值有多个 需要循环
            ParsedStringTerms attr_value_agg = bucket.getAggregations().get("attr_value_agg");
            List<String> attrValue = new ArrayList<>();
            for (Terms.Bucket attr_value_aggBucket : attr_value_agg.getBuckets()) {
                String keyAsString2 = attr_value_aggBucket.getKeyAsString();
                attrValue.add(keyAsString2);
            }
            //属性值  有多个
            attrVo.setAttrValue(attrValue);
            attrs.add(attrVo);
        }
        searchResult.setAttrs(attrs);


        //所有品牌信息
        List<SearchResult.BrandVo> brands = new ArrayList<>();
        ParsedLongTerms brand_agg = aggregations.get("brand_agg");
        List<? extends Terms.Bucket> buckets1 = brand_agg.getBuckets();
        for (Terms.Bucket bucket : buckets1) {
            String keyAsString = bucket.getKeyAsString();
            SearchResult.BrandVo brandVo = new SearchResult.BrandVo();
            //品牌id
            brandVo.setBrandId(Long.valueOf(keyAsString));
            ParsedStringTerms brand_img_agg = bucket.getAggregations().get("brand_img_agg");
            String keyAsString1 = brand_img_agg.getBuckets().get(0).getKeyAsString();
            //品牌图片
            brandVo.setBrandImg(keyAsString1);
            ParsedStringTerms brand_name_agg = bucket.getAggregations().get("brand_name_agg");
            String keyAsString2 = brand_name_agg.getBuckets().get(0).getKeyAsString();
            //品牌名字
            brandVo.setBrandName(keyAsString2);
            brands.add(brandVo);
        }

        searchResult.setBrands(brands);

        //所有分类信息
        List<SearchResult.CatalogVo> catalogs = new ArrayList<>();
        ParsedLongTerms catalog_agg = aggregations.get("catalog_agg");
        List<? extends Terms.Bucket> buckets = catalog_agg.getBuckets();
        for (Terms.Bucket bucket : buckets) {
            String keyAsString = bucket.getKeyAsString();
            SearchResult.CatalogVo catalogVo = new SearchResult.CatalogVo();
            //分类id
            catalogVo.setCatalogId(Long.valueOf(keyAsString));
            ParsedStringTerms aggregations1 = bucket.getAggregations().get("catalog_name_agg");
            String keyAsString1 = aggregations1.getBuckets().get(0).getKeyAsString();
            //分类名字
            catalogVo.setCatalogName(keyAsString1);
            catalogs.add(catalogVo);
        }

        searchResult.setCatalogs(catalogs);

        //hits
        SearchHits hits = searchResponse.getHits();
        //所有商品信息
        SearchHit[] hits1 = hits.getHits();
        if (!ObjectUtils.isEmpty(hits1)) {
            List<SkuEsModel> products = new ArrayList<>();
            for (SearchHit hit : hits1) {
                String sourceAsString = hit.getSourceAsString();
                SkuEsModel skuEsModel = JSON.parseObject(sourceAsString, SkuEsModel.class);
                Map<String, HighlightField> highlightFields = hit.getHighlightFields();
                if (!ObjectUtils.isEmpty(highlightFields)) {
                    HighlightField skuTitle = highlightFields.get("skuTitle");
                    String string = skuTitle.getFragments()[0].string();
                    skuEsModel.setSkuTitle(string);
                }
                products.add(skuEsModel);
            }
            //添加商品
            searchResult.setProducts(products);
        }

        //页码
        searchResult.setPageNum(searchParam.getPageNum());
        //总页数
        long total = hits.getTotalHits().value;
        searchResult.setTotal(total);
        //总页码数
        int totalPages = (int) total % EsConstant.PRODUCT_PAGESIZE == 0 ? (int) total / EsConstant.PRODUCT_PAGESIZE : (int) total / EsConstant.PRODUCT_PAGESIZE + 1;
        searchResult.setTotalPages(totalPages);
        List<Integer> PageNavs = new ArrayList<>();
        for (int i = 1; i < totalPages + 1; i++) {
            PageNavs.add(i);
        }
        searchResult.setPageNavs(PageNavs);
        //商品属性面包屑
        if (!ObjectUtils.isEmpty(searchParam.getAttrs())) {
            //构建面包屑导航功能
            List<SearchResult.NavVo> collect = searchParam.getAttrs().stream().map(attr -> {
                SearchResult.NavVo navVo = new SearchResult.NavVo();
                //分析每个attr的值  attrs=1_其他:安卓&attrs=2_5寸:6寸
                String[] s = attr.split("_");
                navVo.setNavValue(s[1]);
                searchResult.getAttrIds().add(Long.valueOf(s[0]));
                R r = themallProduct.attrInfo(Long.parseLong(s[0]));
                if (r.getCode() == 0) {
                    AttrResponseVo attr1 = r.getData("attr", new TypeReference<AttrResponseVo>() {
                    });
                    navVo.setNavName(attr1.getAttrName());
                }
                String replace = encodeToString(searchParam, attr, "attrs");
                navVo.setLink(replace);
                return navVo;
            }).collect(Collectors.toList());
            searchResult.setNavs(collect);
        }

        //品牌面包屑
        if (!ObjectUtils.isEmpty(searchParam.getBrandId())) {
            List<SearchResult.NavVo> navs = searchResult.getNavs();
            SearchResult.NavVo navVo = new SearchResult.NavVo();
            navVo.setNavName("品牌");

            R r = themallProduct.brandsByIds(searchParam.getBrandId());
            if (r.getCode() == 0) {
                List<BrandVo> data = r.getData(new TypeReference<List<BrandVo>>() {
                });
                String replace = null;
                StringBuffer stringBuffer = new StringBuffer();
                for (BrandVo datum : data) {
                    stringBuffer.append(datum.getName());
                    replace = encodeToString(searchParam, datum.getBrandId() + "", "brandId");
                }
                navVo.setNavValue(stringBuffer.toString());
                navVo.setLink(replace);
            }

            navs.add(navVo);
        }
        //TODO 分类品牌屑
        if (!ObjectUtils.isEmpty(searchParam.getCatalog3Id())) {

        }


        return searchResult;
    }

    private String encodeToString(SearchParam searchParam, String attr, String key) {
        String encode = null;
        try {
            encode = URLEncoder.encode(attr, "UTF-8");
            //
            encode = encode.replace("+", "20%");
            encode = encode.replace("%28", "(");
            encode = encode.replace("%29", ")");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        String replace = searchParam.get_queryString().replace(key + "=" + encode, "").replace("&&", "&");
        return "https://search.poo0054.top/list.html?" + replace;
    }

    /**
     * 构建请求
     *
     * @return
     */
    private SearchRequest buildSearchRequest(SearchParam searchParam) {
        SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
        //模糊匹配  过滤（按照属性，分类，品牌，价格区间，库存）
        //1.bool
        BoolQueryBuilder boolQuery = QueryBuilders.boolQuery();
        //1-1 must 模糊匹配
        if (!StringUtils.isEmpty(searchParam.getKeyword())) {
            MatchQueryBuilder skuTitle = QueryBuilders.matchQuery("skuTitle", searchParam.getKeyword());
            boolQuery.must(skuTitle);
        }

        //1.2 filter  过滤 分类
        if (!ObjectUtils.isEmpty(searchParam.getCatalog3Id())) {
            TermQueryBuilder catalogId = QueryBuilders.termQuery("catalogId", searchParam.getCatalog3Id());
            boolQuery.filter(catalogId);
        }
        //1.2  filter  过滤  品牌
        if (!ObjectUtils.isEmpty(searchParam.getBrandId())) {
            TermsQueryBuilder brandId = QueryBuilders.termsQuery("brandId", searchParam.getBrandId());
            boolQuery.filter(brandId);
        }

        //1.2  filter  过滤 库存
        if (!ObjectUtils.isEmpty(searchParam.getHasStock())) {
            boolQuery.filter(QueryBuilders.termQuery("hasStock", searchParam.getHasStock() == 0 ? false : true));
        }

        //1.2  filter  过滤  价格区间
        if (!ObjectUtils.isEmpty(searchParam.getSkuPrice())) {
            //1_500 -> 0到500    _500->500以内- 500_ ->500以上
            RangeQueryBuilder skuPrice = QueryBuilders.rangeQuery("skuPrice");
            String skuPrice1 = searchParam.getSkuPrice();
            String[] s = skuPrice1.split("_", 5);
            List<String> collect = Arrays.stream(s).map(item -> {
                if (ObjectUtils.isEmpty(item)) {
                    return null;
                }
                return item;
            }).collect(Collectors.toList());
            if (2 == collect.size()) {
                //区间
                skuPrice.gte(collect.get(0)).lte(collect.get(1));
            } else if (1 == s.length) {
                if (searchParam.getSkuPrice().startsWith("_")) {
                    skuPrice.lte(s[0]);
                } else if (searchParam.getSkuPrice().endsWith("_")) {
                    skuPrice.gte(s[0]);
                }
            }
            boolQuery.filter(skuPrice);
        }
        //1.2  filter  过滤  按照属性
        if (!ObjectUtils.isEmpty(searchParam.getAttrs())) {
            for (String attr : searchParam.getAttrs()) {
                //bool
                BoolQueryBuilder nestBoolQuery = QueryBuilders.boolQuery();
                String[] s = attr.split("_");
                //属性id
                String attrId = s[0];
                //属性值
                String[] attrValue = s[1].split(":");
                nestBoolQuery.must(QueryBuilders.termQuery("attrs.attrId", attrId));
                nestBoolQuery.must(QueryBuilders.termsQuery("attrs.attrValue", attrValue));

                //每一个属性  都生成一个nested
                NestedQueryBuilder nestedQuery = QueryBuilders.nestedQuery("attrs", nestBoolQuery, ScoreMode.None);
                boolQuery.filter(nestedQuery);
            }
        }
        //所有条件添加入search
        searchSourceBuilder.query(boolQuery);

        //2.排序，分页，高亮
        if (!ObjectUtils.isEmpty(searchParam.getSort())) {
            //sort=skuPrice_desc/asc
            String[] s = searchParam.getSort().split("_");
            SortOrder asc = s[1].equalsIgnoreCase("asc") ? SortOrder.ASC : SortOrder.DESC;
            searchSourceBuilder.sort(s[0], asc);
        }
        //分页
        searchSourceBuilder.from((searchParam.getPageNum() - 1) * EsConstant.PRODUCT_PAGESIZE);
        searchSourceBuilder.size(EsConstant.PRODUCT_PAGESIZE);
        //高亮
        if (!ObjectUtils.isEmpty(searchParam.getKeyword())) {
            HighlightBuilder highlightBuilder = new HighlightBuilder();
            highlightBuilder.field("skuTitle");
            highlightBuilder.preTags("<b style='color:red'>");
            highlightBuilder.postTags("</b>");
            searchSourceBuilder.highlighter(highlightBuilder);
        }


        //品牌id聚合
        TermsAggregationBuilder brand_agg = AggregationBuilders.terms("brand_agg");
        brand_agg.field("brandId").size(50);
        //品牌id子聚合  品牌名称
        brand_agg.subAggregation(AggregationBuilders.terms("brand_name_agg").field("brandName").size(1));
        //品牌id子聚合  品牌图片地址
        brand_agg.subAggregation(AggregationBuilders.terms("brand_img_agg").field("brandImg").size(1));
        //聚合添加入build
        searchSourceBuilder.aggregation(brand_agg);


        //分类聚合
        TermsAggregationBuilder catalog_agg = AggregationBuilders.terms("catalog_agg").field("catalogId").size(20);
        catalog_agg.subAggregation(AggregationBuilders.terms("catalog_name_agg").field("catalogName").size(1));
        searchSourceBuilder.aggregation(catalog_agg);

        //属性聚合
        NestedAggregationBuilder attr_agg = AggregationBuilders.nested("attr_agg", "attrs");
        TermsAggregationBuilder attr_id_agg = AggregationBuilders.terms("attr_id_agg").field("attrs.attrId");
        //聚合的子聚合  分析attr的name
        attr_id_agg.subAggregation(AggregationBuilders.terms("attr_name_agg").field("attrs.attrName").size(1));
        //聚合的子聚合  分析attr的value
        attr_id_agg.subAggregation(AggregationBuilders.terms("attr_value_agg").field("attrs.attrValue").size(50));

        attr_agg.subAggregation(attr_id_agg);
        searchSourceBuilder.aggregation(attr_agg);

        //3.聚合
        System.out.println(searchSourceBuilder.toString());
        SearchRequest searchResult = new SearchRequest(new String[]{EsConstant.PRODUCT_INDEX}, searchSourceBuilder);
        return searchResult;
    }
}
