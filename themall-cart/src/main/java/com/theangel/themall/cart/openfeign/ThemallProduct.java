package com.theangel.themall.cart.openfeign;

import com.theangel.common.utils.R;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @ProjectName: theangelmall
 * @Package: com.theangel.themall.cart.openfeign
 * @ClassName: a
 * @Author: theangel
 * @Date: 2021/8/30 22:15
 */
@FeignClient("themall-product")
public interface ThemallProduct {

    /**
     * 根据skuid获取商品详细信息
     */
    @RequestMapping("product/skuinfo/info/{skuId}")
    public R getSkuInfo(@PathVariable("skuId") Long skuId);

    /**
     * 根据skuid查询属性名：属性值
     *
     * @param skuId
     * @return
     */
    @GetMapping("product/skusaleattrvalue/attrstrbyskuid/{skuid}")
    public R getAttrStrBySkuId(@PathVariable("skuid") Long skuId);
}
