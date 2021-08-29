package com.theangel.themall.auth.web;

import com.alibaba.fastjson.TypeReference;
import com.theangel.common.constant.AuthServerConstant;
import com.theangel.common.to.MemberVo;
import com.theangel.common.utils.R;
import com.theangel.themall.auth.openfeign.ThemallmemberFerign;
import com.theangel.themall.auth.vo.LoginUserVo;
import org.apache.http.HttpResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.Map;

@Controller
@RequestMapping("/auth")
public class LoginController {

    @Autowired
    ThemallmemberFerign themallmemberFerign;


    @GetMapping("/login.html")
    public String webLogin(HttpSession session) {
        Object attribute = session.getAttribute(AuthServerConstant.LOGIN_USER);
        if (ObjectUtils.isEmpty(attribute)) {
            return "login";
        }
        return "redirect:http://theangel.com";
    }

    /**
     * 登录
     *
     * @param loginUserVo
     * @param attributes
     * @param httpResponse
     * @return
     */
    @PostMapping("/login")
    public String login(LoginUserVo loginUserVo, RedirectAttributes attributes, HttpSession httpResponse) {
        R login = themallmemberFerign.login(loginUserVo);
        if (login.getCode() == 0) {
            //添加入ression的redis中
            MemberVo data = login.getData(new TypeReference<MemberVo>() {
            });
            //登录成功，redis存入返回的数据
            httpResponse.setAttribute(AuthServerConstant.LOGIN_USER, data);
            return "redirect:http://theangel.com";
        } else {
            Map<String, String> map = new HashMap();
            map.put("msg", login.getData("msg", new TypeReference<String>() {
            }));
            attributes.addFlashAttribute("errors", map);
            return "redirect:http://auth.theangel.com/auth/login.html";
        }

    }
}
