package com.theangel.themall.ware.controller;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.Map;

//import org.apache.shiro.authz.annotation.RequiresPermissions;
import com.theangel.themall.ware.vo.FareVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.theangel.themall.ware.entity.WareInfoEntity;
import com.theangel.themall.ware.service.WareInfoService;
import com.theangel.common.utils.PageUtils;
import com.theangel.common.utils.R;


/**
 * 仓库信息
 *
 * @author theangel
 * @email poo0054@outlook.com
 * @date 2021-06-10 20:02:13
 */
@RestController
@RequestMapping("ware/wareinfo")
public class WareInfoController {
    @Autowired
    private WareInfoService wareInfoService;

    /**
     * 根据用户地址信息，获取运费金额，收货人信息
     */
    @GetMapping("/fare/{addrId}")
    public R getFare(@PathVariable("addrId") Long addrId) {
        FareVo Farevo = wareInfoService.getFare(addrId);
        return R.ok().put("data", Farevo);
    }

    /**
     * 列表
     */
    @RequestMapping("/list")
    //@RequiresPermissions("ware:wareinfo:list")
    public R list(@RequestParam Map<String, Object> params) {
        PageUtils page = wareInfoService.queryPage(params);

        return R.ok().put("page", page);
    }


    /**
     * 信息
     */
    @RequestMapping("/info/{id}")
    //@RequiresPermissions("ware:wareinfo:info")
    public R info(@PathVariable("id") Long id) {
        WareInfoEntity wareInfo = wareInfoService.getById(id);

        return R.ok().put("wareInfo", wareInfo);
    }

    /**
     * 保存
     */
    @RequestMapping("/save")
    //@RequiresPermissions("ware:wareinfo:save")
    public R save(@RequestBody WareInfoEntity wareInfo) {
        wareInfoService.save(wareInfo);

        return R.ok();
    }

    /**
     * 修改
     */
    @RequestMapping("/update")
    //@RequiresPermissions("ware:wareinfo:update")
    public R update(@RequestBody WareInfoEntity wareInfo) {
        wareInfoService.updateById(wareInfo);

        return R.ok();
    }

    /**
     * 删除
     */
    @RequestMapping("/delete")
    //@RequiresPermissions("ware:wareinfo:delete")
    public R delete(@RequestBody Long[] ids) {
        wareInfoService.removeByIds(Arrays.asList(ids));

        return R.ok();
    }

}
