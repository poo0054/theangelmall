package com.theangel.themall.seckill.openfeign;

import com.theangel.common.utils.R;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * @ProjectName: theangelmall
 * @Package: com.theangel.themall.seckill.openfeign
 * @ClassName: CouponFeignService
 * @Author: theangel
 * @Date: 2021/10/3 20:30
 */
@FeignClient("themall-coupon")
public interface CouponFeignService {
    /**
     * 获取3天的活动
     */
    @GetMapping("coupon/seckillsession/lates3DaySession")
    R getLates3DaySession();
}
