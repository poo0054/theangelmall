package com.themall.cart.config;

import feign.RequestInterceptor;
import feign.RequestTemplate;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
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
@Configuration
@EnableFeignClients("com.theangel.themall.cart.openfeign")
public class FeignConfig {
    @Bean
    public RequestInterceptor requestInterceptor() {
        return new RequestInterceptor() {
            @Override
            public void apply(RequestTemplate template) {
                log.info("远程之前，调用====================");
                //获取当前请求参数，进来的requert
                /**
                 * RequestContextHolder   Request上下文，mvc封装的
                 * ServletRequestAttributes是RequestAttributess的实现类
                 */
                ServletRequestAttributes requestAttributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
                if (!ObjectUtils.isEmpty(requestAttributes)) {
                    //获取当前request的cookie,放入新cookie
                    HttpServletRequest request = requestAttributes.getRequest();
                    String cookie = request.getHeader("Cookie");
                    //放入新请求，防止cookie丢失   给新请求同步当前request的cookie
                    template.header("Cookie", cookie);
                }
            }
        };
    }

    /**
     * 监听器：监听HTTP请求事件
     * 解决RequestContextHolder.getRequestAttributes()空指针问题
     *
     * @return
     */
//    @Bean
    public RequestContextListener requestContextListener() {
        return new RequestContextListener();
    }
}
