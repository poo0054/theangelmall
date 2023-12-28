package com.themall.product.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.themall.common.utils.PageUtils;
import com.themall.common.utils.Query;
import com.themall.product.dao.BrandDao;
import com.themall.product.pojo.entity.BrandEntity;
import com.themall.product.service.BrandService;
import com.themall.product.service.CategoryBrandRelationService;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;


@Service("brandService")
public class BrandServiceImpl extends ServiceImpl<BrandDao, BrandEntity> implements BrandService {

    private CategoryBrandRelationService categoryBrandRelationService;

    @Autowired
    public void setCategoryBrandRelationService(CategoryBrandRelationService categoryBrandRelationService) {
        this.categoryBrandRelationService = categoryBrandRelationService;
    }

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        Object key = params.get("key");
        LambdaQueryWrapper<BrandEntity> queryWrapper = Wrappers.lambdaQuery(BrandEntity.class);
        if (ObjectUtils.isNotEmpty(key)) {
            queryWrapper.eq(BrandEntity::getBrandId, key).
                    or().eq(BrandEntity::getName, key).
                    or().eq(BrandEntity::getDescript, key);
        }
        IPage<BrandEntity> page = this.page(new Query<BrandEntity>().getPage(params), queryWrapper);
        return new PageUtils(page);
    }

    @Override
    @Transactional
    public void updateDetail(BrandEntity brand) {
        this.updateById(brand);
        if (!StringUtils.isEmpty(brand.getName())) {
            categoryBrandRelationService.updateBrand(brand.getBrandId(), brand.getName());
        }
    }

    @Cacheable(value = "brand", key = "#root.methodName")
    @Override
    public List<BrandEntity> getBrandsById(List<Long> brandIds) {
        return this.listByIds(brandIds);
    }

}