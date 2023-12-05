package com.themall.order.openfeign.impl;

import com.themall.common.constant.HttpStatusEnum;
import com.themall.common.utils.R;
import com.themall.order.openfeign.CartFeignService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * @ProjectName: theangelmall
 * @Package: com.theangel.themall.order.openfeign.impl
 * @ClassName: CartFeignServiceFallback
 * @Author: theangel
 * @Date: 2021/10/18 23:38
 */
@Slf4j
@Service
public class CartFeignServiceFallback implements CartFeignService {

    @Override
    public R getCartItem() {
        log.error("getSkuSeckillInfo方法调用熔断");
        return R.error(HttpStatusEnum.SYSTEM_ERROR_B0210);
    }
}
