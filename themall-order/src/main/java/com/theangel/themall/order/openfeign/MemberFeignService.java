package com.theangel.themall.order.openfeign;

import com.theangel.common.utils.R;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient("themall-member")
public interface MemberFeignService {

    /**
     * 根据id查询收货地址
     */
    @GetMapping("/member/memberreceiveaddress/{memberid}/address")
    R getAddress(@PathVariable("memberid") Long memnerId);


}
