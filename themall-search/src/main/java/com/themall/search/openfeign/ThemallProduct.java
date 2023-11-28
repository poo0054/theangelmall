package com.themall.search.openfeign;

import com.themall.common.utils.R;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@FeignClient("themall-product")
public interface ThemallProduct {

    /**
     * 信息
     * /product/attrgroup/info/{attrGroupId}
     * 根据属性Id获取详情
     *
     * @param attrId
     * @return
     */
    @GetMapping("/product/attr/info/{attrId}")
    public R attrInfo(@PathVariable("attrId") Long attrId);

    /**
     * 根据品牌id查询所有品牌信息
     *
     * @param brandIds
     * @return
     */
    @GetMapping("/product/brand/infos")
    public R brandsByIds(@RequestParam("brandIds") List<Long> brandIds);
}
