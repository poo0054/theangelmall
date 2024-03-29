package com.themall.product.controller;

import com.themall.common.utils.PageUtils;
import com.themall.model.entity.R;
import com.themall.product.pojo.entity.ProductAttrValueEntity;
import com.themall.product.pojo.vo.AttrResVo;
import com.themall.product.pojo.vo.AttrVo;
import com.themall.product.service.AttrService;
import com.themall.product.service.ProductAttrValueService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.List;
import java.util.Map;


/**
 * 商品属性
 *
 * @author theangel
 * @email poo0054@outlook.com
 * @date 2021-06-09 20:47:30
 */
@RestController
@RequestMapping("product/attr")
public class AttrController {
    private AttrService attrService;

    private ProductAttrValueService productAttrValueService;

    @Autowired
    public void setAttrService(AttrService attrService) {
        this.attrService = attrService;
    }

    @Autowired
    public void setProductAttrValueService(ProductAttrValueService productAttrValueService) {
        this.productAttrValueService = productAttrValueService;
    }

    /**
     * /product/attr/base/listforspu/{spuId}
     *
     * @param spuId
     * @return
     */
    @GetMapping("/base/listforspu/{spuId}")
    public R baseListForSpu(@PathVariable("spuId") Long spuId) {
        List<ProductAttrValueEntity> pageUtils = productAttrValueService.baseListForSpu(spuId);
        return R.status().put("data", pageUtils);
    }

    /**
     * @param params
     * @param catelogId
     * @param type
     * @return
     */
    @GetMapping("/{type}/list/{catelogId}")
    public R baseList(@RequestParam Map<String, Object> params, @PathVariable("catelogId") Long catelogId, @PathVariable("type") String type) {
        PageUtils pageUtils = attrService.queryBaseAttrPage(params, catelogId, type);
        return R.status().put("page", pageUtils);
    }

    /**
     * 列表
     */
    @RequestMapping("/list")
    //@RequiresPermissions("product:attr:list")
    public R list(@RequestParam Map<String, Object> params) {
        PageUtils page = attrService.queryPage(params);
        return R.status().put("page", page);
    }


    /**
     * 信息
     * /product/attrgroup/info/{attrGroupId}
     * 根据属性Id获取详情
     *
     * @param attrId
     * @return
     */
    @GetMapping("/info/{attrId}")
    //@RequiresPermissions("product:attr:info")
    public R info(@PathVariable("attrId") Long attrId) {
//        AttrEntity attr = attrService.getById(attrId);
        AttrResVo attr = attrService.getAttrInfo(attrId);
        return R.status().put("attr", attr);
    }

    /**
     * 保存
     */
    @RequestMapping("/save")
    //@RequiresPermissions("product:attr:save")
    public R save(@RequestBody AttrVo attr) {
//        attrService.save(attr);
        attrService.saveAttr(attr);
        return R.status();
    }

    /**
     * 修改
     */
    @RequestMapping("/update")
    //@RequiresPermissions("product:attr:update")
    public R update(@RequestBody AttrVo attr) {
//        attrService.updateById(attr);
        attrService.updateAttr(attr);

        return R.status();
    }

    /**
     * /product/attr/update/{spuId}
     * 修改
     */
    @RequestMapping("/update/{spuId}")
    //@RequiresPermissions("product:attr:update")
    public R updateAttr(@PathVariable("spuId") Long spuId, @RequestBody List<ProductAttrValueEntity> attr) {
//        attrService.updateById(attr);
        productAttrValueService.updateAttr(spuId, attr);
        return R.status();
    }

    /**
     * 删除
     */
    @RequestMapping("/delete")
    //@RequiresPermissions("product:attr:delete")
    public R delete(@RequestBody Long[] attrIds) {
        attrService.removeByIds(Arrays.asList(attrIds));

        return R.status();
    }

}
