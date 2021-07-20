package com.theangel.themall.product.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.theangel.common.utils.PageUtils;
import com.theangel.themall.product.entity.AttrEntity;
import com.theangel.themall.product.vo.AttrGroupRelationVo;
import com.theangel.themall.product.vo.AttrResVo;
import com.theangel.themall.product.vo.AttrVo;


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
}

