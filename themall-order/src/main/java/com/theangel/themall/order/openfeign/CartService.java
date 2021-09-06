package com.theangel.themall.order.openfeign;

import com.theangel.common.utils.R;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient("themall-cart")
public interface CartService {


    /**
     * 根据id查询所有购物项
     */
    @GetMapping("cart/cartitem/cartitem")
    public R getCartItem();


}
