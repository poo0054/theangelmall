package com.themall.cart.service;

import com.themall.cart.vo.Cart;
import com.themall.cart.vo.CartItem;

import java.util.List;
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

    CartItem getCartItem(Long skuId);

    Cart getcartList() throws ExecutionException, InterruptedException;

    /**
     * 清空购物车
     *
     * @param name
     */
    void clearCartById(String name);

    /**
     * 改变选中状态
     *
     * @param skuId
     * @return
     */
    void checkItem(Long skuId);

    /**
     * 改变商品数量
     *
     * @param skuId
     * @param num
     */
    void countItem(Long skuId, Integer num);

    /**
     * 删除
     *
     * @param skuId
     */
    void deleteItem(Long skuId);

    /**
     * 根据id查询购物项
     *
     * @return
     */
    List<CartItem> getCartItemBy();
}
