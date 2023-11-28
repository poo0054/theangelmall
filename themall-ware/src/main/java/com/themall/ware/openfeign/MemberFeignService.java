package com.themall.ware.openfeign;

import com.themall.common.utils.R;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @ProjectName: theangelmall
 * @Package: com.theangel.themall.ware.openfeign
 * @ClassName: MemberFeign
 * @Author: theangel
 * @Date: 2021/9/11 18:04
 */
@FeignClient("themall-member")
public interface MemberFeignService {
    /**
     * 根据用戶id查询收货地址
     */
    @GetMapping("member/memberreceiveaddress/{memberid}/address")
    R getAddress(@PathVariable("memberid") Long memnerId);

    /**
     * 根据地址id查询地址信息
     * 信息
     */
    @RequestMapping("member/memberreceiveaddress/info/{id}")
    R getAddrInfo(@PathVariable("id") Long id);
}
