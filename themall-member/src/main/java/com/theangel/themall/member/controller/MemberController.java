package com.theangel.themall.member.controller;

import java.util.Arrays;
import java.util.Map;

//import org.apache.shiro.authz.annotation.RequiresPermissions;
import com.theangel.themall.member.openfeign.CouponOpenFeignService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.theangel.themall.member.entity.MemberEntity;
import com.theangel.themall.member.service.MemberService;
import com.theangel.common.utils.PageUtils;
import com.theangel.common.utils.R;


/**
 * 会员
 *
 * @author theangel
 * @email poo0054@outlook.com
 * @date 2021-06-09 21:47:49
 */
@RestController
@RequestMapping("member/member")
public class MemberController {
    @Autowired
    private MemberService memberService;
    
    
    @Autowired
    private CouponOpenFeignService couponOpenFeignService;
    
    @RequestMapping(path = "/membercoupon", method = RequestMethod.GET)
    public R getMemberCoupon() {
        MemberEntity memberEntity = new MemberEntity();
        memberEntity.setNickname("张三");
        return R.ok().put("member", memberEntity).put("coupon", couponOpenFeignService.memberList().get("coupons"));
    }
    
    /**
     * 列表
     */
    @RequestMapping("/list")
    //@RequiresPermissions("member:member:list")
    public R list(@RequestParam Map<String, Object> params) {
        PageUtils page = memberService.queryPage(params);
        
        return R.ok().put("page", page);
    }
    
    
    /**
     * 信息
     */
    @RequestMapping("/info/{id}")
    //@RequiresPermissions("member:member:info")
    public R info(@PathVariable("id") Long id) {
        MemberEntity member = memberService.getById(id);
        
        return R.ok().put("member", member);
    }
    
    /**
     * 保存
     */
    @RequestMapping("/save")
    //@RequiresPermissions("member:member:save")
    public R save(@RequestBody MemberEntity member) {
        memberService.save(member);
        
        return R.ok();
    }
    
    /**
     * 修改
     */
    @RequestMapping("/update")
    //@RequiresPermissions("member:member:update")
    public R update(@RequestBody MemberEntity member) {
        memberService.updateById(member);
        
        return R.ok();
    }
    
    /**
     * 删除
     */
    @RequestMapping("/delete")
    //@RequiresPermissions("member:member:delete")
    public R delete(@RequestBody Long[] ids) {
        memberService.removeByIds(Arrays.asList(ids));
        
        return R.ok();
    }
    
}
