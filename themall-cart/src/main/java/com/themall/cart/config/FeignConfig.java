package com.themall.cart.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Configuration;

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
@EnableFeignClients("com.themall.cart.openfeign")
public class FeignConfig {

}
