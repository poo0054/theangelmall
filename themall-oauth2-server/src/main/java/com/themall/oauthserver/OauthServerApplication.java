package com.themall.oauthserver;

import com.themall.common.config.ResourceSecurityConfig;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @author poo0054
 */
@SpringBootApplication(exclude = ResourceSecurityConfig.class)
public class OauthServerApplication {
    public static void main(String[] args) {
        SpringApplication.run(OauthServerApplication.class, args);
    }
}
