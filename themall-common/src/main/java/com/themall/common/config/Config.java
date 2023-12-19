package com.themall.common.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

/**
 * @author poo0054
 */
@Import({FeignConfig.class, WebMvcLocaleConfig.class, FastJsonConfig.class, ExceptionConfig.class})
@Configuration
public class Config {


}
