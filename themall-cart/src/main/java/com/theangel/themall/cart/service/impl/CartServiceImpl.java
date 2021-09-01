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
import com.theangel.themall.cart.vo.Cart;
import com.theangel.themall.cart.vo.CartItem;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.BoundHashOperations;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;

import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.stream.Collectors;

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

    /**
     * 改变商品数量
     *
     * @param skuId
     * @param num
     */
    @Override
    public void countItem(Long skuId, Integer num) {
        CartItem cartItem = getCartItem(skuId);
        cartItem.setCount(num);
        BoundHashOperations<String, Object, Object> cartOps = getCartOps();
        cartOps.put(skuId.toString(), JSON.toJSONString(cartItem));
    }

    @Override
    public void deleteItem(Long skuId) {
        BoundHashOperations<String, Object, Object> cartOps = getCartOps();
        cartOps.delete(skuId.toString());
    }

    @Override
    public CartItem addToCart(Long skuId, Integer num) throws ExecutionException, InterruptedException {
        BoundHashOperations<String, Object, Object> cartOps = getCartOps();
        String redidCart = (String) cartOps.get(skuId.toString());
        //购物车没有该商品
        if (StringUtils.isEmpty(redidCart)) {
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
        } else {
            //商品存在，添加购物数量
            CartItem cartItem = JSON.parseObject(redidCart, CartItem.class);
            cartItem.setCount(cartItem.getCount() + num);
            cartOps.put(skuId.toString(), JSON.toJSONString(cartItem));
            return cartItem;
        }

    }


    /**
     * 查询商品，返回给查询页面
     *
     * @param skuId
     * @return
     */
    @Override
    public CartItem getCartItem(Long skuId) {
        BoundHashOperations<String, Object, Object> cartOps = getCartOps();
        String o = (String) cartOps.get(skuId.toString());
        return JSON.parseObject(o, CartItem.class);
    }

    /**
     * 根据用户获取
     *
     * @return
     */
    @Override
    public Cart getcartList() throws ExecutionException, InterruptedException {
        Cart cart = new Cart();
        CartUserTo cartUserTo = CartInterceptor.threadLocal.get();
        //临时用户和登录用户都不为空  把临时用户的数量添加入登录用户
        if (!ObjectUtils.isEmpty(cartUserTo.getUserId()) && !ObjectUtils.isEmpty(cartUserTo.getUserKey())) {
            //获取临时用户的商品项
            List<CartItem> cartItem = getCartItemById(cartUserTo.getUserKey());
            //临时用户有数据 需要合并
            if (!ObjectUtils.isEmpty(cartItem)) {
                for (CartItem item : cartItem) {
                    addToCart(item.getSkuId(), item.getCount());
                }
                //清除临时用户车
                clearCartById(CartConstant.CART_PREFIX + cartUserTo.getUserKey());
            }
        }
        List<CartItem> cartItem1 = getCartItem();
        cart.setCartItem(cartItem1);
        return cart;
    }


    /**
     * 删除购物车
     *
     * @param name
     */
    @Override
    public void clearCartById(String name) {
        redisTemplate.delete(name);
    }

    /**
     * 改变选中状态
     *
     * @param skuId
     * @return
     */
    @Override
    public void checkItem(Long skuId) {
        CartItem cartItem = getCartItem(skuId);
        cartItem.setCheck(!cartItem.getCheck());
        BoundHashOperations<String, Object, Object> cartOps = getCartOps();
        cartOps.put(skuId.toString(), JSON.toJSONString(cartItem));
    }


    /**
     * 获取当前线程用户的购物车项
     * 默认获取登录用户  不存在则获取临时用户
     *
     * @return
     */
    private List<CartItem> getCartItem() {
        return getCartItemById(null);
    }


    /**
     * 获取指定用户的购物车所有内容
     *
     * @return
     */
    private List<CartItem> getCartItemById(String name) {
        BoundHashOperations<String, Object, Object> hashOps = null;
        //为空，获取当前线程的bound redis信息   如果登录 优先获取登录的信息
        if (StringUtils.isEmpty(name)) {
            hashOps = getCartOps();
        } else {
            hashOps = redisTemplate.boundHashOps(CartConstant.CART_PREFIX + name);
        }
        List<Object> values = hashOps.values();
        if (!ObjectUtils.isEmpty(values)) {
            List<CartItem> collect = values.stream().map(item -> {
                CartItem cartItem = JSON.parseObject((String) item, CartItem.class);
                return cartItem;
            }).collect(Collectors.toList());
            return collect;
        }
        return null;
    }

    /**
     * 获取指定用户购物车
     *
     * @return
     */
    private BoundHashOperations<String, Object, Object> getCartOps(String name) {
        return redisTemplate.boundHashOps(CartConstant.CART_PREFIX + name);
    }


    /**
     * 获取当前线程用户购物车信息
     * 默认先获取登录的用户  如果登录用户为空  则获取临时用户
     *
     * @return
     */
    private BoundHashOperations<String, Object, Object> getCartOps() {
        CartUserTo cartUserTo = CartInterceptor.threadLocal.get();
        String cartPrefix = "";
        //未登录  临时用户
        if (!ObjectUtils.isEmpty(cartUserTo.getUserId())) {
            cartPrefix = cartUserTo.getUserId().toString();
        } else {
            cartPrefix = cartUserTo.getUserKey();
        }
        return getCartOps(cartPrefix);
    }
}
