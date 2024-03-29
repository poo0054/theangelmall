package com.themall.auth.comfig;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * @author poo0054
 */
@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Override
    public void addViewControllers(ViewControllerRegistry registry) {
        //不用添加空的controller
//        registry.addViewController("/auth/login.html").setViewName("login");
        registry.addViewController("/auth/reg.html").setViewName("reg");
        //        WebMvcConfigurer.super.addViewControllers(registry);
    }
}
