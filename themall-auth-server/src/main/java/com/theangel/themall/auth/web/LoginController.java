package com.theangel.themall.auth.web;

import com.alibaba.fastjson.TypeReference;
import com.theangel.common.utils.R;
import com.theangel.themall.auth.openfeign.ThemallmemberFerign;
import com.theangel.themall.auth.vo.LoginUserVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.HashMap;
import java.util.Map;

@Controller
@RequestMapping("/auth")
public class LoginController {

    @Autowired
    ThemallmemberFerign themallmemberFerign;

    @PostMapping("/login")
    public String login(LoginUserVo loginUserVo, RedirectAttributes attributes) {
        R login = themallmemberFerign.login(loginUserVo);
        if (login.getCode() == 0) {
            //TODO 登录成功后，添加token

            return "redirect:http://localhost";
        } else {
            Map<String, String> map = new HashMap();
            map.put("msg", login.getData("msg", new TypeReference<String>() {
            }));
            attributes.addFlashAttribute("errors", map);
            return "redirect:http://localhost/auth/login.html";
        }

    }
}
