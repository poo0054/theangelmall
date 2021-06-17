package com.theangel.themall.product.service.impl;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.theangel.common.utils.PageUtils;
import com.theangel.common.utils.Query;

import com.theangel.themall.product.dao.CategoryDao;
import com.theangel.themall.product.entity.CategoryEntity;
import com.theangel.themall.product.service.CategoryService;
import org.springframework.util.comparator.ComparableComparator;


@Service("categoryService")
public class CategoryServiceImpl extends ServiceImpl<CategoryDao, CategoryEntity> implements CategoryService {

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        IPage<CategoryEntity> page = this.page(
                new Query<CategoryEntity>().getPage(params),
                new QueryWrapper<CategoryEntity>()
        );

        return new PageUtils(page);
    }

    @Override
    public List<CategoryEntity> listCategoryTree() {
        List<CategoryEntity> categoryEntities = baseMapper.selectList(null);
        List<CategoryEntity> collect = categoryEntities
                .stream()
                .filter(categoryEntity ->
                        categoryEntity.getParentCid() == 0
                )
                .map(categoryEntity -> {
                    categoryEntity.setChildren(getChildren(categoryEntity, categoryEntities));
                    return categoryEntity;
                })
                .sorted((v1, v2) -> (v1.getSort() == null ? 0 : v1.getSort()) - (v2.getSort() == null ? 0 : v2.getSort()))
                .collect(Collectors.toList());
        return collect;
    }

    @Override
    public void removeMenuById(List<Long> asList) {
        baseMapper.deleteBatchIds(asList);
    }

    /**
     * 获取下级菜单
     *
     * @param categoryEntity
     * @param categoryEntities
     * @return
     */
    private List<CategoryEntity> getChildren(CategoryEntity categoryEntity, List<CategoryEntity> categoryEntities) {
        return categoryEntities
                .stream()
                .filter(v -> categoryEntity.getCatId() == v.getParentCid())
                .map(categoryentity -> {
                    categoryentity.setChildren(getChildren(categoryentity, categoryEntities));
                    return categoryentity;
                })
                .sorted((v1, v2) -> (v1.getSort() == null ? 0 : v1.getSort()) - (v2.getSort() == null ? 0 : v2.getSort()))
                .collect(Collectors.toList());
    }

}