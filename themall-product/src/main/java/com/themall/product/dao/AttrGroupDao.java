package com.themall.product.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.themall.product.pojo.entity.AttrGroupEntity;
import com.themall.product.pojo.vo.SpuItemAttrGroupVo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 属性分组
 *
 * @author theangel
 * @email poo0054@outlook.com
 * @date 2021-06-09 20:47:30
 */
@Mapper
public interface AttrGroupDao extends BaseMapper<AttrGroupEntity> {

    List<SpuItemAttrGroupVo> attrGroupWithAttrBySpuId(@Param("spuId") Long spuId);
}
