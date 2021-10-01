package com.theangel.themall.member.openfeign;

import com.theangel.common.utils.R;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;

@FeignClient("themall-coupon")
public interface CouponFeignService {

    @PostMapping("/coupon/coupon/member/list")
    R memberList();
}
