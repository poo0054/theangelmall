package com.themall.product.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.themall.common.utils.PageUtils;
import com.themall.product.pojo.entity.BrandEntity;
import com.themall.product.pojo.entity.CategoryBrandRelationEntity;

import java.util.List;
import java.util.Map;

/**
 * 品牌分类关联
 *
 * @author theangel
 * @email poo0054@outlook.com
 * @date 2021-06-09 20:47:30
 */
public interface CategoryBrandRelationService extends IService<CategoryBrandRelationEntity> {

    PageUtils queryPage(Map<String, Object> params);


    void saveDatail(CategoryBrandRelationEntity categoryBrandRelation);


    void updateBrand(Long brandId, String name);

    void updateCategoryIdAndName(Long catId, String name);

    List<BrandEntity> getBrandslis(Long catId);
}

