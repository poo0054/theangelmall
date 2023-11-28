package com.themall.ware.openfeign;

import com.themall.common.utils.R;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@FeignClient("themall-product")
public interface ProductFeignService {

    /**
     * openfeign 有俩种方法
     * 给网关发请求  /api/product/skuinfo/info/{skuId}
     * 给服务发请求  /product/skuinfo/info/{skuId}
     *
     * @param skuId
     * @return
     */
    @RequestMapping("/product/skuinfo/info/{skuId}")
    R info(@PathVariable("skuId") Long skuId);
}
