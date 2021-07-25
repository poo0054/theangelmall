package com.theangel.themall.product.controller;

import java.util.Arrays;
import java.util.Map;

//import org.apache.shiro.authz.annotation.RequiresPermissions;
import com.theangel.themall.product.vo.AttrResVo;
import com.theangel.themall.product.vo.AttrVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.theangel.themall.product.entity.AttrEntity;
import com.theangel.themall.product.service.AttrService;
import com.theangel.common.utils.PageUtils;
import com.theangel.common.utils.R;


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
    @Autowired
    private AttrService attrService;

    // product/attr/base/list/0?t=1626618562074&page=1&limit=10&key=
    //GET /product/attr/base/list/{catelogId}
    //GET /product/attr/sale/list/{catelogId}

    /**
     * @param params
     * @param catelogId
     * @param type
     * @return
     */
    @GetMapping("/{type}/list/{catelogId}")
    public R baseList(@RequestParam Map<String, Object> params, @PathVariable("catelogId") Long catelogId, @PathVariable("type") String type) {
        PageUtils pageUtils = attrService.queryBaseAttrPage(params, catelogId, type);
        return R.ok().put("page", pageUtils);
    }

    /**
     * 列表
     */
    @RequestMapping("/list")
    //@RequiresPermissions("product:attr:list")
    public R list(@RequestParam Map<String, Object> params) {
        PageUtils page = attrService.queryPage(params);

        return R.ok().put("page", page);
    }


    /**
     * 信息
     * /product/attrgroup/info/{attrGroupId}
     * 获取属性分组详情
     *
     * @param attrId
     * @return
     */
    @RequestMapping("/info/{attrId}")
    //@RequiresPermissions("product:attr:info")
    public R info(@PathVariable("attrId") Long attrId) {
//        AttrEntity attr = attrService.getById(attrId);
        AttrResVo attr = attrService.getAttrInfo(attrId);
        return R.ok().put("attr", attr);
    }

    /**
     * 保存
     */
    @RequestMapping("/save")
    //@RequiresPermissions("product:attr:save")
    public R save(@RequestBody AttrVo attr) {
//        attrService.save(attr);
        attrService.saveAttr(attr);
        return R.ok();
    }

    /**
     * 修改
     */
    @RequestMapping("/update")
    //@RequiresPermissions("product:attr:update")
    public R update(@RequestBody AttrVo attr) {
//        attrService.updateById(attr);
        attrService.updateAttr(attr);

        return R.ok();
    }

    /**
     * 删除
     */
    @RequestMapping("/delete")
    //@RequiresPermissions("product:attr:delete")
    public R delete(@RequestBody Long[] attrIds) {
        attrService.removeByIds(Arrays.asList(attrIds));

        return R.ok();
    }

}
