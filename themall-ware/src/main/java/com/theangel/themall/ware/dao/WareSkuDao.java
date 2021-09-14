package com.theangel.themall.ware.dao;

import com.theangel.themall.ware.entity.WareSkuEntity;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 商品库存
 *
 * @author theangel
 * @email poo0054@outlook.com
 * @date 2021-06-10 20:02:13
 */
@Mapper
public interface WareSkuDao extends BaseMapper<WareSkuEntity> {


    void addStack(@Param("skuId") Long skuId, @Param("wareId") Long wareId, @Param("skuNum") Integer skuNum);

    Long getHasStock(@Param("skuId") Long skuId);


    /**
     * 根据skuid查询出所有有库存的  仓库id
     *
     * @param skuId
     * @return
     */
    List<Long> listWareIdHakSkuStock(@Param("skuId") Long skuId);

    /**
     * 锁定库存
     *
     * @param skuId
     * @param wareId
     * @param num
     */
    Integer lockSkuStock(@Param("skuId") Long skuId, @Param("wareId") Long wareId, @Param("num") Integer num);
}
