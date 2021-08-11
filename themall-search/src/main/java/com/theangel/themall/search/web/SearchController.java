package com.theangel.themall.search.web;

import com.theangel.themall.search.service.MallSearchService;
import com.theangel.themall.search.vo.SearchParam;
import com.theangel.themall.search.vo.SearchResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class SearchController {

    @Autowired
    MallSearchService searchService;

    /**
     * @param searchParam 查询的所有参数
     * @return 返回给页面的所有结果
     */
    @GetMapping("/list.html")
    public String getSearch(SearchParam searchParam, Model model) {
        SearchResult search = searchService.search(searchParam);
        model.addAttribute("result", search);
        return "list";
    }
}
