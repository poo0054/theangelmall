package com.themall.product.controller;

import com.themall.common.utils.PageUtils;
import com.themall.common.utils.R;
import com.themall.common.valid.addGro;
import com.themall.common.valid.updateGro;
import com.themall.product.entity.BrandEntity;
import com.themall.product.service.BrandService;
import org.springframework.beans.factory.annotation.Autowired;
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
    @Autowired
    private BrandService brandService;

    /**
     * 根据品牌id查询所有品牌信息
     *
     * @param brandIds
     * @return
     */
    @GetMapping("/infos")
    public R infos(@RequestParam("brandIds") List<Long> brandIds) {
        List<BrandEntity> brandEntities = brandService.getBrandsById(brandIds);
        return R.httpStatus().setData(brandEntities);
    }

    /**
     * 列表
     */
    @RequestMapping("/list")
    //@RequiresPermissions("product:brand:list")
    public R list(@RequestParam Map<String, Object> params) {
        PageUtils page = brandService.queryPage(params);
        return R.httpStatus().put("page", page);
    }


    /**
     * 信息
     */
    @RequestMapping("/info/{brandId}")
    //@RequiresPermissions("product:brand:info")
    public R info(@PathVariable("brandId") Long brandId) {
        BrandEntity brand = brandService.getById(brandId);

        return R.httpStatus().put("brand", brand);
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
        return R.httpStatus();

    }

    /**
     * 修改
     */
    @RequestMapping("/update")
    //@RequiresPermissions("product:brand:update")
    public R update(@Validated({updateGro.class}) @RequestBody BrandEntity brand) {
//        brandService.updateById(brand);
        brandService.updateDetail(brand);
        return R.httpStatus();
    }

    /**
     * 删除
     */
    @RequestMapping("/delete")
    //@RequiresPermissions("product:brand:delete")
    public R delete(@RequestBody Long[] brandIds) {
        brandService.removeByIds(Arrays.asList(brandIds));
        return R.httpStatus();
    }

}
