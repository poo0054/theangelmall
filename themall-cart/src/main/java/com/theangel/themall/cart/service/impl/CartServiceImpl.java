package com.theangel.themall.cart.service.impl;

import com.theangel.themall.cart.service.CartService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

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


}
