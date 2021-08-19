package com.theangel.themall.auth.web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("auth")
public class RegController {

    @GetMapping({"/reg", "/reg.html"})
    public String ref() {
        return "reg";
    }
}
