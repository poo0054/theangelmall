package com.theangel.themall.order.openfeign;

import com.theangel.common.utils.R;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

/**
 * @ProjectName: theangelmall
 * @Package: com.theangel.themall.order.openfeign
 * @ClassName: WareService
 * @Author: theangel
 * @Date: 2021/9/7 20:40
 */
@FeignClient("themall-ware")
public interface WareFeignService {

    /**
     * 查询sku是否有库存
     */
    @PostMapping("ware/waresku/hasstock")
    public R getHasStock(@RequestBody List<Long> skuIds);

    /**
     * 根据用户地址信息，获取运费金额，收货人信息
     */
    @GetMapping("ware/wareinfo/fare/{addrId}")
    public R getFare(@PathVariable("addrId") Long addrId);
}
