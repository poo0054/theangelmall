package com.themall.product.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.themall.common.utils.PageUtils;
import com.themall.product.pojo.entity.CategoryEntity;
import com.themall.product.pojo.vo.Catelog2Vo;

import java.util.List;
import java.util.Map;

/**
 * 商品三级分类
 *
 * @author theangel
 * @email poo0054@outlook.com
 * @date 2021-06-09 20:47:30
 */
public interface CategoryService extends IService<CategoryEntity> {

    PageUtils queryPage(Map<String, Object> params);

    List<CategoryEntity> listCategoryTree();

    Integer removeMenuById(List<Long> asList);


    Integer addCategory(CategoryEntity categoryEntity);

    Long[] getCateLogPath(Long catelogId);

    void updateDetail(CategoryEntity category);

    List<CategoryEntity> getLevel1Categorys();

    Map<String, List<Catelog2Vo>> getCatalogJson();
}

