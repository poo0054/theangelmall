package com.themall.member.controller;

import com.themall.common.utils.PageUtils;
import com.themall.member.entity.MemberEntity;
import com.themall.member.exception.MemberExection;
import com.themall.member.openfeign.CouponFeignService;
import com.themall.member.service.MemberService;
import com.themall.member.vo.LoginUserVo;
import com.themall.member.vo.MemberRegistVo;
import com.themall.model.constants.HttpStatusEnum;
import com.themall.model.entity.R;
import com.themall.model.exception.RRException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.Map;


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
    private CouponFeignService couponOpenFeignService;

    /**
     * 登录会员
     *
     * @return
     */
    @PostMapping(path = "/login")
    public R login(@RequestBody LoginUserVo loginUserVo) {
        try {
            MemberEntity login = memberService.login(loginUserVo);
            return R.httpStatus().setData(login);
        } catch (RRException e) {
            return R.error(HttpStatusEnum.USER_ERROR_A0120);
        }
    }

    /**
     * 注册会员
     *
     * @return
     */
    @PostMapping(path = "/regist")
    public R regist(@RequestBody MemberRegistVo memberRegistVo) {
        try {
            memberService.regist(memberRegistVo);
        } catch (MemberExection e) {
            return R.error(HttpStatusEnum.USER_ERROR_A0120);
        }
        return R.httpStatus();
    }


    @RequestMapping(path = "/membercoupon", method = RequestMethod.GET)
    public R getMemberCoupon() {
        MemberEntity memberEntity = new MemberEntity();
        memberEntity.setNickname("张三");
        return R.httpStatus().put("member", memberEntity).put("coupon", couponOpenFeignService.memberList().get("coupons"));
    }

    /**
     * 列表
     */
    @RequestMapping("/list")
    //@RequiresPermissions("member:member:list")
    public R list(@RequestParam Map<String, Object> params) {
        PageUtils page = memberService.queryPage(params);
        return R.httpStatus().put("page", page);
    }


    /**
     * 信息
     */
    @RequestMapping("/info/{id}")
    //@RequiresPermissions("member:member:info")
    public R info(@PathVariable("id") Long id) {
        MemberEntity member = memberService.getById(id);
        return R.httpStatus().put("member", member);
    }

    /**
     * 保存
     */
    @RequestMapping("/save")
    //@RequiresPermissions("member:member:save")
    public R save(@RequestBody MemberEntity member) {
        memberService.save(member);
        return R.httpStatus();
    }

    /**
     * 修改
     */
    @RequestMapping("/update")
    //@RequiresPermissions("member:member:update")
    public R update(@RequestBody MemberEntity member) {
        memberService.updateById(member);

        return R.httpStatus();
    }

    /**
     * 删除
     */
    @RequestMapping("/delete")
    //@RequiresPermissions("member:member:delete")
    public R delete(@RequestBody Long[] ids) {
        memberService.removeByIds(Arrays.asList(ids));

        return R.httpStatus();
    }

}
