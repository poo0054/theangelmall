package com.theangel.themall.auth.web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("auth")
public class LoginController {

    @GetMapping({"/login", "login.html"})
    public String login() {
        return "login";
    }
}
