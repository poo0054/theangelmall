package com.theangel.themall.auth.openfeign;

import com.theangel.common.utils.R;
import com.theangel.themall.auth.vo.LoginUserVo;
import com.theangel.themall.auth.vo.UserRegistVo;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient("themall-member")
public interface memberFerignService {
    /**
     * 注册会员
     *
     * @return
     */
    @PostMapping(path = "member/member/regist")
    public R regist(@RequestBody UserRegistVo userRegistVo);

    /**
     * 登录会员
     *
     * @return
     */
    @PostMapping(path = "member/member/login")
    public R login(@RequestBody LoginUserVo loginUserVo);
}
