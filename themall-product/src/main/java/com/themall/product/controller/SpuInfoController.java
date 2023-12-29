package com.themall.product.controller;

import com.themall.common.utils.PageUtils;
import com.themall.model.entity.R;
import com.themall.product.pojo.entity.SpuInfoEntity;
import com.themall.product.pojo.vo.SpuSaveVo;
import com.themall.product.service.SpuInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.Map;


/**
 * spu信息
 *
 * @author theangel
 * @email poo0054@outlook.com
 * @date 2021-06-09 20:47:30
 */
@RestController
@RequestMapping("product/spuinfo")
public class SpuInfoController {
    @Autowired
    private SpuInfoService spuInfoService;

    /**
     * 根据skuid获取spuid
     *
     * @param skuId
     * @return
     */
    @PostMapping("/skuid/{id}")
    //@RequiresPermissions("product:spuinfo:list")
    public R getSpuInfo(@PathVariable("id") Long skuId) {
//        PageUtils page = spuInfoService.queryPage(params);
        SpuInfoEntity spuInfoEntity = spuInfoService.getSpuInfo(skuId);
        return R.status().setData(spuInfoEntity);
    }

    /**
     * 商品上架
     * /product/spuinfo/{spuId}/up
     */
    @PostMapping("/{spuId}/up")
    //@RequiresPermissions("product:spuinfo:list")
    public R spuIdUp(@PathVariable("spuId") Long spuId) {
//        PageUtils page = spuInfoService.queryPage(params);
        spuInfoService.up(spuId);

        return R.status();
    }

    /**
     * 列表
     */
    @RequestMapping("/list")
    //@RequiresPermissions("product:spuinfo:list")
    public R list(@RequestParam Map<String, Object> params) {
//        PageUtils page = spuInfoService.queryPage(params);
        PageUtils page = spuInfoService.queryPageByCondition(params);

        return R.status().put("page", page);
    }


    /**
     * 信息
     */
    @RequestMapping("/info/{id}")
    //@RequiresPermissions("product:spuinfo:info")
    public R info(@PathVariable("id") Long id) {
        SpuInfoEntity spuInfo = spuInfoService.getById(id);

        return R.status().put("spuInfo", spuInfo);
    }

    /**
     * /product/spuinfo/save
     * 新增商品
     *
     * @param spuInfo
     * @return
     */
    @PostMapping("/save")
    //@RequiresPermissions("product:spuinfo:save")
    public R save(@RequestBody SpuSaveVo spuInfo) {
//        spuInfoService.save(spuInfo);
        spuInfoService.saveSpuInfo(spuInfo);
        return R.status();
    }

    /**
     * 修改
     */
    @RequestMapping("/update")
    //@RequiresPermissions("product:spuinfo:update")
    public R update(@RequestBody SpuInfoEntity spuInfo) {
        spuInfoService.updateById(spuInfo);

        return R.status();
    }

    /**
     * 删除
     */
    @RequestMapping("/delete")
    //@RequiresPermissions("product:spuinfo:delete")
    public R delete(@RequestBody Long[] ids) {
        spuInfoService.removeByIds(Arrays.asList(ids));

        return R.status();
    }

}
