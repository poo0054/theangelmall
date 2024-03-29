package com.themall.product.config;

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
@Configuration(proxyBeanMethods = false)
@EnableFeignClients("com.themall.product.openfeign")
public class FeignConfig {


}
