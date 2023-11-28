package com.themall.search.web;

import com.themall.search.service.MallSearchService;
import com.themall.search.vo.SearchParam;
import com.themall.search.vo.SearchResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import javax.servlet.http.HttpServletRequest;

@Controller
public class SearchController {

    @Autowired
    MallSearchService searchService;

    /**
     * @param searchParam 查询的所有参数
     * @return 返回给页面的所有结果
     */
    @GetMapping("/list.html")
    public String getSearch(SearchParam searchParam, Model model, HttpServletRequest httpServletRequest) {
        //获取当前url地址
        searchParam.set_queryString(httpServletRequest.getQueryString());
        SearchResult search = searchService.search(searchParam);
        model.addAttribute("result", search);
        return "list";
    }
}
