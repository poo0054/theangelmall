package com.theangel.themall.ware.service.impl;

import com.theangel.common.utils.R;
import com.theangel.themall.ware.openfeign.ProductFeignService;
import com.theangel.common.to.SkuHasStockVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.theangel.common.utils.PageUtils;
import com.theangel.common.utils.Query;

import com.theangel.themall.ware.dao.WareSkuDao;
import com.theangel.themall.ware.entity.WareSkuEntity;
import com.theangel.themall.ware.service.WareSkuService;
import org.springframework.util.ObjectUtils;


@Service("wareSkuService")
public class WareSkuServiceImpl extends ServiceImpl<WareSkuDao, WareSkuEntity> implements WareSkuService {
    @Autowired
    ProductFeignService productFeignService;

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        /**
         * skuId ->
         * wareId ->
         */
        QueryWrapper<WareSkuEntity> queryWrapper = new QueryWrapper<>();
        String skuId = (String) params.get("skuId");
        if (!ObjectUtils.isEmpty(skuId)) {
            queryWrapper.eq("sku_id", skuId);
        }
        String wareId = (String) params.get("wareId");
        if (!ObjectUtils.isEmpty(wareId)) {
            queryWrapper.like("ware_id", wareId);
        }

        IPage<WareSkuEntity> page = this.page(
                new Query<WareSkuEntity>().getPage(params),
                queryWrapper
        );

        return new PageUtils(page);
    }

    @Override
    public void addStack(Long skuId, Long wareId, Integer skuNum) {
        //判断是否有这个库存
        WareSkuEntity list = this.getOne(new QueryWrapper<WareSkuEntity>()
                .eq("sku_id", skuId)
                .eq("ware_id", wareId));
        if (ObjectUtils.isEmpty(list)) {
            WareSkuEntity wareSkuEntity = new WareSkuEntity();
            wareSkuEntity.setSkuId(skuId);
            wareSkuEntity.setWareId(wareId);
            wareSkuEntity.setStockLocked(0);
            //  查询远程sku名称  事务不需要回滚

            try {
                R r = productFeignService.info(skuId);
                if (r.getCode() == 0) {
                    Map<String, Object> data = (Map) r.get("skuInfo");
                    wareSkuEntity.setSkuName((String) data.get("skuName"));
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            wareSkuEntity.setStock(skuNum);
            this.baseMapper.insert(wareSkuEntity);
        } else {
            Integer stock = list.getStock();
            skuNum += stock;
            this.baseMapper.addStack(skuId, wareId, skuNum);
        }
    }

    /**
     * 是否有库存
     *
     * @param skuIds
     * @return
     */
    @Override
    public List<SkuHasStockVo> getHasStock(List<Long> skuIds) {
        List<SkuHasStockVo> collect = skuIds.stream().map(skuId -> {
            SkuHasStockVo skuHasStockVo = new SkuHasStockVo();
            Long aLong = baseMapper.getHasStock(skuId);
            skuHasStockVo.setSkuId(skuId);
            if (!ObjectUtils.isEmpty(aLong)) {
                skuHasStockVo.setHasStock(aLong > 0);
            } else {
                skuHasStockVo.setHasStock(false);
            }
            return skuHasStockVo;
        }).filter(item -> {
            return !ObjectUtils.isEmpty(item);
        }).collect(Collectors.toList());
        return collect;
    }
}