package com.themall.product.controller;

import com.themall.common.utils.PageUtils;
import com.themall.model.entity.R;
import com.themall.product.pojo.entity.ProductAttrValueEntity;
import com.themall.product.service.ProductAttrValueService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.Map;


/**
 * spu属性值
 *
 * @author theangel
 * @email poo0054@outlook.com
 * @date 2021-06-09 20:47:30
 */
@RestController
@RequestMapping("product/productattrvalue")
public class ProductAttrValueController {
    @Autowired
    private ProductAttrValueService productAttrValueService;

    /**
     * 列表
     */
    @RequestMapping("/list")
    //@RequiresPermissions("product:productattrvalue:list")
    public R list(@RequestParam Map<String, Object> params) {
        PageUtils page = productAttrValueService.queryPage(params);

        return R.status().put("page", page);
    }


    /**
     * 信息
     */
    @RequestMapping("/info/{id}")
    //@RequiresPermissions("product:productattrvalue:info")
    public R info(@PathVariable("id") Long id) {
            ProductAttrValueEntity productAttrValue = productAttrValueService.getById(id);

        return R.status().put("productAttrValue", productAttrValue);
    }

    /**
     * 保存
     */
    @RequestMapping("/save")
    //@RequiresPermissions("product:productattrvalue:save")
    public R save(@RequestBody ProductAttrValueEntity productAttrValue) {
            productAttrValueService.save(productAttrValue);

        return R.status();
    }

    /**
     * 修改
     */
    @RequestMapping("/update")
    //@RequiresPermissions("product:productattrvalue:update")
    public R update(@RequestBody ProductAttrValueEntity productAttrValue) {
            productAttrValueService.updateById(productAttrValue);

        return R.status();
    }

    /**
     * 删除
     */
    @RequestMapping("/delete")
    //@RequiresPermissions("product:productattrvalue:delete")
    public R delete(@RequestBody Long[] ids) {
            productAttrValueService.removeByIds(Arrays.asList(ids));

        return R.status();
    }

}
