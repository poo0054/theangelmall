package com.theangel.themall.product.service.impl;

import com.theangel.themall.product.dao.AttrAttrgroupRelationDao;
import com.theangel.themall.product.dao.AttrGroupDao;
import com.theangel.themall.product.dao.CategoryDao;
import com.theangel.themall.product.entity.AttrAttrgroupRelationEntity;
import com.theangel.themall.product.entity.AttrGroupEntity;
import com.theangel.themall.product.entity.CategoryEntity;
import com.theangel.themall.product.service.AttrAttrgroupRelationService;
import com.theangel.themall.product.vo.AttrResVo;
import com.theangel.themall.product.vo.AttrVo;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

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
        //保存基本数据
        this.save(attrEntity);
        //保存关联关系
        AttrAttrgroupRelationEntity relationEntity = new AttrAttrgroupRelationEntity();
        relationEntity.setAttrGroupId(attrVo.getAttrGroupId());
        relationEntity.setAttrId(attrEntity.getAttrId());
        attrAttrgroupRelationDao.insert(relationEntity);
    }

    @Override
    public PageUtils queryBaseAttrPage(Map<String, Object> params, Long catelogId) {
        QueryWrapper<AttrEntity> queryWrapper = new QueryWrapper<>();
        String key = (String) params.get("key");
        if (0 != catelogId) {
            queryWrapper.eq("catelog_id", catelogId);
            if (!StringUtils.isEmpty(key)) {
                queryWrapper.and((wrapper) -> {
                    queryWrapper.like("attr_id", key).or().like("attr_name", key);
                });
            }
        } else {
            if (!StringUtils.isEmpty(key)) {
                queryWrapper.like("attr_id", key).or().like("attr_name", key);
            }
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
            //根据商品属性id  在关联表  查询属性分组id
            AttrAttrgroupRelationEntity attr_id = attrAttrgroupRelationDao.selectOne(new QueryWrapper<AttrAttrgroupRelationEntity>().eq("attr_id", attrEntity.getAttrId()));
            //属性分组id
            if (!ObjectUtils.isEmpty(attr_id)) {
                AttrGroupEntity attrGroupEntity = attrGroupDao.selectById(attr_id.getAttrGroupId());
                //设置属性分组名字
                attrResVo.setGroupName(attrGroupEntity.getAttrGroupName());
            }
            CategoryEntity categoryEntity = categoryDao.selectById(attrEntity.getCatelogId());
            if (!ObjectUtils.isEmpty(categoryEntity)) {
                //设置分组名字
                attrResVo.setCatelogName(categoryEntity.getName());
            }
            return attrResVo;
        })).collect(Collectors.toList());

        pageUtils.setList(collect);

        return pageUtils;
    }

}