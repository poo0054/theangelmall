package com.theangel.themall.product.service.impl;

import com.theangel.themall.product.vo.SkuItemAttrVo;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.theangel.common.utils.PageUtils;
import com.theangel.common.utils.Query;

import com.theangel.themall.product.dao.SkuSaleAttrValueDao;
import com.theangel.themall.product.entity.SkuSaleAttrValueEntity;
import com.theangel.themall.product.service.SkuSaleAttrValueService;


@Service("skuSaleAttrValueService")
public class SkuSaleAttrValueServiceImpl extends ServiceImpl<SkuSaleAttrValueDao, SkuSaleAttrValueEntity> implements SkuSaleAttrValueService {

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        IPage<SkuSaleAttrValueEntity> page = this.page(
                new Query<SkuSaleAttrValueEntity>().getPage(params),
                new QueryWrapper<SkuSaleAttrValueEntity>()
        );

        return new PageUtils(page);
    }

    /**
     * 根据spuid获取所有属性
     *
     * @param spuId
     * @return
     */
    @Override
    public List<SkuItemAttrVo> getSaleAttrBySpuId(Long spuId) {
        List<SkuItemAttrVo> skuItemAttrVo = this.baseMapper.getSaleAttrBySpuId(spuId);
        return skuItemAttrVo;
    }

}