package com.theangel.themall.product.service.impl;

import com.baomidou.mybatisplus.annotation.TableField;
import com.theangel.themall.product.vo.SpuSaveVo;
import org.springframework.stereotype.Service;

import java.util.Map;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.theangel.common.utils.PageUtils;
import com.theangel.common.utils.Query;

import com.theangel.themall.product.dao.SpuInfoDao;
import com.theangel.themall.product.entity.SpuInfoEntity;
import com.theangel.themall.product.service.SpuInfoService;
import org.springframework.transaction.annotation.Transactional;


@Service("spuInfoService")
public class SpuInfoServiceImpl extends ServiceImpl<SpuInfoDao, SpuInfoEntity> implements SpuInfoService {

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        IPage<SpuInfoEntity> page = this.page(
                new Query<SpuInfoEntity>().getPage(params),
                new QueryWrapper<SpuInfoEntity>()
        );

        return new PageUtils(page);
    }

    @Transactional
    @Override
    public void saveSpuInfo(SpuSaveVo spuInfo) {

        //1 保存spu基本信息  pms_spu_info


        //2 保存spu描述图片  pms_spu_info_desc

        //3 保存spu图片集 pms_spu_images


        //4 保存spu的属性值  pms_product_attr_value

        //5  保存spu的积分信息 themall_sms -> sms_spu_bounds

        //5 保存当前spu对应的sku信息

        // 5.1  sku基本信息  pms_sku_info

        //5.2  sku 图片   pms_sku_images

        // 5.3  sku销售属性  pms_sku_sale_attr_value

        // 5.4 sku的优惠满减信息  themall_sms ->  sms_sku_ladder 打折表 \  sms_sku_full_reduction 满减表  \  sms_member_price 会员折扣表


    }
}