package com.theangel.themall.product.service.impl;

import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.theangel.common.constant.productConstant;
import com.theangel.themall.product.dao.AttrAttrgroupRelationDao;
import com.theangel.themall.product.dao.AttrGroupDao;
import com.theangel.themall.product.dao.CategoryDao;
import com.theangel.themall.product.entity.AttrAttrgroupRelationEntity;
import com.theangel.themall.product.entity.AttrGroupEntity;
import com.theangel.themall.product.entity.CategoryEntity;
import com.theangel.themall.product.service.AttrAttrgroupRelationService;
import com.theangel.themall.product.service.CategoryService;
import com.theangel.themall.product.vo.AttrGroupRelationVo;
import com.theangel.themall.product.vo.AttrResVo;
import com.theangel.themall.product.vo.AttrVo;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.theangel.common.utils.PageUtils;
import com.theangel.common.utils.Query;

import com.theangel.themall.product.dao.AttrDao;
import com.theangel.themall.product.entity.AttrEntity;
import com.theangel.themall.product.service.AttrService;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;


@Service("attrService")
public class AttrServiceImpl extends ServiceImpl<AttrDao, AttrEntity> implements AttrService {

    @Autowired
    private AttrGroupDao attrGroupDao;

    @Autowired
    private CategoryDao categoryDao;

    @Autowired
    private AttrAttrgroupRelationDao attrAttrgroupRelationDao;

