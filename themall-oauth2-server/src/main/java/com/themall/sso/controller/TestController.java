package com.themall.sso.controller;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author poo0054
 */
@RestController
@RequestMapping
public class TestController {

    @GetMapping("test")
    @PreAuthorize("hasAuthority('SCOPE_read:user') or hasAuthority('ROLE_USER') ")
    public String test() {
        return "你好";
    }
}
