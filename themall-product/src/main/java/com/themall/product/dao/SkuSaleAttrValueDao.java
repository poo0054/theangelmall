package com.themall.product.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.themall.product.pojo.entity.SkuSaleAttrValueEntity;
import com.themall.product.pojo.vo.SkuItemAttrVo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * sku销售属性&值
 *
 * @author theangel
 * @email poo0054@outlook.com
 * @date 2021-06-09 20:47:30
 */
@Mapper
public interface SkuSaleAttrValueDao extends BaseMapper<SkuSaleAttrValueEntity> {

    List<SkuItemAttrVo> getSaleAttrBySpuId(@Param("spuId") Long spuId);

    List<String> getAttrStrBySkuId(@Param("skuId") Long skuId);
}
