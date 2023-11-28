package com.themall.cart.web;

import com.themall.cart.service.CartService;
import com.themall.cart.vo.Cart;
import com.themall.cart.vo.CartItem;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpSession;
import java.util.concurrent.ExecutionException;

/**
 * 购物车
 *
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
     * 改变商品数量
     *
     * @param skuId
     * @return
     */
    @GetMapping("/deleteitem")
    public String deleteItem(@RequestParam("skuId") Long skuId) {
        cartService.deleteItem(skuId);
        return "redirect:https://cart.poo0054.top/cart.html";
    }

    /**
     * 改变商品数量
     *
     * @param skuId
     * @param num
     * @return
     */
    @GetMapping("/countitem")
    public String countItem(@RequestParam("skuId") Long skuId, @RequestParam("num") Integer num) {
        cartService.countItem(skuId, num);
        return "redirect:https://cart.poo0054.top/cart.html";
    }

    /**
     * 改变选中状态
     *
     * @param skuId
     * @return
     */
    @GetMapping("/checkitem")
    public String checkItem(@RequestParam("skuId") Long skuId) {
        cartService.checkItem(skuId);
        return "redirect:https://cart.poo0054.top/cart.html";
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
        return "redirect:https://cart.poo0054.top/addtocart.html";
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
