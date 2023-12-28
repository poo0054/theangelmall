package com.themall.product.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.themall.common.utils.PageUtils;
import com.themall.common.utils.Query;
import com.themall.product.dao.SkuSaleAttrValueDao;
import com.themall.product.pojo.entity.SkuSaleAttrValueEntity;
import com.themall.product.pojo.vo.SkuItemAttrVo;
import com.themall.product.service.SkuSaleAttrValueService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;


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

    @Override
    public List<String> getAttrStrBySkuId(Long skuId) {
        return this.baseMapper.getAttrStrBySkuId(skuId);
    }

}