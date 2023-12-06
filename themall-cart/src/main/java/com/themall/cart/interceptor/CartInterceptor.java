package com.themall.cart.interceptor;

import com.themall.cart.to.CartUserTo;
import com.themall.common.utils.fileutils.UUIDUtils;
import com.themall.model.constants.AuthServerConstant;
import com.themall.model.constants.CartConstant;
import com.themall.model.to.MemberVo;
import org.springframework.stereotype.Component;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * 判断有没有登录
 * 没有的登录就创建一个user-key
 *
 * @ProjectName: theangelmall
 * @Package: com.theangel.themall.cart.interceptor
 * @ClassName: CartInterceptor
 * @Author: theangel
 * @Date: 2021/8/29 22:15
 */
@Component
public class CartInterceptor implements HandlerInterceptor {

    //每个线程数据互通
    public static ThreadLocal<CartUserTo> threadLocal = new ThreadLocal<>();

    /**
     * 拦截之前
     *
     * @param request
     * @param response
     * @param handler
     * @return
     * @throws Exception
     */
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        CartUserTo cartUserTo = new CartUserTo();
        HttpSession session = request.getSession();
        System.out.println(session.getId());

        MemberVo attribute = (MemberVo) session.getAttribute(AuthServerConstant.LOGIN_USER);

        if (!ObjectUtils.isEmpty(attribute)) {
            cartUserTo.setUserId(attribute.getId());
        }

        Cookie[] cookies = request.getCookies();
        if (!ObjectUtils.isEmpty(cookies)) {
            for (Cookie cookie : cookies) {
                String name = cookie.getName();
                if (name.equals(CartConstant.TEMP_USER_COOKIE_NAME)) {
                    cartUserTo.setUserKey(cookie.getValue());
                    //cookie 存在临时用户
                    cartUserTo.setTempUser(true);
                }
            }
        }

        //没有登录，并且没有临时用户，需要创建一个临时用户
        if (StringUtils.isEmpty(cartUserTo.getUserId()) && StringUtils.isEmpty(cartUserTo.getUserKey())) {
            String uuid = UUIDUtils.getUUID();
            cartUserTo.setUserKey(uuid);
        }
        //查询出来的登录信息 放入
        threadLocal.set(cartUserTo);
        return true;
    }


    /**
     * 执行之后
     * 让浏览器保存临时用户cookie
     *
     * @param request
     * @param response
     * @param handler
     * @param modelAndView
     * @throws Exception
     */
    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
        CartUserTo cartUserTo = threadLocal.get();
        //cookie 中没有，需要创建一个
        if (!cartUserTo.getTempUser()) {
            Cookie cookie = new Cookie(CartConstant.TEMP_USER_COOKIE_NAME, threadLocal.get().getUserKey());
            cookie.setDomain("poo0054.top");
            cookie.setMaxAge(CartConstant.TEMP_USER_COOKIE_TIMEOUT);
            response.addCookie(cookie);
        }

    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        threadLocal.remove();
    }

}
