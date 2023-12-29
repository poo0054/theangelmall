package com.themall.member.controller;

import com.themall.common.utils.PageUtils;
import com.themall.member.entity.MemberLoginLogEntity;
import com.themall.member.service.MemberLoginLogService;
import com.themall.model.entity.R;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.Map;


/**
 * 会员登录记录
 *
 * @author theangel
 * @email poo0054@outlook.com
 * @date 2021-06-09 21:47:50
 */
@RestController
@RequestMapping("member/memberloginlog")
public class MemberLoginLogController {
    @Autowired
    private MemberLoginLogService memberLoginLogService;

    /**
     * 列表
     */
    @RequestMapping("/list")
    //@RequiresPermissions("member:memberloginlog:list")
    public R list(@RequestParam Map<String, Object> params) {
        PageUtils page = memberLoginLogService.queryPage(params);

        return R.status().put("page", page);
    }


    /**
     * 信息
     */
    @RequestMapping("/info/{id}")
    //@RequiresPermissions("member:memberloginlog:info")
    public R info(@PathVariable("id") Long id) {
            MemberLoginLogEntity memberLoginLog = memberLoginLogService.getById(id);

        return R.status().put("memberLoginLog", memberLoginLog);
    }

    /**
     * 保存
     */
    @RequestMapping("/save")
    //@RequiresPermissions("member:memberloginlog:save")
    public R save(@RequestBody MemberLoginLogEntity memberLoginLog) {
            memberLoginLogService.save(memberLoginLog);

        return R.status();
    }

    /**
     * 修改
     */
    @RequestMapping("/update")
    //@RequiresPermissions("member:memberloginlog:update")
    public R update(@RequestBody MemberLoginLogEntity memberLoginLog) {
            memberLoginLogService.updateById(memberLoginLog);

        return R.status();
    }

    /**
     * 删除
     */
    @RequestMapping("/delete")
    //@RequiresPermissions("member:memberloginlog:delete")
    public R delete(@RequestBody Long[] ids) {
            memberLoginLogService.removeByIds(Arrays.asList(ids));

        return R.status();
    }

}
