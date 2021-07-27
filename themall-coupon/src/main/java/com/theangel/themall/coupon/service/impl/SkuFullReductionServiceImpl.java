package com.theangel.themall.coupon.service.impl;

import com.theangel.common.to.MemberPrice;
import com.theangel.common.to.SkuReductionTo;
import com.theangel.themall.coupon.entity.MemberPriceEntity;
import com.theangel.themall.coupon.entity.SkuLadderEntity;
import com.theangel.themall.coupon.service.MemberPriceService;
import com.theangel.themall.coupon.service.SkuLadderService;
import org.springframework.beans.BeanUtils;
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

import com.theangel.themall.coupon.dao.SkuFullReductionDao;
import com.theangel.themall.coupon.entity.SkuFullReductionEntity;
import com.theangel.themall.coupon.service.SkuFullReductionService;


@Service("skuFullReductionService")
public class SkuFullReductionServiceImpl extends ServiceImpl<SkuFullReductionDao, SkuFullReductionEntity> implements SkuFullReductionService {

    @Autowired
    SkuLadderService skuLadderService;

    @Autowired
    MemberPriceService memberPriceService;

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        IPage<SkuFullReductionEntity> page = this.page(
                new Query<SkuFullReductionEntity>().getPage(params),
                new QueryWrapper<SkuFullReductionEntity>()
        );

        return new PageUtils(page);
    }

    @Override
    public void saveInfo(SkuReductionTo skuReductionTo) {
        //阶梯价格
        SkuLadderEntity skuLadderEntity = new SkuLadderEntity();
        Long skuId = skuReductionTo.getSkuId();
        skuLadderEntity.setSkuId(skuId);
        //满几件
        skuLadderEntity.setFullCount(skuReductionTo.getFullCount());
        //打几折
        skuLadderEntity.setDiscount(skuReductionTo.getDiscount());
        //是否叠加其它优惠
        skuLadderEntity.setAddOther(skuReductionTo.getCountStatus());//打折的状态是否参与其它
       /* //折后价格
        skuLadderEntity.setPrice();*/
        this.skuLadderService.save(skuLadderEntity);

        //保存满减打折
        SkuFullReductionEntity skuFullReductionEntity = new SkuFullReductionEntity();
        BeanUtils.copyProperties(skuReductionTo, skuFullReductionEntity);
        this.save(skuFullReductionEntity);
        //保存会员价格
        List<MemberPrice> memberPrice = skuReductionTo.getMemberPrice();
        List<MemberPriceEntity> collect = memberPrice.stream().map(item -> {
            MemberPriceEntity memberPriceEntity = new MemberPriceEntity();
            memberPriceEntity.setSkuId(skuId);
            memberPriceEntity.setMemberLevelId(item.getId());
            memberPriceEntity.setMemberLevelName(item.getName());
            memberPriceEntity.setMemberPrice(item.getPrice());
            memberPriceEntity.setAddOther(1);
            return memberPriceEntity;
        }).collect(Collectors.toList());
        memberPriceService.saveBatch(collect);
    }
}