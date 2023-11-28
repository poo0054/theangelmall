package com.themall.coupon.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.themall.common.to.MemberPrice;
import com.themall.common.to.SkuReductionTo;
import com.themall.common.utils.PageUtils;
import com.themall.common.utils.Query;
import com.themall.coupon.dao.SkuFullReductionDao;
import com.themall.coupon.entity.MemberPriceEntity;
import com.themall.coupon.entity.SkuFullReductionEntity;
import com.themall.coupon.entity.SkuLadderEntity;
import com.themall.coupon.service.MemberPriceService;
import com.themall.coupon.service.SkuFullReductionService;
import com.themall.coupon.service.SkuLadderService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;


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
        if (skuReductionTo.getFullCount() > 0) {
            this.skuLadderService.save(skuLadderEntity);
        }


        //保存满减
        SkuFullReductionEntity skuFullReductionEntity = new SkuFullReductionEntity();
        BeanUtils.copyProperties(skuReductionTo, skuFullReductionEntity);
        if (skuFullReductionEntity.getFullPrice().compareTo(new BigDecimal(0)) == 1) {
            this.save(skuFullReductionEntity);
        }

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
        })
                .filter(memberPriceEntity -> memberPriceEntity.getMemberPrice().compareTo(new BigDecimal(0)) == 1)
                .collect(Collectors.toList());
        memberPriceService.saveBatch(collect);
    }
}