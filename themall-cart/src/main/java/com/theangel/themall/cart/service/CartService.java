package com.theangel.themall.cart.service;

import com.theangel.themall.cart.vo.CartItem;

import java.util.concurrent.ExecutionException;

/**
 * @ProjectName: theangelmall
 * @Package: com.theangel.themall.cart.service
 * @ClassName: CartService
 * @Author: theangel
 * @Date: 2021/8/29 21:04
 */

public interface CartService {

    /**
     * 加入购物车
     * 返回所有数据库中的购物车
     *
     * @param skuId
     * @param num
     * @return
     */
    CartItem addToCart(Long skuId, Integer num) throws ExecutionException, InterruptedException;
}
