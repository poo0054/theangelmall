package com.theangel.themall.cart.web;

import com.theangel.common.constant.AuthServerConstant;
import com.theangel.themall.cart.interceptor.CartInterceptor;
import com.theangel.themall.cart.service.CartService;
import com.theangel.themall.cart.to.CartUserTo;
import com.theangel.themall.cart.vo.CartItem;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpSession;
import java.util.concurrent.ExecutionException;

/**
 * @ProjectName: theangelmall
 * @Package: com.theangel.themall.cart.web
 * @ClassName: CartController
 * @Author: theangel
 * @Date: 2021/8/29 12:49
 */
@Controller
public class CartController {

    @Autowired
    CartService cartService;

    /**
     * 购物车，列表页
     * cookie: user-key  京东 。表示用户key，一个月过期
     * 如果第一次使用京东购物车，会临时给一个用户身份，user-key
     * 没有登录：按照user-key
     * 登录：session中存在
     *
     * @param model
     * @param session
     * @return
     */
    @GetMapping("/cart.html")
    public String cartList(Model model, HttpSession session) {
        CartUserTo cartUserTo = CartInterceptor.threadLocal.get();
        return "cartList";
    }


    /**
     * @return
     */
    @GetMapping("/success.html")
    public String success() {
        return "success";
    }

    /**
     * 添加商品到购物车
     *
     * @return
     */
    @GetMapping("/addtocart")
    public String addToCart(@RequestParam("skuId") Long skuId, @RequestParam("num") Integer num, Model model) throws ExecutionException, InterruptedException {

        CartItem cartItem = cartService.addToCart(skuId, num);
        model.addAttribute("item",cartItem);
        return "success";
    }


}
