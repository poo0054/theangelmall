package com.themall.product.service.impl;

import com.alibaba.fastjson.TypeReference;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.themall.common.constant.ProductConstant;
import com.themall.common.to.SkuHasStockVo;
import com.themall.common.to.SkuReductionTo;
import com.themall.common.to.SpuBoundTo;
import com.themall.common.to.es.AttrsEsModel;
import com.themall.common.to.es.SkuEsModel;
import com.themall.common.utils.PageUtils;
import com.themall.common.utils.Query;
import com.themall.common.utils.R;
import com.themall.product.dao.SpuInfoDao;
import com.themall.product.entity.*;
import com.themall.product.openfeign.CouponFeignService;
import com.themall.product.openfeign.SearchFeignService;
import com.themall.product.openfeign.WareFeignService;
import com.themall.product.service.*;
import com.themall.product.vo.*;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;

import java.math.BigDecimal;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;


@Service("spuInfoService")
public class SpuInfoServiceImpl extends ServiceImpl<SpuInfoDao, SpuInfoEntity> implements SpuInfoService {
    @Autowired
    SpuInfoDescService spuInfoDescService;
    @Autowired
    SpuImagesService spuImagesService;
    @Autowired
    AttrService attrService;
    @Autowired
    ProductAttrValueService productAttrValueService;
    @Autowired
    SkuInfoService skuInfoService;
    @Autowired
    SkuImagesService skuImagesService;
    @Autowired
    SkuSaleAttrValueService skuSaleAttrValueService;
    @Autowired
    CouponFeignService themallCouponFeign;
    @Autowired
    BrandService brandService;
    @Autowired
    CategoryService categoryService;
    @Autowired
    WareFeignService theamgelWareFeign;
    @Autowired
    SearchFeignService themallSearchFeign;

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        IPage<SpuInfoEntity> page = this.page(
                new Query<SpuInfoEntity>().getPage(params),
                new QueryWrapper<>()
        );

