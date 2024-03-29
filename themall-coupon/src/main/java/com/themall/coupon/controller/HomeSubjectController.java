package com.themall.coupon.controller;

import com.themall.common.utils.PageUtils;
import com.themall.coupon.entity.HomeSubjectEntity;
import com.themall.coupon.service.HomeSubjectService;
import com.themall.model.entity.R;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.Map;


/**
 * 首页专题表【jd首页下面很多专题，每个专题链接新的页面，展示专题商品信息】
 *
 * @author theangel
 * @email poo0054@outlook.com
 * @date 2021-06-09 20:52:38
 */
@RestController
@RequestMapping("coupon/homesubject")
public class HomeSubjectController {
    @Autowired
    private HomeSubjectService homeSubjectService;

    /**
     * 列表
     */
    @RequestMapping("/list")
    //@RequiresPermissions("coupon:homesubject:list")
    public R list(@RequestParam Map<String, Object> params) {
        PageUtils page = homeSubjectService.queryPage(params);

        return R.status().put("page", page);
    }


    /**
     * 信息
     */
    @RequestMapping("/info/{id}")
    //@RequiresPermissions("coupon:homesubject:info")
    public R info(@PathVariable("id") Long id) {
            HomeSubjectEntity homeSubject = homeSubjectService.getById(id);

        return R.status().put("homeSubject", homeSubject);
    }

    /**
     * 保存
     */
    @RequestMapping("/save")
    //@RequiresPermissions("coupon:homesubject:save")
    public R save(@RequestBody HomeSubjectEntity homeSubject) {
            homeSubjectService.save(homeSubject);

        return R.status();
    }

    /**
     * 修改
     */
    @RequestMapping("/update")
    //@RequiresPermissions("coupon:homesubject:update")
    public R update(@RequestBody HomeSubjectEntity homeSubject) {
            homeSubjectService.updateById(homeSubject);

        return R.status();
    }

    /**
     * 删除
     */
    @RequestMapping("/delete")
    //@RequiresPermissions("coupon:homesubject:delete")
    public R delete(@RequestBody Long[] ids) {
            homeSubjectService.removeByIds(Arrays.asList(ids));

        return R.status();
    }

}
