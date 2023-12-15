package com.themall.product.controller;

import com.themall.model.entity.R;
import com.themall.product.entity.CategoryEntity;
import com.themall.product.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.List;


/**
 * 商品三级分类
 *
 * @author theangel
 * @email poo0054@outlook.com
 * @date 2021-06-09 20:47:30
 */
@RestController
@RequestMapping("category")
public class CategoryController {
    @Autowired
    private CategoryService categoryService;

    /**
     * 查出所有分类,以树形菜单展示
     */
    @GetMapping(value = "/list/tree")
    @PreAuthorize("hasAuthority('product:category:list')")
    public R list() {
        List<CategoryEntity> categoryEntityList = categoryService.listCategoryTree();
        return R.httpStatus().put("data", categoryEntityList);
    }


    /**
     * 信息
     */
    @RequestMapping(value = "/info/{catId}", method = RequestMethod.GET)
    @PreAuthorize("hasAuthority('product:category:list')")
    public R info(@PathVariable("catId") Long catId) {
        CategoryEntity category = categoryService.getById(catId);

        return R.httpStatus().put("data", category);
    }

    /**
     * 保存
     */
    @RequestMapping(value = "/save", method = RequestMethod.POST)
    @PreAuthorize("hasAuthority('product:category:save')")
    public R save(@RequestBody CategoryEntity category) {
        categoryService.addCategory(category);
        return R.httpStatus();
    }

    /**
     * 修改
     */
    @PostMapping(value = "/update")
    @PreAuthorize("hasAuthority('product:category:update')")
    public R update(@RequestBody CategoryEntity category) {
        categoryService.updateDetail(category);
        return R.httpStatus();
    }

    /**
     * 拖拽
     */
    @RequestMapping(value = "/update/list", method = RequestMethod.POST)
    @PreAuthorize("hasAuthority('product:category:update')")
    public R updateList(@RequestBody List<CategoryEntity> category) {
        categoryService.updateBatchById(category);
        return R.httpStatus();
    }

    /**
     * 删除
     */
    @RequestMapping(value = "/delete", method = RequestMethod.POST)
    @PreAuthorize("hasAuthority('product:category:delete')")
    public R delete(@RequestBody Long[] catIds) {
        categoryService.removeMenuById(Arrays.asList(catIds));
        return R.httpStatus();
    }

}
