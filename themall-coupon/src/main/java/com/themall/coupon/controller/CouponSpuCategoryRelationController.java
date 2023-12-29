package com.themall.coupon.controller;

import com.themall.common.utils.PageUtils;
import com.themall.coupon.entity.CouponSpuCategoryRelationEntity;
import com.themall.coupon.service.CouponSpuCategoryRelationService;
import com.themall.model.entity.R;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.Map;


/**
 * 优惠券分类关联
 *
 * @author theangel
 * @email poo0054@outlook.com
 * @date 2021-06-09 20:52:38
 */
@RestController
@RequestMapping("coupon/couponspucategoryrelation")
public class CouponSpuCategoryRelationController {
    @Autowired
    private CouponSpuCategoryRelationService couponSpuCategoryRelationService;

    /**
     * 列表
     */
    @RequestMapping("/list")
    //@RequiresPermissions("coupon:couponspucategoryrelation:list")
    public R list(@RequestParam Map<String, Object> params) {
        PageUtils page = couponSpuCategoryRelationService.queryPage(params);

        return R.status().put("page", page);
    }


    /**
     * 信息
     */
    @RequestMapping("/info/{id}")
    //@RequiresPermissions("coupon:couponspucategoryrelation:info")
    public R info(@PathVariable("id") Long id) {
            CouponSpuCategoryRelationEntity couponSpuCategoryRelation = couponSpuCategoryRelationService.getById(id);

        return R.status().put("couponSpuCategoryRelation", couponSpuCategoryRelation);
    }

    /**
     * 保存
     */
    @RequestMapping("/save")
    //@RequiresPermissions("coupon:couponspucategoryrelation:save")
    public R save(@RequestBody CouponSpuCategoryRelationEntity couponSpuCategoryRelation) {
            couponSpuCategoryRelationService.save(couponSpuCategoryRelation);

        return R.status();
    }

    /**
     * 修改
     */
    @RequestMapping("/update")
    //@RequiresPermissions("coupon:couponspucategoryrelation:update")
    public R update(@RequestBody CouponSpuCategoryRelationEntity couponSpuCategoryRelation) {
            couponSpuCategoryRelationService.updateById(couponSpuCategoryRelation);

        return R.status();
    }

    /**
     * 删除
     */
    @RequestMapping("/delete")
    //@RequiresPermissions("coupon:couponspucategoryrelation:delete")
    public R delete(@RequestBody Long[] ids) {
            couponSpuCategoryRelationService.removeByIds(Arrays.asList(ids));

        return R.status();
    }

}
