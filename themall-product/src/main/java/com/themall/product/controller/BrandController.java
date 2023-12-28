package com.themall.product.controller;

import com.themall.common.utils.PageUtils;
import com.themall.model.entity.R;
import com.themall.model.validator.group.AddGroup;
import com.themall.product.pojo.entity.BrandEntity;
import com.themall.product.service.BrandService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

/**
 * 品牌
 *
 * @author theangel
 * @email poo0054@outlook.com
 * @date 2021-06-09 20:47:30
 */
@RestController
@RequestMapping("product/brand")
public class BrandController {

    private BrandService brandService;

    @Autowired
    public void setBrandService(BrandService brandService) {
        this.brandService = brandService;
    }

    /**
     * 根据品牌id查询所有品牌信息
     *
     * @param brandIds
     * @return
     */
    @GetMapping("/infos")
    @PreAuthorize("hasAuthority('product:brand:list')")
    public R infos(@RequestParam("brandIds") List<Long> brandIds) {
        List<BrandEntity> brandEntities = brandService.getBrandsById(brandIds);
        return R.httpStatus().setData(brandEntities);
    }

    /**
     * 列表
     */
    @RequestMapping("/list")
    @PreAuthorize("hasAuthority('product:brand:list')")
    public R list(@RequestParam Map<String, Object> params) {
        PageUtils page = brandService.queryPage(params);
        return R.httpStatus().setData(page);
    }


    /**
     * 信息
     */
    @RequestMapping("/info/{brandId}")
    @PreAuthorize("hasAuthority('product:brand:list')")
    public R info(@PathVariable("brandId") Long brandId) {
        BrandEntity brand = brandService.getById(brandId);
        return R.httpStatus().setData(brand);
    }

    /**
     * 保存
     */
    @RequestMapping(value = "/save", method = RequestMethod.POST)
    @PreAuthorize("hasAuthority('product:brand:save')")
    public R save(@Validated({AddGroup.class}) @RequestBody BrandEntity brand) {
        return R.httpStatus(brandService.save(brand));
    }

    /**
     * 修改
     */
    @RequestMapping("/update")
    @PreAuthorize("hasAuthority('product:brand:update')")
    public R update(@Validated @RequestBody BrandEntity brand) {
        brandService.updateDetail(brand);
        return R.httpStatus();
    }

    /**
     * 删除
     */
    @RequestMapping("/delete")
    @PreAuthorize("hasAuthority('product:brand:delete')")
    public R delete(@RequestBody Long[] brandIds) {
        brandService.removeByIds(Arrays.asList(brandIds));
        return R.httpStatus();
    }

}
