package com.theangel.themall.product.service.impl;

import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.theangel.themall.product.dao.BrandDao;
import com.theangel.themall.product.dao.CategoryDao;
import com.theangel.themall.product.entity.BrandEntity;
import com.theangel.themall.product.entity.CategoryEntity;
import org.aspectj.weaver.patterns.ThisOrTargetAnnotationPointcut;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Map;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.theangel.common.utils.PageUtils;
import com.theangel.common.utils.Query;

import com.theangel.themall.product.dao.CategoryBrandRelationDao;
import com.theangel.themall.product.entity.CategoryBrandRelationEntity;
import com.theangel.themall.product.service.CategoryBrandRelationService;


@Service("categoryBrandRelationService")
public class CategoryBrandRelationServiceImpl extends ServiceImpl<CategoryBrandRelationDao, CategoryBrandRelationEntity> implements CategoryBrandRelationService {


    @Autowired
    private BrandDao brandDao;

    @Autowired
    private CategoryDao categoryDao;

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        IPage<CategoryBrandRelationEntity> page = this.page(
                new Query<CategoryBrandRelationEntity>().getPage(params),
                new QueryWrapper<CategoryBrandRelationEntity>()
        );

        return new PageUtils(page);
    }

    @Override
    public PageUtils brandIdPage(Long brandId) {

        return null;
    }

    @Override
    public void saveDatail(CategoryBrandRelationEntity categoryBrandRelation) {
        Long brandId = categoryBrandRelation.getBrandId();
        Long catelogId = categoryBrandRelation.getCatelogId();
        BrandEntity brandEntity = brandDao.selectById(brandId);
        CategoryEntity categoryEntity = categoryDao.selectById(catelogId);
        categoryBrandRelation.setBrandName(brandEntity.getName());
        categoryBrandRelation.setCatelogName(categoryEntity.getName());
        boolean save = this.save(categoryBrandRelation);
    }

    @Override
    public void updateBrand(Long brandId, String name) {
        CategoryBrandRelationEntity brandEntity = new CategoryBrandRelationEntity();
        brandEntity.setBrandId(brandId);
        brandEntity.setBrandName(name);
        this.update(brandEntity, new UpdateWrapper<CategoryBrandRelationEntity>().eq("brand_id", brandId));
    }

    @Override
    public void updateCategoryIdAndName(Long catId, String name) {
        CategoryBrandRelationEntity brandEntity = new CategoryBrandRelationEntity();
        brandEntity.setCatelogId(catId);
        brandEntity.setCatelogName(name);
        boolean catelog_id = this.update(brandEntity, new UpdateWrapper<CategoryBrandRelationEntity>().eq("catelog_id", catId));
    }


}