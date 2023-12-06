package com.themall.model.constants;

/**
 * @ProjectName: theangelmall
 * @Package: com.theangel.common.constant
 * @ClassName: CartConstant
 * @Author: theangel
 * @Date: 2021/8/29 22:25
 */
public class CartConstant {
    //临时用户的key
    public static final String TEMP_USER_COOKIE_NAME = "user-key";
    //过期时间
    public static final Integer TEMP_USER_COOKIE_TIMEOUT = 60 * 60 * 24 * 30;
    //购物车前缀   cart:skuId:信息
    public static final String CART_PREFIX = "cart:";
}
