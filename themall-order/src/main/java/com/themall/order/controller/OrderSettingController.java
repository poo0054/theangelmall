package com.themall.order.controller;

import com.themall.common.utils.PageUtils;
import com.themall.model.entity.R;
import com.themall.order.entity.OrderSettingEntity;
import com.themall.order.service.OrderSettingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.Map;


/**
 * 订单配置信息
 *
 * @author theangel
 * @email poo0054@outlook.com
 * @date 2021-06-10 19:36:04
 */
@RestController
@RequestMapping("order/ordersetting")
public class OrderSettingController {
    @Autowired
    private OrderSettingService orderSettingService;

    /**
     * 列表
     */
    @RequestMapping("/list")
    //@RequiresPermissions("order:ordersetting:list")
    public R list(@RequestParam Map<String, Object> params) {
        PageUtils page = orderSettingService.queryPage(params);

        return R.status().put("page", page);
    }


    /**
     * 信息
     */
    @RequestMapping("/info/{id}")
    //@RequiresPermissions("order:ordersetting:info")
    public R info(@PathVariable("id") Long id) {
            OrderSettingEntity orderSetting = orderSettingService.getById(id);

        return R.status().put("orderSetting", orderSetting);
    }

    /**
     * 保存
     */
    @RequestMapping("/save")
    //@RequiresPermissions("order:ordersetting:save")
    public R save(@RequestBody OrderSettingEntity orderSetting) {
            orderSettingService.save(orderSetting);

        return R.status();
    }

    /**
     * 修改
     */
    @RequestMapping("/update")
    //@RequiresPermissions("order:ordersetting:update")
    public R update(@RequestBody OrderSettingEntity orderSetting) {
            orderSettingService.updateById(orderSetting);

        return R.status();
    }

    /**
     * 删除
     */
    @RequestMapping("/delete")
    //@RequiresPermissions("order:ordersetting:delete")
    public R delete(@RequestBody Long[] ids) {
            orderSettingService.removeByIds(Arrays.asList(ids));

        return R.status();
    }

}
