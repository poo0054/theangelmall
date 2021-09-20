package com.theangel.themall.ware.service.impl;

import com.theangel.common.exception.NoStockException;
import com.theangel.common.utils.R;
import com.theangel.themall.ware.openfeign.ProductFeignService;
import com.theangel.common.to.SkuHasStockVo;
import com.theangel.themall.ware.to.OrderItemTo;
import com.theangel.themall.ware.to.WareSkuLockTo;
import lombok.Data;
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
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ObjectUtils;


@Service("wareSkuService")
public class WareSkuServiceImpl extends ServiceImpl<WareSkuDao, WareSkuEntity> implements WareSkuService {
    @Autowired
    ProductFeignService productFeignService;
    @Autowired
    WareSkuDao wareSkuDao;

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

    /**
     * 锁定库存
     *
     * @param skuLockTo
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean OrderLock(WareSkuLockTo skuLockTo) {
        //TODO 按照下单的收货地址  找到就近库存  锁定库存

        List<OrderItemTo> locks = skuLockTo.getLocks();

        List<SkuWareHasStock> collect = locks.stream().map(item -> {
            SkuWareHasStock skuWareHasStock = new SkuWareHasStock();
            Long skuId = item.getSkuId();
            skuWareHasStock.setSkuId(skuId);
            //查询有库存的  仓库id
            List<Long> list = wareSkuDao.listWareIdHakSkuStock(skuId);
            skuWareHasStock.setWareId(list);

            skuWareHasStock.setNum(item.getCount());
            return skuWareHasStock;
        }).collect(Collectors.toList());

        //锁定库存
        for (SkuWareHasStock stock : collect) {
            //商品是否锁成功
            boolean skuIdLock = false;
            Long skuId = stock.getSkuId();
            List<Long> longList = stock.getWareId();
            if (ObjectUtils.isEmpty(longList)) {
                //没有库存就直接抛异常   库存不足 无法下单
                throw new NoStockException(skuId);
            }
            for (Long wareId : longList) {
                Integer integer = wareSkuDao.lockSkuStock(skuId, wareId, stock.getNum());
                if (1 == integer) {
                    skuIdLock = true;
                    //锁定成功跳出循环
                    break;
                }
            }
            //都没有锁住
            if (!skuIdLock) {
                //当前商品所有仓库没有锁住，就直接抛出
                throw new NoStockException(skuId);
            }

        }
        //所有锁定成功

        return true;

    }


    @Data
    class SkuWareHasStock {
        private Long skuId;
        /**
         * 仓库id
         * 当前商品在哪些仓库有库存
         */
        private List<Long> wareId;

        /**
         * 多少件
         */
        private Integer num;

    }


}