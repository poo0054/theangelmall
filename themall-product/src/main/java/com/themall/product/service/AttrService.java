package com.themall.product.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.themall.common.utils.PageUtils;
import com.themall.product.pojo.entity.AttrEntity;
import com.themall.product.pojo.vo.AttrGroupRelationVo;
import com.themall.product.pojo.vo.AttrResVo;
import com.themall.product.pojo.vo.AttrVo;

import java.util.List;
import java.util.Map;

/**
 * 商品属性
 *
 * @author theangel
 * @email poo0054@outlook.com
 * @date 2021-06-09 20:47:30
 */
public interface AttrService extends IService<AttrEntity> {

    PageUtils queryPage(Map<String, Object> params);

    void saveAttr(AttrVo attrVo);

    PageUtils queryBaseAttrPage(Map<String, Object> params, Long catelogId, String type);

    AttrResVo getAttrInfo(Long attrId);

    void updateAttr(AttrVo attr);

    List<AttrEntity> attrRelation(Long attrgroupId);

    void deleteRelation(List<AttrGroupRelationVo> attrGroupRelationVo);

    PageUtils noAttrRelation(Map<String, Object> params, Long attrgroupId);

    List<Long> listSearchAttrIds(List<Long> attrIds);
}

