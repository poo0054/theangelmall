package com.theangel.themall.product.controller;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

//import org.apache.shiro.authz.annotation.RequiresPermissions;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.theangel.themall.product.entity.BrandEntity;
import com.theangel.themall.product.vo.BrandVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.theangel.themall.product.entity.CategoryBrandRelationEntity;
import com.theangel.themall.product.service.CategoryBrandRelationService;
import com.theangel.common.utils.PageUtils;
import com.theangel.common.utils.R;


/**
 * 品牌分类关联
 *
 * @author theangel
 * @email poo0054@outlook.com
 * @date 2021-06-09 20:47:30
 */
@RestController
@RequestMapping("product/categorybrandrelation")
public class CategoryBrandRelationController {
    @Autowired
    private CategoryBrandRelationService categoryBrandRelationService;

    /**
     * 列表
     */
    @RequestMapping("/list")
    //@RequiresPermissions("product:categorybrandrelation:list")
    public R list(@RequestParam Map<String, Object> params) {
        PageUtils page = categoryBrandRelationService.queryPage(params);
        return R.ok().put("page", page);
    }

    /**
     * /product/categorybrandrelation/brands/list
     */
    @GetMapping("/brands/list")
    public R brandslist(@RequestParam(required = true, value = "catId") Long catId) {
//        PageUtils page = categoryBrandRelationService.queryPage(params);
        List<BrandEntity> brand_id = categoryBrandRelationService.getBrandslis(catId);

        List<BrandVo> collect = brand_id.stream().map(brandEntity -> {
            BrandVo brandVo = new BrandVo();
            brandVo.setBrandId(brandEntity.getBrandId());
            brandVo.setBrandName(brandEntity.getName());
            return brandVo;
        }).collect(Collectors.toList());
        return R.ok().put("data", collect);
    }

    /**
     * 品牌关联分类
     */
    @GetMapping("/catelog/list")
    //@RequiresPermissions("product:categorybrandrelation:list")
    public R cateloglist(@RequestParam Long brandId) {
//        PageUtils page = categoryBrandRelationService.queryPage(params);
        List<CategoryBrandRelationEntity> brand_id = categoryBrandRelationService.list(new QueryWrapper<CategoryBrandRelationEntity>().eq("brand_id", brandId));
        return R.ok().put("data", brand_id);
    }


    /**
     * 信息
     */
    @RequestMapping("/info/{id}")
    //@RequiresPermissions("product:categorybrandrelation:info")
    public R info(@PathVariable("id") Long id) {
        CategoryBrandRelationEntity categoryBrandRelation = categoryBrandRelationService.getById(id);

        return R.ok().put("categoryBrandRelation", categoryBrandRelation);
    }

    /**
     * 保存
     */
    @RequestMapping("/save")
    //@RequiresPermissions("product:categorybrandrelation:save")
    public R save(@RequestBody CategoryBrandRelationEntity categoryBrandRelation) {
        categoryBrandRelationService.saveDatail(categoryBrandRelation);

        return R.ok();
    }

    /**
     * 修改
     */
    @RequestMapping("/update")
    //@RequiresPermissions("product:categorybrandrelation:update")
    public R update(@RequestBody CategoryBrandRelationEntity categoryBrandRelation) {
        categoryBrandRelationService.updateById(categoryBrandRelation);

        return R.ok();
    }

    /**
     * 删除
     */
    @RequestMapping("/delete")
    //@RequiresPermissions("product:categorybrandrelation:delete")
    public R delete(@RequestBody Long[] ids) {
        categoryBrandRelationService.removeByIds(Arrays.asList(ids));

        return R.ok();
    }

}
