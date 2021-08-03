package com.theangel.themall.product.service.impl;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.theangel.themall.product.service.CategoryBrandRelationService;
import com.theangel.themall.product.vo.Catelog2Vo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.stream.Collectors;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.theangel.common.utils.PageUtils;
import com.theangel.common.utils.Query;

import com.theangel.themall.product.dao.CategoryDao;
import com.theangel.themall.product.entity.CategoryEntity;
import com.theangel.themall.product.service.CategoryService;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;
import org.springframework.util.comparator.ComparableComparator;


@Service("categoryService")
public class CategoryServiceImpl extends ServiceImpl<CategoryDao, CategoryEntity> implements CategoryService {

    @Autowired
    private CategoryBrandRelationService categoryBrandRelationService;

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
        CopyOnWriteArrayList<CategoryEntity> categoryEntities = new CopyOnWriteArrayList<>(baseMapper.selectList(null));
        List<CategoryEntity> collect = categoryEntities
                .parallelStream()
                .filter(categoryEntity ->
                        categoryEntity.getParentCid().equals(0L)
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
    public Integer removeMenuById(List<Long> asList) {
        return baseMapper.deleteBatchIds(asList);
    }

    @Override
    public Integer addCategory(CategoryEntity categoryEntity) {
        return baseMapper.insert(categoryEntity);
    }

    @Override
    public Long[] getCateLogPath(Long catelog) {
        List<Long> longs = new ArrayList<>();
        List<Long> parentCateLogId = getParentCateLogId(catelog, longs);
        Collections.reverse(parentCateLogId);
        return parentCateLogId.toArray(new Long[parentCateLogId.size()]);
    }

    @Override
    @Transactional
    public void updateDetail(CategoryEntity category) {
        this.updateById(category);
        if (!StringUtils.isEmpty(category.getName())) {
            categoryBrandRelationService.updateCategoryIdAndName(category.getCatId(), category.getName());
        }
    }

    private List<Long> getParentCateLogId(Long catelogId, List<Long> longs) {
        longs.add(catelogId);
        CategoryEntity byId = this.getById(catelogId);
        if (0 != byId.getParentCid()) {
            getParentCateLogId(byId.getParentCid(), longs);
        }
        return longs;
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
                .filter(v -> categoryEntity.getCatId().equals(v.getParentCid()))
                .map(categoryentity -> {
                    categoryentity.setChildren(getChildren(categoryentity, categoryEntities));
                    return categoryentity;
                })
                .sorted(Comparator.comparingInt(v -> (v.getSort() == null ? 0 : v.getSort())))
//                .sorted((v1, v2) -> (v1.getSort() == null ? 0 : v1.getSort()) - (v2.getSort() == null ? 0 : v2.getSort()))
                .collect(Collectors.toList());
    }


    /**
     * 查询所有一级分类
     *
     * @return
     */
    @Override
    public List<CategoryEntity> getLevel1Categorys() {
        List<CategoryEntity> parent_cid = this.list(new QueryWrapper<CategoryEntity>().eq("parent_cid", 0));
        return parent_cid;
    }

    @Override
    public Map<String, List<Catelog2Vo>> indexCatalog() {
        //查询所有1级分类
        List<CategoryEntity> level1Categorys = this.getLevel1Categorys();
        Map<String, List<Catelog2Vo>> collect1 = level1Categorys.stream().collect(Collectors.toMap(k -> k.getCatId().toString(), v1 -> {
            List<CategoryEntity> categoryEntities = this.list(new QueryWrapper<CategoryEntity>().eq("parent_cid", v1.getCatId()));
            List<Catelog2Vo> catelog2Vos = null;
            if (!ObjectUtils.isEmpty(categoryEntities)) {
                catelog2Vos = categoryEntities.stream().map(item -> {
                    //查询所有一级分类的二级分类
                    Catelog2Vo catelog2Vo = new Catelog2Vo(item.getParentCid() + "", item.getCatId() + "", null, item.getName());
                    //查询二级分类下上级分类
                    List<CategoryEntity> parent_cid2 = this.list(new QueryWrapper<CategoryEntity>().eq("parent_cid", item.getCatId()));
                    if (!ObjectUtils.isEmpty(parent_cid2)) {
                        List<Catelog2Vo.Catalog3Vo> catalog3Vos = parent_cid2.stream().map(categoryEntity -> {
                            Catelog2Vo.Catalog3Vo catalog3Vo = new Catelog2Vo.Catalog3Vo(categoryEntity.getParentCid() + "", categoryEntity.getCatId() + "", categoryEntity.getName());
                            return catalog3Vo;
                        }).collect(Collectors.toList());
                        catelog2Vo.setCatalog3List(catalog3Vos);
                    }
                    return catelog2Vo;
                }).collect(Collectors.toList());
            }
            return catelog2Vos;
        }));
        return collect1;
    }
}