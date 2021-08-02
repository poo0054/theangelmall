package com.theangel.themall.product.dao;

import com.theangel.themall.product.entity.AttrEntity;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
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
