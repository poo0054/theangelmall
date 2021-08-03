package com.theangel.themall.product.web;

import com.theangel.themall.product.entity.CategoryEntity;
import com.theangel.themall.product.service.CategoryService;
import com.theangel.themall.product.vo.Catelog2Vo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;
import java.util.Map;

@Controller
public class indexController {

    @Autowired
    CategoryService categoryService;

    @GetMapping({"/", "/index.html"})
    public String indexPage(Model model) {
        List<CategoryEntity> categoryEntities = categoryService.getLevel1Categorys();
        model.addAttribute("categorys", categoryEntities);
        return "index";
    }

    @ResponseBody
    @GetMapping("index/catalog.json")
    public Map<String, List<Catelog2Vo>> indexCatalog(Model model) {
        Map<String, List<Catelog2Vo>> categoryEntities = categoryService.indexCatalog();
        model.addAttribute("categorys", categoryEntities);
        return categoryEntities;
    }
}
