package com.theangel.themall.member.interceptor;

import com.theangel.common.constant.AuthServerConstant;
import com.theangel.common.to.MemberVo;
import org.springframework.stereotype.Component;
import org.springframework.util.AntPathMatcher;
import org.springframework.util.ObjectUtils;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * 只有用户登录才能访问
 *
 * @ProjectName: theangelmall
 * @Package: com.theangel.themall.order.interceptor
 * @ClassName: LoginInterceptor
 * @Author: theangel
 * @Date: 2021/9/5 20:47
 */
@Component
public class LoginInterceptor implements HandlerInterceptor {

    public static ThreadLocal<MemberVo> threadLocal = new ThreadLocal<>();

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String requestURI = request.getRequestURI();
        //member/member/login
        /**
         * member/memberreceiveaddress/info/{id}
         */
        //这个是远程调用之间的接口，可以不用登录
        boolean match = new AntPathMatcher().match("/member/**", requestURI);
        if (match) {
            System.out.println("地址" + requestURI + "不用登录！！！！！！！！！！");
            return true;
        }

        HttpSession session = request.getSession();
        MemberVo attribute = (MemberVo) session.getAttribute(AuthServerConstant.LOGIN_USER);
        //登录成功
        if (!ObjectUtils.isEmpty(attribute)) {
            threadLocal.set(attribute);
            return true;
        }
        //未登录，去登录页面
        response.sendRedirect("http://auth.theangel.com/auth/login.html");
        return false;

    }

}
