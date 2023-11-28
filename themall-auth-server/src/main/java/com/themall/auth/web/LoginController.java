package com.themall.auth.web;

import com.alibaba.fastjson.TypeReference;
import com.themall.auth.openfeign.memberFerignService;
import com.themall.auth.vo.LoginUserVo;
import com.themall.common.constant.AuthServerConstant;
import com.themall.common.to.MemberVo;
import com.themall.common.utils.R;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.Map;

@Controller
@RequestMapping("/auth")
public class LoginController {

    @Autowired
    memberFerignService themallmemberFerign;


    /**
     * 跳转登录页面，如果session中存在，则跳到首页，不存在 就跳转登录页面
     *
     * @param session
     * @return
     */
    @GetMapping("/login.html")
    public String webLogin(HttpSession session) {
        Object attribute = session.getAttribute(AuthServerConstant.LOGIN_USER);
        if (ObjectUtils.isEmpty(attribute)) {
            return "login";
        }
        return "redirect:http://poo0054.top";
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
            httpResponse.getAttributeNames();
            System.out.println(httpResponse.getId());
            return "redirect:https://poo0054.top";
        } else {
            Map<String, String> map = new HashMap();
            map.put("msg", login.getData("msg", new TypeReference<String>() {
            }));
            attributes.addFlashAttribute("errors", map);
            return "redirect:https://auth.poo0054.top/auth/login.html";
        }

    }
}
