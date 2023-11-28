package com.themall.search.service;

import com.themall.search.vo.SearchParam;
import com.themall.search.vo.SearchResult;

public interface MallSearchService {

    /**
     * 页面搜索
     *
     * @param searchParam 检索的所有参数
     * @return 检索的所有结果
     */
    SearchResult search(SearchParam searchParam);
}
