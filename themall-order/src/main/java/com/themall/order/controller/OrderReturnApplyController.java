package com.themall.order.controller;

import com.themall.common.utils.PageUtils;
import com.themall.model.entity.R;
import com.themall.order.entity.OrderReturnApplyEntity;
import com.themall.order.service.OrderReturnApplyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.Map;


/**
 * 订单退货申请
 *
 * @author theangel
 * @email poo0054@outlook.com
 * @date 2021-06-10 19:36:04
 */
@RestController
@RequestMapping("order/orderreturnapply")
public class OrderReturnApplyController {
    @Autowired
    private OrderReturnApplyService orderReturnApplyService;

    /**
     * 列表
     */
    @RequestMapping("/list")
    //@RequiresPermissions("order:orderreturnapply:list")
    public R list(@RequestParam Map<String, Object> params) {
        PageUtils page = orderReturnApplyService.queryPage(params);

        return R.status().put("page", page);
    }


    /**
     * 信息
     */
    @RequestMapping("/info/{id}")
    //@RequiresPermissions("order:orderreturnapply:info")
    public R info(@PathVariable("id") Long id) {
            OrderReturnApplyEntity orderReturnApply = orderReturnApplyService.getById(id);

        return R.status().put("orderReturnApply", orderReturnApply);
    }

    /**
     * 保存
     */
    @RequestMapping("/save")
    //@RequiresPermissions("order:orderreturnapply:save")
    public R save(@RequestBody OrderReturnApplyEntity orderReturnApply) {
            orderReturnApplyService.save(orderReturnApply);

        return R.status();
    }

    /**
     * 修改
     */
    @RequestMapping("/update")
    //@RequiresPermissions("order:orderreturnapply:update")
    public R update(@RequestBody OrderReturnApplyEntity orderReturnApply) {
            orderReturnApplyService.updateById(orderReturnApply);

        return R.status();
    }

    /**
     * 删除
     */
    @RequestMapping("/delete")
    //@RequiresPermissions("order:orderreturnapply:delete")
    public R delete(@RequestBody Long[] ids) {
            orderReturnApplyService.removeByIds(Arrays.asList(ids));

        return R.status();
    }

}
