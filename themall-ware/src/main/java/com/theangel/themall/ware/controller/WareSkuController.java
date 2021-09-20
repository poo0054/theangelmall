package com.theangel.themall.ware.controller;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

//import org.apache.shiro.authz.annotation.RequiresPermissions;
import com.theangel.common.exception.BizCodeEnum;
import com.theangel.common.exception.NoStockException;
import com.theangel.common.to.SkuHasStockVo;
import com.theangel.themall.ware.to.WareSkuLockTo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.theangel.themall.ware.entity.WareSkuEntity;
import com.theangel.themall.ware.service.WareSkuService;
import com.theangel.common.utils.PageUtils;
import com.theangel.common.utils.R;


/**
 * 商品库存
 *
 * @author theangel
 * @email poo0054@outlook.com
 * @date 2021-06-10 20:02:13
 */
@RestController
@RequestMapping("ware/waresku")
public class WareSkuController {
    @Autowired
    private WareSkuService wareSkuService;

    /**
     * 锁库存
     */
    @PostMapping("/lock/order")
    public R OrderLock(@RequestBody WareSkuLockTo skuLockTo) {
        try {
            wareSkuService.OrderLock(skuLockTo);
            return R.ok();
        } catch (NoStockException e) {
            e.printStackTrace();
            return R.error(BizCodeEnum.NO_STOCK_EXCEPTION.getCode(), e.getMessage());
        }
    }

    /**
     * 查询sku是否有库存
     */
    @PostMapping("/hasstock")
    public R getHasStock(@RequestBody List<Long> skuIds) {
        List<SkuHasStockVo> skuHasStockVos = wareSkuService.getHasStock(skuIds);
        R ok = R.ok();
        return ok.setData(skuHasStockVos);
    }


    /**
     * 列表
     */
    @RequestMapping("/list")
    //@RequiresPermissions("ware:waresku:list")
    public R list(@RequestParam Map<String, Object> params) {
        PageUtils page = wareSkuService.queryPage(params);

        return R.ok().put("page", page);
    }


    /**
     * 信息
     */
    @RequestMapping("/info/{id}")
    //@RequiresPermissions("ware:waresku:info")
    public R info(@PathVariable("id") Long id) {
        WareSkuEntity wareSku = wareSkuService.getById(id);

        return R.ok().put("wareSku", wareSku);
    }

    /**
     * 保存
     */
    @RequestMapping("/save")
    //@RequiresPermissions("ware:waresku:save")
    public R save(@RequestBody WareSkuEntity wareSku) {
        wareSkuService.save(wareSku);

        return R.ok();
    }

    /**
     * 修改
     */
    @RequestMapping("/update")
    //@RequiresPermissions("ware:waresku:update")
    public R update(@RequestBody WareSkuEntity wareSku) {
        wareSkuService.updateById(wareSku);

        return R.ok();
    }

    /**
     * 删除
     */
    @RequestMapping("/delete")
    //@RequiresPermissions("ware:waresku:delete")
    public R delete(@RequestBody Long[] ids) {
        wareSkuService.removeByIds(Arrays.asList(ids));

        return R.ok();
    }

}
