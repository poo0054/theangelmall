package com.theangel.themall.product.controller;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

//import org.apache.shiro.authz.annotation.RequiresPermissions;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.databind.util.JSONPObject;
//import com.theangel.themall.product.openfeign.ThirdPartyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.theangel.themall.product.entity.CategoryEntity;
import com.theangel.themall.product.service.CategoryService;
import com.theangel.common.utils.R;
import org.springframework.web.multipart.MultipartFile;


/**
 * 商品三级分类
 *
 * @author theangel
 * @email poo0054@outlook.com
 * @date 2021-06-09 20:47:30
 */
@RestController
@RequestMapping("product/category")
public class CategoryController {
    @Autowired
    private CategoryService categoryService;
  /*  @Autowired
    private ThirdPartyService thirdPartyService;
*/
    /**
     * 查出所有分类,以树形菜单展示
     */
    @RequestMapping(value = "/tree", method = RequestMethod.GET)
    //@RequiresPermissions("product:category:list")
    public R list() {
        List<CategoryEntity> categoryEntityList = categoryService.listCategoryTree();
        return R.ok().put("data", categoryEntityList);
    }


    /**
     * 信息
     */
    @RequestMapping(value = "/info/{catId}", method = RequestMethod.GET)
    //@RequiresPermissions("product:category:info")
    public R info(@PathVariable("catId") Long catId) {
        CategoryEntity category = categoryService.getById(catId);

        return R.ok().put("data", category);
    }

    /**
     * 保存
     */
    @RequestMapping(value = "/save", method = RequestMethod.POST)
    //@RequiresPermissions("product:category:save")
    public R save(@RequestBody CategoryEntity category) {
        categoryService.addCategory(category);
        return R.ok();
    }

    /**
     * 修改
     */
    @RequestMapping(value = "/update", method = RequestMethod.POST)
    //@RequiresPermissions("product:category:update")
    public R update(@RequestBody CategoryEntity category) {
        categoryService.updateById(category);
        return R.ok();
    }

    /**
     * 拖拽
     */
    @RequestMapping(value = "/update/list", method = RequestMethod.POST)
    //@RequiresPermissions("product:category:update")
    public R updateList(@RequestBody List<CategoryEntity> category) {
        categoryService.updateBatchById(category);
        return R.ok();
    }

    /**
     * 删除
     */
    @RequestMapping(value = "/delete", method = RequestMethod.POST)
    //@RequiresPermissions("product:category:delete")
    public R delete(@RequestBody Long[] catIds) {
//        categoryService.removeByIds(Arrays.asList(catIds));
        categoryService.removeMenuById(Arrays.asList(catIds));
        return R.ok();
    }

//    @RequestMapping(value = "/files", method = RequestMethod.POST)
    public R files(@RequestParam("file") MultipartFile[] files) {
        Map map = new HashMap<>();
        boolean b = false;
        for (MultipartFile file : files) {
//            Map<String, String> stringStringMap = thirdPartyService.cosTenGoodsLogo(file);
        }
        JSONObject json = new JSONObject();
        json.putAll(map);
        if (b) {
            return R.ok(json);
        } else {
            return R.error(json.toJSONString());
        }
    }

}
