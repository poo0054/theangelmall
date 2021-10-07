package com.theangel.themall.product.openfeign;

import com.theangel.common.utils.R;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

@FeignClient("themall-ware")
public interface WareFeignService {
    /**
     * 查询sku是否有库存
     */
    @PostMapping("/ware/waresku/hasstock")
    R getHasStock(@RequestBody List<Long> skuIds);
}
