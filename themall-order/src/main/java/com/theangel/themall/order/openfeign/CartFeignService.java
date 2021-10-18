package com.theangel.themall.order.openfeign;

import com.theangel.common.utils.R;
import com.theangel.themall.order.openfeign.impl.CartFeignServiceFallback;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

@FeignClient(value = "themall-cart", fallback = CartFeignServiceFallback.class)
public interface CartFeignService {


    /**
     * 查询当前用户所有购物项
     */
    @GetMapping("cart/cartitem/cartitem")
    R getCartItem();


}
