package com.theangel.themall.search.service.impl;

import com.theangel.themall.search.config.ElasticSearchConfig;
import com.theangel.themall.search.constant.EsConstant;
import com.theangel.themall.search.service.MallSearchService;
import com.theangel.themall.search.vo.SearchParam;
import com.theangel.themall.search.vo.SearchResult;
import org.apache.lucene.search.join.ScoreMode;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.index.query.*;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;

import java.io.IOException;

@Service
public class MallSearchServiceImpl implements MallSearchService {

    @Autowired
    RestHighLevelClient restHighLevelClient;

    @Override
    public SearchResult search(SearchParam searchParam) {
        SearchResponse searchResponse = null;
        //准备检索请求
        SearchRequest searchRequest = buildSearchRequest(searchParam);
        try {
            //执行请求
            searchResponse = restHighLevelClient.search(searchRequest, ElasticSearchConfig.COMMON_OPTIONS);
            //分析请求
            SearchResult searchResult = buildSearchResult(searchResponse);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 构建返回值
     *
     * @param searchResponse
     * @return
     */
    private SearchResult buildSearchResult(SearchResponse searchResponse) {

        return null;
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
        boolQuery.filter(QueryBuilders.termQuery("hasStock", searchParam.getHasStock() == 0 ? false : true));
        //1.2  filter  过滤  价格区间
        if (!ObjectUtils.isEmpty(searchParam.getSkuPrice())) {
            //1_500 -> 0到500    _500->500以内- 500_ ->500以上
            RangeQueryBuilder skuPrice = QueryBuilders.rangeQuery("skuPrice");
            String[] s = searchParam.getSkuPrice().split("_");
            if (2 == s.length) {
                //区间
                skuPrice.gte(s[0]).lte(s[1]);
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

        searchSourceBuilder.query(boolQuery);

        //2.排序，分页，高亮

        //3.聚合

        SearchRequest searchResult = new SearchRequest(new String[]{EsConstant.PRODUCT_INDEX}, searchSourceBuilder);
        return searchResult;
    }
}
