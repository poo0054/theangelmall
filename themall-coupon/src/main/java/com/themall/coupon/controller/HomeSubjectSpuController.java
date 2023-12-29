package com.themall.coupon.controller;

import com.themall.common.utils.PageUtils;
import com.themall.coupon.entity.HomeSubjectSpuEntity;
import com.themall.coupon.service.HomeSubjectSpuService;
import com.themall.model.entity.R;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.Map;


/**
 * 专题商品
 *
 * @author theangel
 * @email poo0054@outlook.com
 * @date 2021-06-09 20:52:38
 */
@RestController
@RequestMapping("coupon/homesubjectspu")
public class HomeSubjectSpuController {
    @Autowired
    private HomeSubjectSpuService homeSubjectSpuService;

    /**
     * 列表
     */
    @RequestMapping("/list")
    //@RequiresPermissions("coupon:homesubjectspu:list")
    public R list(@RequestParam Map<String, Object> params) {
        PageUtils page = homeSubjectSpuService.queryPage(params);

        return R.status().put("page", page);
    }


    /**
     * 信息
     */
    @RequestMapping("/info/{id}")
    //@RequiresPermissions("coupon:homesubjectspu:info")
    public R info(@PathVariable("id") Long id) {
            HomeSubjectSpuEntity homeSubjectSpu = homeSubjectSpuService.getById(id);

        return R.status().put("homeSubjectSpu", homeSubjectSpu);
    }

    /**
     * 保存
     */
    @RequestMapping("/save")
    //@RequiresPermissions("coupon:homesubjectspu:save")
    public R save(@RequestBody HomeSubjectSpuEntity homeSubjectSpu) {
            homeSubjectSpuService.save(homeSubjectSpu);

        return R.status();
    }

    /**
     * 修改
     */
    @RequestMapping("/update")
    //@RequiresPermissions("coupon:homesubjectspu:update")
    public R update(@RequestBody HomeSubjectSpuEntity homeSubjectSpu) {
            homeSubjectSpuService.updateById(homeSubjectSpu);

        return R.status();
    }

    /**
     * 删除
     */
    @RequestMapping("/delete")
    //@RequiresPermissions("coupon:homesubjectspu:delete")
    public R delete(@RequestBody Long[] ids) {
            homeSubjectSpuService.removeByIds(Arrays.asList(ids));

        return R.status();
    }

}
