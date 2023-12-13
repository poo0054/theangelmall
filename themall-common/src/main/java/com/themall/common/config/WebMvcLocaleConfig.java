package com.themall.common.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.LocaleResolver;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport;
import org.springframework.web.servlet.i18n.FixedLocaleResolver;

import java.util.Locale;

/**
 * @author poo0054
 */
@Configuration(proxyBeanMethods = false)
public class WebMvcLocaleConfig extends WebMvcConfigurationSupport {

    public LocaleResolver localeResolver() {
        FixedLocaleResolver fixedLocaleResolver = new FixedLocaleResolver(Locale.SIMPLIFIED_CHINESE);
        return fixedLocaleResolver;
    }
}
