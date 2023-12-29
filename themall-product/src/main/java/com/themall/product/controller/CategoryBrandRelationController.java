package com.themall.product.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.themall.common.utils.PageUtils;
import com.themall.model.entity.R;
import com.themall.product.pojo.entity.BrandEntity;
import com.themall.product.pojo.entity.CategoryBrandRelationEntity;
import com.themall.product.pojo.vo.BrandVo;
import com.themall.product.service.CategoryBrandRelationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;


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

    private CategoryBrandRelationService categoryBrandRelationService;

    @Autowired
    public void setCategoryBrandRelationService(CategoryBrandRelationService categoryBrandRelationService) {
        this.categoryBrandRelationService = categoryBrandRelationService;
    }

    /**
     * 列表
     */
    @RequestMapping("/list")
    @PreAuthorize("hasAuthority('product:brand:list')")
    public R list(@RequestParam Map<String, Object> params) {
        PageUtils page = categoryBrandRelationService.queryPage(params);
        return R.status().put("page", page);
    }

    /**
     * /product/categorybrandrelation/brands/list
     */
    @GetMapping("/brands/list")
    @PreAuthorize("hasAuthority('product:brand:list')")
    public R brandslist(@RequestParam(value = "catId") Long catId) {
        List<BrandEntity> brand_id = categoryBrandRelationService.getBrandslis(catId);

        List<BrandVo> collect = brand_id.stream().map(brandEntity -> {
            BrandVo brandVo = new BrandVo();
            brandVo.setBrandId(brandEntity.getBrandId());
            brandVo.setBrandName(brandEntity.getName());
            return brandVo;
        }).collect(Collectors.toList());
        return R.status().setData(collect);
    }

    /**
     * 品牌查询所有分类
     */
    @GetMapping("/catelog/list")
    @PreAuthorize("hasAuthority('product:brand:list')")
    public R cateloglist(@RequestParam Long brandId) {
        List<CategoryBrandRelationEntity> brand_id = categoryBrandRelationService.list(new QueryWrapper<CategoryBrandRelationEntity>().eq("brand_id", brandId));
        return R.status().setData(brand_id);
    }


    /**
     * 信息
     */
    @RequestMapping("/info/{id}")
    @PreAuthorize("hasAuthority('product:brand:list')")
    public R info(@PathVariable("id") Long id) {
        CategoryBrandRelationEntity categoryBrandRelation = categoryBrandRelationService.getById(id);
        return R.status().put("categoryBrandRelation", categoryBrandRelation);
    }

    /**
     * 保存
     */
    @RequestMapping("/save")
    @PreAuthorize("hasAuthority('product:brand:save')")
    public R save(@RequestBody CategoryBrandRelationEntity categoryBrandRelation) {
        categoryBrandRelationService.saveDatail(categoryBrandRelation);

        return R.status();
    }

    /**
     * 修改
     */
    @RequestMapping("/update")
    @PreAuthorize("hasAuthority('product:brand:update')")
    public R update(@RequestBody CategoryBrandRelationEntity categoryBrandRelation) {
        categoryBrandRelationService.updateById(categoryBrandRelation);

        return R.status();
    }

    /**
     * 删除
     */
    @RequestMapping("/delete")
    @PreAuthorize("hasAuthority('product:brand:delete')")
    public R delete(@RequestBody Long[] ids) {
        categoryBrandRelationService.removeByIds(Arrays.asList(ids));

        return R.status();
    }

}
