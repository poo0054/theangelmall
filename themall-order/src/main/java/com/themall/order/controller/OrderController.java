package com.themall.order.controller;

import com.themall.common.utils.PageUtils;
import com.themall.common.utils.R;
import com.themall.order.entity.OrderEntity;
import com.themall.order.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.Map;


/**
 * 订单
 *
 * @author theangel
 * @email poo0054@outlook.com
 * @date 2021-06-10 19:36:04
 */
@RestController
@RequestMapping("order/order")
public class OrderController {
    @Autowired
    private OrderService orderService;


    /**
     * 根据订单sn，查询订单状态
     */
    @GetMapping("/status/{orderSn}")
    public R getOrderStock(@PathVariable("orderSn") String orderSn) {
        OrderEntity orderEntity = orderService.getOrderStockByOrderSn(orderSn);
        return R.httpStatus().setData(orderEntity);
    }

    /**
     * 列表
     */
    @GetMapping("/list")
    public R list(@RequestParam Map<String, Object> params) {
        PageUtils page = orderService.queryPage(params);
        return R.httpStatus().put("page", page);
    }

    /**
     * 查询用户订单
     */
    @PostMapping("/list/with/item")
    public R listWithItem(@RequestBody Map<String, Object> params) {
        PageUtils page = orderService.listWithItem(params);
        return R.httpStatus().put("page", page);
    }


    /**
     * 信息
     */
    @RequestMapping("/info/{id}")
    //@RequiresPermissions("order:order:info")
    public R info(@PathVariable("id") Long id) {
        OrderEntity order = orderService.getById(id);

        return R.httpStatus().put("order", order);
    }

    /**
     * 保存
     */
    @RequestMapping("/save")
    //@RequiresPermissions("order:order:save")
    public R save(@RequestBody OrderEntity order) {
        orderService.save(order);

        return R.httpStatus();
    }

    /**
     * 修改
     */
    @RequestMapping("/update")
    //@RequiresPermissions("order:order:update")
    public R update(@RequestBody OrderEntity order) {
        orderService.updateById(order);

        return R.httpStatus();
    }

    /**
     * 删除
     */
    @RequestMapping("/delete")
    //@RequiresPermissions("order:order:delete")
    public R delete(@RequestBody Long[] ids) {
        orderService.removeByIds(Arrays.asList(ids));

        return R.httpStatus();
    }

}
