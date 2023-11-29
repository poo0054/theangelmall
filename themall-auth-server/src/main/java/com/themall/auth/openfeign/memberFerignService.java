package com.themall.auth.openfeign;

import com.themall.auth.vo.LoginUserVo;
import com.themall.auth.vo.UserRegisterterVo;
import com.themall.common.utils.R;
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
    public R regist(@RequestBody UserRegisterterVo userRegistertVo);

    /**
     * 登录会员
     *
     * @return
     */
    @PostMapping(path = "member/member/login")
    public R login(@RequestBody LoginUserVo loginUserVo);
}
