package com.theangel.themall.product.web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Controller
public class ItemController {

    @GetMapping("/item/{skuid}.html")
    public String item(@PathVariable("skuid") Long skyId) {
        System.out.println("skuid为" + skyId);
        return "item";
    }
}
