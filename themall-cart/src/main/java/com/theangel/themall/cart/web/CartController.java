package com.theangel.themall.cart.web;

import com.theangel.common.constant.AuthServerConstant;
import com.theangel.themall.cart.interceptor.CartInterceptor;
import com.theangel.themall.cart.service.CartService;
import com.theangel.themall.cart.to.CartUserTo;
import com.theangel.themall.cart.vo.Cart;
import com.theangel.themall.cart.vo.CartItem;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpSession;
import java.util.List;
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

    @GetMapping("/checkitem")
    public String checkItem(@RequestParam("skuId") Long skuId) {
        cartService.checkItem(skuId);
        return "redirect:http://cart.theangel.com/cart.html";
    }

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
    public String cartList(Model model, HttpSession session) throws ExecutionException, InterruptedException {
        Cart cart = cartService.getcartList();
        model.addAttribute("cart", cart);
        return "cartList";
    }


    /**
     * 添加商品到购物车
     * redirectAttributes
     * addAttribute 会在？后面拼接
     * redirectAttributes.()  会存在session中  只能使用一次
     *
     * @return
     */
    @GetMapping("/addtocart")
    public String addToCart(@RequestParam("skuId") Long skuId, @RequestParam("num") Integer num, RedirectAttributes redirectAttributes) throws ExecutionException, InterruptedException {
        cartService.addToCart(skuId, num);
        redirectAttributes.addAttribute("skuId", skuId);
        return "redirect:http://cart.theangel.com/addtocart.html";
    }

    /**
     * 查询购物车 展示购物车
     *
     * @return
     */
    @GetMapping("/addtocart.html")
    public String addToCartSuccessPage(@RequestParam("skuId") Long skuId, Model model) {
        //重定向到成功页面 重新查询一次
        CartItem cartItem = cartService.getCartItem(skuId);
        model.addAttribute("item", cartItem);
        return "success";
    }
}