    @Autowired
    private CategoryService categoryService;

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        IPage<AttrEntity> page = this.page(
                new Query<AttrEntity>().getPage(params),
                new QueryWrapper<AttrEntity>()
        );
        return new PageUtils(page);
    }

    @Override
    @Transactional
    public void saveAttr(AttrVo attrVo) {
        AttrEntity attrEntity = new AttrEntity();
        BeanUtils.copyProperties(attrVo, attrEntity);
        //保存商品
        this.save(attrEntity);
        //保存分组属性
        if (attrVo.getAttrType() == productConstant.AttrEnum.ATTR_TYPE_ABSE.getCode() && !ObjectUtils.isEmpty(attrVo.getAttrGroupId())) {
            AttrAttrgroupRelationEntity relationEntity = new AttrAttrgroupRelationEntity();
            relationEntity.setAttrGroupId(attrVo.getAttrGroupId());
            relationEntity.setAttrId(attrEntity.getAttrId());
            attrAttrgroupRelationDao.insert(relationEntity);
        }

    }

    @Override
    public PageUtils queryBaseAttrPage(Map<String, Object> params, Long catelogId, String type) {
        QueryWrapper<AttrEntity> queryWrapper = new QueryWrapper<AttrEntity>().eq("attr_type", "base".equalsIgnoreCase(type) ? productConstant.AttrEnum.ATTR_TYPE_ABSE.getCode() : productConstant.AttrEnum.ATTR_TYPE_SALE.getCode());
        String key = (String) params.get("key");
        if (0 != catelogId) {
            queryWrapper.eq("catelog_id", catelogId);
        }
        if (!StringUtils.isEmpty(key)) {
            queryWrapper.and(wrapper -> {
                wrapper.like("attr_id", key).or().like("attr_name", key);
            });
        }
        IPage<AttrEntity> page = this.page(
                new Query<AttrEntity>().getPage(params),
                queryWrapper
        );
        PageUtils pageUtils = new PageUtils(page);
        List<AttrEntity> list = page.getRecords();
        List<AttrResVo> collect = list.stream().map((attrEntity -> {
            AttrResVo attrResVo = new AttrResVo();
            BeanUtils.copyProperties(attrEntity, attrResVo);

            //根据属性id在关联表  查询属性分组id
            AttrAttrgroupRelationEntity attr_id = attrAttrgroupRelationDao.selectOne(new QueryWrapper<AttrAttrgroupRelationEntity>().eq("attr_id", attrEntity.getAttrId()));
            //属性分组id
            if (!StringUtils.isEmpty(attr_id)) {
                AttrGroupEntity attrGroupEntity = attrGroupDao.selectById(attr_id.getAttrGroupId());
                //设置属性分组名字
                if (!ObjectUtils.isEmpty(attrGroupEntity)) {
                    attrResVo.setGroupName(attrGroupEntity.getAttrGroupName());
                }
            }
            CategoryEntity categoryEntity = categoryDao.selectById(attrEntity.getCatelogId());
            if (!StringUtils.isEmpty(categoryEntity)) {
                //设置分组名字
                attrResVo.setCatelogName(categoryEntity.getName());
            }

            return attrResVo;
        })).collect(Collectors.toList());
        pageUtils.setList(collect);
        return pageUtils;
    }

    @Override
    public AttrResVo getAttrInfo(Long attrId) {
        AttrResVo attrResVo = new AttrResVo();
        AttrEntity byId = this.getById(attrId);
        BeanUtils.copyProperties(byId, attrResVo);
        if (byId.getAttrType() == productConstant.AttrEnum.ATTR_TYPE_ABSE.getCode()) {
            //查询分组信息
            AttrAttrgroupRelationEntity relationEntity = attrAttrgroupRelationDao.selectOne(new QueryWrapper<AttrAttrgroupRelationEntity>().eq("attr_id", byId.getAttrId()));
            if (!ObjectUtils.isEmpty(relationEntity)) {
                attrResVo.setAttrGroupId(relationEntity.getAttrGroupId());
                AttrGroupEntity attrGroupEntity = attrGroupDao.selectById(relationEntity.getAttrGroupId());
                if (null != attrGroupEntity) {
                    attrResVo.setGroupName(attrGroupEntity.getAttrGroupName());
                }
            }
        }
        //查询分类信息
        Long catelogId = byId.getCatelogId();
        Long[] cateLogPath = categoryService.getCateLogPath(catelogId);
        if (!ObjectUtils.isEmpty(cateLogPath)) {
            attrResVo.setCatelogPath(cateLogPath);
            CategoryEntity categoryEntity = categoryDao.selectById(catelogId);
            if (!ObjectUtils.isEmpty(categoryEntity)) {
                attrResVo.setCatelogName(categoryEntity.getName());
            }
        }
        return attrResVo;
    }

    @Transactional
    @Override
    public void updateAttr(AttrVo attr) {
        AttrEntity attrEntity = new AttrEntity();
        BeanUtils.copyProperties(attr, attrEntity);
        this.updateById(attrEntity);
        if (attrEntity.getAttrType() == productConstant.AttrEnum.ATTR_TYPE_ABSE.getCode()) {
            //修改分组关系
            AttrAttrgroupRelationEntity attrAttrgroupRelationEntity = new AttrAttrgroupRelationEntity();
            attrAttrgroupRelationEntity.setAttrGroupId(attr.getAttrGroupId());
            attrAttrgroupRelationEntity.setAttrId(attr.getAttrId());
            Integer attr_id = attrAttrgroupRelationDao.selectCount(new QueryWrapper<AttrAttrgroupRelationEntity>().eq("attr_id", attr.getAttrId()));
            if (attr_id > 0) {
                attrAttrgroupRelationDao.update(attrAttrgroupRelationEntity, new UpdateWrapper<AttrAttrgroupRelationEntity>().eq("attr_id", attr.getAttrId()));
            } else {
                attrAttrgroupRelationDao.insert(attrAttrgroupRelationEntity);
            }
        }
    }


    /**
     * 根据分组id查询基本属性
     *
     * @param attrgroupId
     * @return
     */
    @Override
    public List<AttrEntity> attrRelation(Long attrgroupId) {
        List<AttrAttrgroupRelationEntity> attr_group_id = attrAttrgroupRelationDao.selectList(new QueryWrapper<AttrAttrgroupRelationEntity>().eq("attr_group_id", attrgroupId));
        List<Long> collect = attr_group_id.stream().map(attrAttrgroupRelationEntity -> attrAttrgroupRelationEntity.getAttrId()).collect(Collectors.toList());
        List<AttrEntity> attrEntities = this.listByIds(collect);
        if (ObjectUtils.isEmpty(attrEntities)) {
            return null;
        }
        return attrEntities;
    }

    @Override
    public void deleteRelation(List<AttrGroupRelationVo> attrGroupRelationVo) {
        //  attrAttrgroupRelationDao.delete(new QueryWrapper<>().eq("attr_id",).eq("attr_group_id"))
        List<AttrAttrgroupRelationEntity> collect = attrGroupRelationVo.stream().map(attrGroupRelationVo1 -> {
            AttrAttrgroupRelationEntity attrAttrgroupRelationEntity = new AttrAttrgroupRelationEntity();
            BeanUtils.copyProperties(attrGroupRelationVo1, attrAttrgroupRelationEntity);
            return attrAttrgroupRelationEntity;
        }).collect(Collectors.toList());
        attrAttrgroupRelationDao.deleteBatchRelation(collect);
    }

    /**
     * 获取分组还能关联的属性
     *
     * @param params
     * @param attrgroupId
     * @return
     */
    @Override
    public PageUtils noAttrRelation(Map<String, Object> params, Long attrgroupId) {
        //当前分组只能关联所属分类里面的属性
        AttrGroupEntity attrGroupEntity = attrGroupDao.selectById(attrgroupId);
        Long catelogId = attrGroupEntity.getCatelogId();
        //当前分组只能关联别的分组没有引用的属性
        //  当前分类下所有的分组
        List<AttrGroupEntity> attrGroupEntities = attrGroupDao.selectList(new QueryWrapper<AttrGroupEntity>().eq("catelog_id", catelogId));
        // 当前分类下所有的分组Id
        List<Long> collect = attrGroupEntities.stream().map(item -> item.getAttrGroupId()).collect(Collectors.toList());

        // 当前分类下所有分组的属性
        List<AttrAttrgroupRelationEntity> attr_group_id = attrAttrgroupRelationDao.selectList(new QueryWrapper<AttrAttrgroupRelationEntity>().in("attr_group_id", collect));
        // 当前分类下面所有属性的id
        List<Long> longStream = attr_group_id.stream().map(item -> item.getAttrId()).collect(Collectors.toList());

        // 当前分类下 没有绑定分组的所有属性
        QueryWrapper<AttrEntity> queryWrapper = new QueryWrapper<AttrEntity>().eq("catelog_id", catelogId).eq("attr_type", productConstant.AttrEnum.ATTR_TYPE_ABSE.getCode());

        if (!ObjectUtils.isEmpty(longStream)) {
            queryWrapper.notIn("attr_id", longStream);
        }

        String key = (String) params.get("key");
        if (!StringUtils.isEmpty(key)) {
            queryWrapper.and(wrapper -> {
                wrapper.like("attr_id", key).or().like("attr_name", key);
            });
        }
        IPage<AttrEntity> page = this.page(new Query<AttrEntity>().getPage(params), queryWrapper);

        return new PageUtils(page);
    }
}