        return new PageUtils(page);
    }

    /**
     * TODO 剩余失败处理  使用seata的at模式
     *
     * @param spuSaveVo
     */
    @Transactional(rollbackFor = Exception.class)
    @Override
    public void saveSpuInfo(SpuSaveVo spuSaveVo) {
        //1 保存spu基本信息  pms_spu_info
        SpuInfoEntity spuInfoEntity = new SpuInfoEntity();
        BeanUtils.copyProperties(spuSaveVo, spuInfoEntity);
        spuInfoEntity.setCreateTime(new Date());
        this.saveBaseSpuInfo(spuInfoEntity);

        //2 保存spu描述图片  pms_spu_info_desc
        List<String> decript = spuSaveVo.getDecript();
        SpuInfoDescEntity spuInfoDescEntity = new SpuInfoDescEntity();
        spuInfoDescEntity.setSpuId(spuInfoEntity.getId());
        spuInfoDescEntity.setDecript(String.join(",", decript));
        spuInfoDescService.save(spuInfoDescEntity);

        //3 保存spu图片集 pms_spu_images
        List<String> images = spuSaveVo.getImages();
        spuImagesService.saveImages(spuInfoEntity.getId(), images);

        //4 保存spu的属性值  pms_product_attr_value
        List<BaseAttrs> baseAttrs = spuSaveVo.getBaseAttrs();
        List<ProductAttrValueEntity> collect = baseAttrs.stream().map(attr -> {
            ProductAttrValueEntity productAttrValueEntity = new ProductAttrValueEntity();
            productAttrValueEntity.setAttrId(attr.getAttrId());
            AttrEntity byId = attrService.getById(attr.getAttrId());
            productAttrValueEntity.setAttrName(byId.getAttrName());
            productAttrValueEntity.setAttrValue(attr.getAttrValues());
            productAttrValueEntity.setQuickShow(attr.getShowDesc());
            productAttrValueEntity.setSpuId(spuInfoEntity.getId());
            return productAttrValueEntity;
        }).collect(Collectors.toList());
        productAttrValueService.saveBatch(collect);

        // 保存spu的积分信息 themall_sms -> sms_spu_bounds
        Bounds bounds = spuSaveVo.getBounds();
        SpuBoundTo spuBoundTo = new SpuBoundTo();
        BeanUtils.copyProperties(bounds, spuBoundTo);
        spuBoundTo.setSpuId(spuInfoEntity.getId());
        R r = themallCouponFeign.saveSpuBounds(spuBoundTo);
        if (r.getCode() != 0) {
            log.error("远程保存spu积分信息失败");
        }

        //5 保存当前spu对应的sku信息
        List<Skus> skus = spuSaveVo.getSkus();
        if (!ObjectUtils.isEmpty(skus)) {
            skus.forEach(item -> {
                String defaultImg = "";
                for (Images image : item.getImages()) {
                    if (image.getDefaultImg() == 1) {
                        defaultImg = image.getImgUrl();
                    }
                }

                /**
                 *   private String skuName;
                 *     private BigDecimal price;
                 *     private String skuTitle;
                 *     private String skuSubtitle;
                 */
                SkuInfoEntity skuInfoEntity = new SkuInfoEntity();
                BeanUtils.copyProperties(item, skuInfoEntity);
                skuInfoEntity.setBrandId(spuInfoEntity.getBrandId());
                skuInfoEntity.setCatalogId(spuInfoEntity.getCatalogId());
                skuInfoEntity.setSaleCount(0L);
                skuInfoEntity.setSpuId(spuInfoEntity.getId());
                skuInfoEntity.setSkuDefaultImg(defaultImg);
                //5.1  sku基本信息  pms_sku_info
                skuInfoService.save(skuInfoEntity);

                Long skuId = skuInfoEntity.getSkuId();

                //5.2  sku 图片   pms_sku_images
                List<SkuImagesEntity> imagesEntities = item.getImages().stream().map(img -> {
                    SkuImagesEntity skuImagesEntity = new SkuImagesEntity();
                    skuImagesEntity.setImgUrl(img.getImgUrl());
                    skuImagesEntity.setDefaultImg(img.getDefaultImg());
                    skuImagesEntity.setSkuId(skuId);
                    return skuImagesEntity;
                })
                        .filter(entity -> !StringUtils.isEmpty(entity))
                        .collect(Collectors.toList());
                //TODO 没有图片路径不保存
                skuImagesService.saveBatch(imagesEntities);


                //5.3  sku销售属性  pms_sku_sale_attr_value
                List<Attr> attrs = item.getAttr();
                List<SkuSaleAttrValueEntity> skuSaleAttrValueEntities = attrs.stream().map(attr -> {
                    SkuSaleAttrValueEntity skuSaleAttrValueEntity = new SkuSaleAttrValueEntity();
                    BeanUtils.copyProperties(attr, skuSaleAttrValueEntity);
                    skuSaleAttrValueEntity.setSkuId(skuId);
                    return skuSaleAttrValueEntity;
                }).collect(Collectors.toList());
                skuSaleAttrValueService.saveBatch(skuSaleAttrValueEntities);


                //5.4 sku的优惠满减信息  themall_sms ->  sms_sku_ladder 打折表 \  sms_sku_full_reduction 满减表  \  sms_member_price 会员折扣表
                SkuReductionTo skuReductionTo = new SkuReductionTo();
                BeanUtils.copyProperties(item, skuReductionTo);
                skuReductionTo.setSkuId(skuId);
                if (skuReductionTo.getFullCount() > 0 || skuReductionTo.getFullPrice().compareTo(new BigDecimal(0)) == 1) {
                    R r1 = themallCouponFeign.saveSkuReduction(skuReductionTo);
                    if (r1.getCode() != 0) {
                        log.error("远程保存sku优惠信息失败");
                    }
                }
            });
        }


    }

    @Override
    public void saveBaseSpuInfo(SpuInfoEntity spuInfoEntity) {
        this.save(spuInfoEntity);
    }

    @Override
    public PageUtils queryPageByCondition(Map<String, Object> params) {
        QueryWrapper<SpuInfoEntity> queryWrapper = new QueryWrapper<>();
        String key = (String) params.get("key");
        if (!ObjectUtils.isEmpty(key)) {
            queryWrapper.and((wrapper) -> {
                wrapper.like("id", key).or().like("spu_name", key);
            });
        }
        String status = (String) params.get("status");
        if (!ObjectUtils.isEmpty(status)) {
            queryWrapper.eq("publish_status", status);
        }
        String brandId = (String) params.get("brandId");
        if (!ObjectUtils.isEmpty(brandId) && !brandId.equals("0")) {
            queryWrapper.eq("brand_id", brandId);
        }
        String catelogId = (String) params.get("catelogId");
        if (!ObjectUtils.isEmpty(catelogId) && !catelogId.equals("0")) {
            queryWrapper.eq("catalog_id", catelogId);
        }

        IPage<SpuInfoEntity> page = this.page(
                new Query<SpuInfoEntity>().getPage(params),
                queryWrapper
        );

        return new PageUtils(page);
    }


    /**
     * 商品上架
     *
     * @param spuId
     */
    @Override
    public void up(Long spuId) {
        // 1. 根据spu  查出sku对应的所有信息  品牌名称
        List<SkuInfoEntity> skus = skuInfoService.getSkuInfoBySpuId(spuId);

        /**
         * TODO 查出sku的所有可以被检索规格属性，
         *   private Long attrId;
         *     private String attrName;
         *     private String attrValue;
         */
        List<ProductAttrValueEntity> productAttrValueEntities = productAttrValueService.baseListForSpu(spuId);
        //获取所有属性id
        List<Long> attrId = productAttrValueEntities.stream().map(item -> {
            return item.getAttrId();
        }).collect(Collectors.toList());
        List<Long> attrIds = attrService.listSearchAttrIds(attrId);
        HashSet<Long> longs = new HashSet<>(attrIds);
        //所有能被检索的属性
        List<AttrsEsModel> attrsEsModelList = productAttrValueEntities.stream().filter(item -> {
            return longs.contains(item.getAttrId());
        }).map(item -> {
            AttrsEsModel attrsEsModel = new AttrsEsModel();
            BeanUtils.copyProperties(item, attrsEsModel);
            return attrsEsModel;
        }).collect(Collectors.toList());


        /**
         * 查询所有sku库存状态
         */
        List<Long> collect1 = skus.stream().map(item -> item.getSkuId()).collect(Collectors.toList());

        Map<Long, Boolean> stockMap = null;
        try {
            R hasStock = theamgelWareFeign.getHasStock(collect1);
            stockMap = hasStock.getData(new TypeReference<List<SkuHasStockVo>>() {
            })
                    .stream()
                    .collect(Collectors.toMap(SkuHasStockVo::getSkuId, SkuHasStockVo::getHasStock));
        } catch (Exception e) {
            e.printStackTrace();
            log.error("库存服务查询异常，原因{}", e);
        }
        //2. 封装sku信息
        Map<Long, Boolean> finalStockMap = stockMap;
        List<SkuEsModel> collect = skus.stream().map(item -> {
            SkuEsModel skuEsModel = new SkuEsModel();
            BeanUtils.copyProperties(item, skuEsModel);
            // skuImg skuPrice
            skuEsModel.setSkuImg(item.getSkuDefaultImg());
            skuEsModel.setSkuPrice(item.getPrice());
            //hasStock  hotScore
            //TODO 发送远程 查询是否有库存 hasStock
            if (ObjectUtils.isEmpty(finalStockMap)) {
                skuEsModel.setHasStock(true);
            } else {
                skuEsModel.setHasStock(finalStockMap.get(item.getSkuId()));
            }

            //TODO 热度评分 hotScore
            skuEsModel.setHotScore(0L);
            /**
             *  private Long catalogId;
             *     private String brandName;
             *     private String brandImg;
             *     private String catalogName;
             */
            BrandEntity byId = brandService.getById(skuEsModel.getBrandId());
            skuEsModel.setBrandImg(byId.getLogo());
            skuEsModel.setBrandName(byId.getName());

            CategoryEntity byId1 = categoryService.getById(skuEsModel.getCatalogId());
            skuEsModel.setCatalogName(byId1.getName());

            //设置检索属性
            skuEsModel.setAttrs(attrsEsModelList);

            return skuEsModel;
        }).collect(Collectors.toList());


        // 发送给es  themall-search
        R r = themallSearchFeign.productStatusUp(collect);
        if (r.getCode() == 0) {
            //TODO 改变spu状态
            this.baseMapper.updateSpuStatus(spuId, ProductConstant.SPUStatusEnum.UP_SPU.getCode());
        } else {
            //TODO 重复调用？接口幂等性  重试机制

        }

    }

    /**
     * 根据skuid获取spuid
     *
     * @param skuId
     * @return
     */
    @Override
    public SpuInfoEntity getSpuInfo(Long skuId) {
        SkuInfoEntity byId = skuInfoService.getById(skuId);
        Long spuId = byId.getSpuId();
        SpuInfoEntity byId1 = this.getById(spuId);
        return byId1;
    }
}