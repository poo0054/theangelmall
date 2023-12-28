package com.themall.product.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.themall.product.pojo.entity.CategoryEntity;
import org.apache.ibatis.annotations.Mapper;

/**
 * 商品三级分类
 *
 * @author theangel
 * @email poo0054@outlook.com
 * @date 2021-06-09 20:47:30
 */
@Mapper
public interface CategoryDao extends BaseMapper<CategoryEntity> {

}
