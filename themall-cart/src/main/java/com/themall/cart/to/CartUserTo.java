package com.themall.cart.to;

import lombok.Data;

/**
 * 购物车的临时用户和登录用户id
 *
 * @ProjectName: theangelmall
 * @Package: com.theangel.themall.cart.vo
 * @ClassName: CartUserVo
 * @Author: theangel
 * @Date: 2021/8/29 22:20
 */
@Data
public class CartUserTo {
    //登录用户
    private Long userId;
    //临时用户
    private String userKey;

    private Boolean tempUser = false;
}
