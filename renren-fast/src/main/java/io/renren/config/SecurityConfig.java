package io.renren.config;

import io.renren.filter.DefaultAccessDeniedHandler;
import io.renren.filter.DefaultAuthenticationEntryPoint;
import io.renren.filter.JWTBasicAuthenticationFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

/**
 * @author poo0054
 */
@Configuration
@EnableWebSecurity(debug = true)
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    JWTBasicAuthenticationFilter jwtBasicAuthenticationFilter;

    @Autowired
    DefaultAccessDeniedHandler defaultAccessDeniedHandler;

    @Autowired
    DefaultAuthenticationEntryPoint defaultAuthenticationEntryPoint;

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .exceptionHandling(exceptionHandling -> {
                    exceptionHandling.accessDeniedHandler(defaultAccessDeniedHandler).authenticationEntryPoint(defaultAuthenticationEntryPoint);
                })
                .addFilterBefore(jwtBasicAuthenticationFilter, JWTBasicAuthenticationFilter.class)
                .authorizeRequests(authorizeRequests -> {
                    authorizeRequests.mvcMatchers("/sys/login", "/sys/logout", "/captcha.jpg").permitAll().anyRequest().authenticated();
                })
                .cors().disable()
                .csrf().disable()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
        ;


    }

    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }


}
