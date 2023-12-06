package com.themall.order.openfeign;

import com.themall.model.entity.R;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

/**
 * @ProjectName: theangelmall
 * @Package: com.theangel.themall.order.openfeign
 * @ClassName: ProductFeignService
 * @Author: theangel
 * @Date: 2021/9/12 22:52
 */
@FeignClient("themall-product")
public interface ProductFeignService {
    /**
     * 根据skuid获取spuid
     *
     * @param skuId
     * @return
     */
    @PostMapping("product/spuinfo/skuid/{id}")
    R getSpuInfo(@PathVariable("id") Long skuId);
}
