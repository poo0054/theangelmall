package com.theangel.themall.cart.controller;

import com.theangel.common.utils.R;
import com.theangel.themall.cart.service.CartService;
import com.theangel.themall.cart.vo.CartItem;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @ProjectName: theangelmall
 * @Package: com.theangel.themall.cart.controller
 * @ClassName: CartController
 * @Author: theangel
 * @Date: 2021/8/31 22:26
 */
@RestController
@RequestMapping("cart/cartitem")
public class CartItemController {
    @Autowired
    CartService cartService;

    /**
     * 根据id查询所有购物项
     */
    @GetMapping("/cartitem")
    public R getCartItemById() {
        List<CartItem> list = cartService.getCartItemBy();
        return R.ok().setData(list);
    }
}
