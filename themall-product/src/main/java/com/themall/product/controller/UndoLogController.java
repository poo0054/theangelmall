package com.themall.product.controller;

import com.themall.common.utils.PageUtils;
import com.themall.model.entity.R;
import com.themall.product.pojo.entity.UndoLogEntity;
import com.themall.product.service.UndoLogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.Map;


/**
 * @author theangel
 * @email poo0054@outlook.com
 * @date 2021-06-09 20:47:29
 */
@RestController
@RequestMapping("product/undolog")
public class UndoLogController {
    @Autowired
    private UndoLogService undoLogService;

    /**
     * 列表
     */
    @RequestMapping("/list")
    //@RequiresPermissions("product:undolog:list")
    public R list(@RequestParam Map<String, Object> params) {
        PageUtils page = undoLogService.queryPage(params);

        return R.httpStatus().put("page", page);
    }


    /**
     * 信息
     */
    @RequestMapping("/info/{id}")
    //@RequiresPermissions("product:undolog:info")
    public R info(@PathVariable("id") Long id) {
        UndoLogEntity undoLog = undoLogService.getById(id);

        return R.httpStatus().put("undoLog", undoLog);
    }

    /**
     * 保存
     */
    @RequestMapping("/save")
    //@RequiresPermissions("product:undolog:save")
    public R save(@RequestBody UndoLogEntity undoLog) {
        undoLogService.save(undoLog);

        return R.httpStatus();
    }

    /**
     * 修改
     */
    @RequestMapping("/update")
    //@RequiresPermissions("product:undolog:update")
    public R update(@RequestBody UndoLogEntity undoLog) {
        undoLogService.updateById(undoLog);

        return R.httpStatus();
    }

    /**
     * 删除
     */
    @RequestMapping("/delete")
    //@RequiresPermissions("product:undolog:delete")
    public R delete(@RequestBody Long[] ids) {
        undoLogService.removeByIds(Arrays.asList(ids));

        return R.httpStatus();
    }

}
