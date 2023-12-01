package com.themall.sso.web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @author poo0054
 */
@Controller
public class LoginWebController {

    @RequestMapping("/login/index.html")
    public String login() {
        return "index.html";
    }
}
