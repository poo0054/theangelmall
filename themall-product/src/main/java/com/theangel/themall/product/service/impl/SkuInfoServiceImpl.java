package com.theangel.themall.product.service.impl;

import com.theangel.themall.product.entity.SkuImagesEntity;
import com.theangel.themall.product.entity.SpuInfoDescEntity;
import com.theangel.themall.product.service.*;
import com.theangel.themall.product.vo.SkuItemAttrVo;
import com.theangel.themall.product.vo.SkuItemVo;
import com.theangel.themall.product.vo.SpuItemAttrGroupVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ThreadPoolExecutor;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.theangel.common.utils.PageUtils;
import com.theangel.common.utils.Query;

import com.theangel.themall.product.dao.SkuInfoDao;
import com.theangel.themall.product.entity.SkuInfoEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;


@Service("skuInfoService")
public class SkuInfoServiceImpl extends ServiceImpl<SkuInfoDao, SkuInfoEntity> implements SkuInfoService {

    @Autowired
    SkuImagesService skuImagesService;
    @Autowired
    SpuInfoDescService spuInfoDescService;
    @Autowired
    AttrGroupService attrGroupService;
    @Autowired
    SkuSaleAttrValueService skuSaleAttrValueService;
    @Autowired
    ThreadPoolExecutor poolExecutor;


    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        IPage<SkuInfoEntity> page = this.page(
                new Query<SkuInfoEntity>().getPage(params),
                new QueryWrapper<SkuInfoEntity>()
        );

        return new PageUtils(page);
    }

    @Override
    public PageUtils queryPageByCondition(Map<String, Object> params) {
        QueryWrapper<SkuInfoEntity> queryWrapper = new QueryWrapper<>();
        /**
         *    key:
         * catelogId: 0
         * brandId: 0
         * min: 0
         * max: 0
         */
        String key = (String) params.get("key");
        if (!StringUtils.isEmpty(key)) {
            queryWrapper.and(item -> {
                item.eq("sku_id", key).or().like("sku_name", key);
            });
        }
        String catelogId = (String) params.get("catelogId");
        if (!ObjectUtils.isEmpty(catelogId) && !catelogId.equals("0")) {
            queryWrapper.eq("catalog_id", catelogId);
        }
        String brandId = (String) params.get("brandId");
        if (!ObjectUtils.isEmpty(brandId) && !brandId.equals("0")) {
            queryWrapper.eq("brand_id", brandId);
        }
        String min = (String) params.get("min");
        if (!ObjectUtils.isEmpty(min) && !min.equals("0")) {
            BigDecimal bigDecimal = new BigDecimal(min);
            queryWrapper.ge("price", bigDecimal);
        }

        String max = (String) params.get("max");
        if (!ObjectUtils.isEmpty(max) && !max.equals("0")) {
            BigDecimal bigDecimal = new BigDecimal(max);
            queryWrapper.le("price", bigDecimal);
        }

        IPage<SkuInfoEntity> page = this.page(
                new Query<SkuInfoEntity>().getPage(params),
                queryWrapper
        );

        return new PageUtils(page);
    }


    /**
     * 根据spu  查出sku对应的所有信息  品牌名称
     *
     * @param spuId
     * @return
     */
    @Override
    public List<SkuInfoEntity> getSkuInfoBySpuId(Long spuId) {
        List<SkuInfoEntity> spu_id = this.list(new QueryWrapper<SkuInfoEntity>().eq("spu_id", spuId));
        return spu_id;
    }

    /**
     * 根据skuid查询sku商品信息展示
     *
     * @param skyId
     * @return
     */
    @Override
    @Cacheable(value = {"product"}, key = "#root.methodName+':'+#root.args[0]")
    public SkuItemVo itemBySkuId(Long skyId) throws ExecutionException, InterruptedException {
        SkuItemVo skuItemVo = new SkuItemVo();
        //sku基本属性
        CompletableFuture<SkuInfoEntity> supplyAsync = CompletableFuture.supplyAsync(() -> {
            //1 sku基本信息
            SkuInfoEntity byId = getById(skyId);
            skuItemVo.setSkuInfo(byId);
            return byId;
        }, poolExecutor);

        CompletableFuture<Void> attr = supplyAsync.thenAcceptAsync((res) -> {
            //3 销售属性组合
            List<SkuItemAttrVo> skuItemAttrVos = skuSaleAttrValueService.getSaleAttrBySpuId(res.getSpuId());
            skuItemVo.setSkuItemAttrVos(skuItemAttrVos);
        }, poolExecutor);

        CompletableFuture<Void> spuInfo = supplyAsync.thenAcceptAsync((res) -> {
            //4 spu属性
            SpuInfoDescEntity spuInfoDesc = spuInfoDescService.getById(res.getSpuId());
            skuItemVo.setSpuInfoDesc(spuInfoDesc);
        }, poolExecutor);

        //5 属性组-》属性值
        CompletableFuture<Void> group = supplyAsync.thenAcceptAsync((res) -> {
            List<SpuItemAttrGroupVo> groupAttrs = attrGroupService.AttrGroupWithAttrBySpuId(res.getSpuId());
            skuItemVo.setGroupAttrs(groupAttrs);
        }, poolExecutor);

        CompletableFuture<Void> imge = CompletableFuture.runAsync(() -> {
            //2 sku图片信息
            List<SkuImagesEntity> skuImagesEntities = skuImagesService.list(new QueryWrapper<SkuImagesEntity>().eq("sku_id", skyId));
            skuItemVo.setSkuImages(skuImagesEntities);
        }, poolExecutor);
        CompletableFuture.allOf(imge, group, spuInfo, attr).get();


        return skuItemVo;
    }
}