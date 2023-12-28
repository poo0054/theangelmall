package com.themall.common.config;

import feign.RequestInterceptor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpHeaders;
import org.springframework.util.ObjectUtils;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.RequestContextListener;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;

/**
 * RequestInterceptor请求头丢失：
 * 每次远程调用的时候，会创建一个新的request，新的request都会加载所有RequestInterceptor，自定义RequestInterceptor添加请求头
 *
 * @ProjectName: theangelmall
 * @Package: com.theangel.themall.cart.config
 * @ClassName: FeigConfig
 * @Author: theangel
 * @Date: 2021/9/6 21:48
 */
@Slf4j
@Configuration(proxyBeanMethods = false)
public class FeignConfig {

    @Bean
    public RequestInterceptor requestInterceptor() {
        return template -> {
            log.info("远程之前，调用=========添加权限");
            ServletRequestAttributes requestAttributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
            if (!ObjectUtils.isEmpty(requestAttributes)) {
                //获取当前request的cookie,放入新cookie
                HttpServletRequest request = requestAttributes.getRequest();
                //放入新请求 授权
                template.header(HttpHeaders.AUTHORIZATION, request.getHeader(HttpHeaders.AUTHORIZATION));

                //获取当前request的cookie,放入新cookie
                String cookie = request.getHeader("Cookie");
                //放入新请求，防止cookie丢失   给新请求同步当前request的cookie
                template.header("Cookie", cookie);
            }
        };
    }

    /**
     * 监听器：监听HTTP请求事件
     * 解决RequestContextHolder.getRequestAttributes()空指针问题
     */
    @Bean
    public RequestContextListener requestContextListener() {
        return new RequestContextListener();
    }
}
