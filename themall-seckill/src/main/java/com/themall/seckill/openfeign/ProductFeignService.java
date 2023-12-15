package com.themall.seckill.openfeign;

import com.themall.model.entity.R;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @ProjectName: theangelmall
 * @Package: com.theangel.themall.seckill.openfeign
 * @ClassName: ProductFeignService
 * @Author: theangel
 * @Date: 2021/10/5 21:13
 */
@FeignClient("themall-product")
public interface ProductFeignService {

    /**
     * 信息
     */
    @RequestMapping("/skuinfo/info/{skuId}")
    R info(@PathVariable("skuId") Long skuId);
}
