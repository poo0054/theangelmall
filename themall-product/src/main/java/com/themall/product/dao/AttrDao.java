package com.themall.product.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.themall.product.entity.AttrEntity;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 商品属性
 *
 * @author theangel
 * @email poo0054@outlook.com
 * @date 2021-06-09 20:47:30
 */
@Mapper
public interface AttrDao extends BaseMapper<AttrEntity> {

    List<Long> listSearchAttrIds(@Param("attrIds") List<Long> attrIds);

}
