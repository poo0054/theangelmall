package com.themall.auth.openfeign;

import com.themall.common.to.SkuReductionTo;
import com.themall.common.to.SpuBoundTo;
import com.themall.common.utils.R;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient("themall-coupon")
public interface ThemallCouponFeign {

    /**
     * 将这个对象转换为JSON
     * 找个这个远程服务的路径发送请求
     * 对方服务收到请求，请求体有json数据
     * 只要json属性名称能够对应  就可以接收
     *
     * @param spuBoundTo
     * @return
     */
    @PostMapping("/coupon/spubounds/save")
    public R saveSpuBounds(@RequestBody SpuBoundTo spuBoundTo);

    @PostMapping("/coupon/skufullreduction/saveinfo")
    public R saveSkuReduction(@RequestBody SkuReductionTo skuReductionTo);
}
