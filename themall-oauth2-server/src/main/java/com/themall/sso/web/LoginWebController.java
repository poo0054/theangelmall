package com.themall.sso.web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * @author poo0054
 */
@Controller
public class LoginWebController {

    @GetMapping("/login")
    public String login() {
        return "login";
    }

}
