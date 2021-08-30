package com.theangel.themall.cart.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import com.theangel.common.constant.CartConstant;
import com.theangel.common.utils.R;
import com.theangel.themall.cart.config.ThreadConfig;
import com.theangel.themall.cart.interceptor.CartInterceptor;
import com.theangel.themall.cart.openfeign.ThemallProduct;
import com.theangel.themall.cart.service.CartService;
import com.theangel.themall.cart.to.CartUserTo;
import com.theangel.themall.cart.to.SkuInfoTo;
import com.theangel.themall.cart.vo.CartItem;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.BoundHashOperations;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ThreadPoolExecutor;

/**
 * @ProjectName: theangelmall
 * @Package: com.theangel.themall.cart.service.impl
 * @ClassName: CartServiceImpl
 * @Author: theangel
 * @Date: 2021/8/29 21:05
 */
@Slf4j
@Service
public class CartServiceImpl implements CartService {
    @Autowired
    StringRedisTemplate redisTemplate;
    @Autowired
    ThemallProduct themallProduct;
    @Autowired
    ThreadPoolExecutor poolExecutor;

    @Override
    public CartItem addToCart(Long skuId, Integer num) throws ExecutionException, InterruptedException {
        BoundHashOperations<String, Object, Object> cartOps = getCartOps();
        CartItem cartItem = new CartItem();
        CompletableFuture<Void> voidCompletableFuture = CompletableFuture.runAsync(() -> {
            //远程查询当前skuid的商品信息
            R skuInfo = themallProduct.getSkuInfo(skuId);
            if (skuInfo.getCode() == 0) {
                SkuInfoTo skuInfoTo = skuInfo.getData("skuInfo", new TypeReference<SkuInfoTo>() {
                });
                cartItem.setCheck(true);
                cartItem.setCount(num);
                cartItem.setImage(skuInfoTo.getSkuDefaultImg());
                cartItem.setTitle(skuInfoTo.getSkuTitle());
                cartItem.setSkuId(skuId);
                cartItem.setPrice(skuInfoTo.getPrice());
            }
        }, poolExecutor);

        CompletableFuture<Void> voidCompletableFuture1 = CompletableFuture.runAsync(() -> {
            //   查询sku属性信息
            R skuInfo = themallProduct.getAttrStrBySkuId(skuId);
            if (skuInfo.getCode() == 0) {
                List<String> data = skuInfo.getData(new TypeReference<List<String>>() {
                });
                cartItem.setSkuAttr(data);
            }
        }, poolExecutor);
        //所有完成才能返回
        CompletableFuture.allOf(voidCompletableFuture, voidCompletableFuture1).get();
        String s = JSON.toJSONString(cartItem);
        //存入redis
        cartOps.put(skuId.toString(), s);
        return cartItem;
    }

    /**
     * 获取要操作购物车
     *
     * @return
     */
    private BoundHashOperations<String, Object, Object> getCartOps() {
        CartUserTo cartUserTo = CartInterceptor.threadLocal.get();
        String cartPrefix = CartConstant.CART_PREFIX;
        if (!ObjectUtils.isEmpty(cartUserTo.getUserId())) {
            cartPrefix += cartUserTo.getUserId();
        } else {
            cartPrefix += cartUserTo.getUserKey();
        }
        BoundHashOperations<String, Object, Object> stringObjectObjectBoundHashOperations = redisTemplate.boundHashOps(cartPrefix);
        return stringObjectObjectBoundHashOperations;
    }
}
