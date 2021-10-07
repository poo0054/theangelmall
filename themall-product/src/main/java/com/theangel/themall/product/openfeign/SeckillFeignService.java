package com.theangel.themall.product.openfeign;

import com.theangel.common.utils.R;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

/**
 * @ProjectName: theangelmall
 * @Package: com.theangel.themall.product.openfeign
 * @ClassName: SeckillFeignService
 * @Author: theangel
 * @Date: 2021/10/7 13:32
 */
@FeignClient("themall-seckill")
public interface SeckillFeignService {
    /**
     * 根据skuid查询优惠信息
     *
     * @return
     */
    @GetMapping("sku/seckill/{skuId}")
    R getSkuSeckillInfo(@PathVariable("skuId") Long skuId);
}
