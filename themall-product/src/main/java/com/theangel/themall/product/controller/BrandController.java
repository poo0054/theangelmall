package com.theangel.themall.product.controller;

import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

//import org.apache.shiro.authz.annotation.RequiresPermissions;
import com.netflix.client.http.HttpRequest;
import com.netflix.ribbon.proxy.annotation.Http;
import com.theangel.common.valid.addGro;
import com.theangel.common.valid.updateGro;
import org.apache.http.HttpStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import com.theangel.themall.product.entity.BrandEntity;
import com.theangel.themall.product.service.BrandService;
import com.theangel.common.utils.PageUtils;
import com.theangel.common.utils.R;

import javax.validation.Valid;


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
    @Autowired
    private BrandService brandService;

    /**
     * 列表
     */
    @RequestMapping("/list")
    //@RequiresPermissions("product:brand:list")
    public R list(@RequestParam Map<String, Object> params) {
        PageUtils page = brandService.queryPage(params);

        return R.ok().put("page", page);
    }


    /**
     * 信息
     */
    @RequestMapping("/info/{brandId}")
    //@RequiresPermissions("product:brand:info")
    public R info(@PathVariable("brandId") Long brandId) {
        BrandEntity brand = brandService.getById(brandId);

        return R.ok().put("brand", brand);
    }

    /**
     * 保存
     */
    @RequestMapping(value = "/save", method = RequestMethod.POST)
    //@RequiresPermissions("product:brand:save")
    public R save(@Validated({addGro.class}) @RequestBody BrandEntity brand/**, BindingResult result*/) {
     /*   if (result.hasErrors()) {
            LinkedHashMap<String, String> map = new LinkedHashMap<>();
            List<FieldError> fieldErrors = result.getFieldErrors();
            fieldErrors.forEach(k -> {
                map.put(k.getField(), k.getDefaultMessage());
            });
            return R.error(HttpStatus.SC_BAD_REQUEST, "提交数据不合法").put("data", map);
        } else {
            brandService.save(brand);
            return R.ok();
        }*/
        brandService.save(brand);
        return R.ok();

    }

    /**
     * 修改
     */
    @RequestMapping("/update")
    //@RequiresPermissions("product:brand:update")
    public R update(@Validated({updateGro.class}) @RequestBody BrandEntity brand) {
        brandService.updateById(brand);
        return R.ok();
    }

    /**
     * 删除
     */
    @RequestMapping("/delete")
    //@RequiresPermissions("product:brand:delete")
    public R delete(@RequestBody Long[] brandIds) {
        brandService.removeByIds(Arrays.asList(brandIds));
        return R.ok();
    }

}
