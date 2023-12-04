package com.themall.sso.config;

import com.themall.sso.userdetails.SysUserDetailsManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.oauth2.server.resource.OAuth2ResourceServerConfigurer;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.oauth2.client.registration.ClientRegistrationRepository;
import org.springframework.security.oauth2.core.OAuth2AccessToken;
import org.springframework.security.oauth2.core.oidc.OidcUserInfo;
import org.springframework.security.oauth2.server.authorization.config.annotation.web.configurers.OAuth2AuthorizationServerConfigurer;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.util.matcher.RequestMatcher;

import java.util.HashMap;
import java.util.Map;

/**
 * @author poo0054
 */
@Configuration
@EnableWebSecurity(debug = true)
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfig {
    @Autowired
    ClientRegistrationRepository clientRegistrationRepository;

    // @formatter:off
    public static void applyDefaultSecurity(HttpSecurity http) throws Exception {
        OAuth2AuthorizationServerConfigurer authorizationServerConfigurer = new OAuth2AuthorizationServerConfigurer();
        RequestMatcher endpointsMatcher = authorizationServerConfigurer.getEndpointsMatcher();
        authorizationServerConfigurer.oidc(oidc -> {
            // 用户信息
            oidc.userInfoEndpoint(
                    userInfoEndpoint -> userInfoEndpoint.userInfoMapper(oidcUserInfoAuthenticationContext -> {
                        OAuth2AccessToken accessToken = oidcUserInfoAuthenticationContext.getAccessToken();
                        Map<String, Object> claims = new HashMap<>();
                        claims.put("accessToken", accessToken);
                        claims.put("user", oidcUserInfoAuthenticationContext.getAuthorization().getPrincipalName());
                        claims.put("role", oidcUserInfoAuthenticationContext.getAuthentication().getAuthorities());
                        return new OidcUserInfo(claims);
                    }));
        });

        http.requestMatcher(endpointsMatcher)
                .authorizeRequests(authorizeRequests -> authorizeRequests.anyRequest().authenticated())
                .csrf(csrf -> csrf.ignoringRequestMatchers(endpointsMatcher)).apply(authorizationServerConfigurer);
    }

    @Bean
    @Order(1)
    public SecurityFilterChain authorizationServerSecurityFilterChain(HttpSecurity http)
            throws Exception {
        applyDefaultSecurity(http);

        // Enable OpenID Connect 1.0
        http.getConfigurer(OAuth2AuthorizationServerConfigurer.class)
                .oidc(Customizer.withDefaults());
        http
                .oauth2ResourceServer(OAuth2ResourceServerConfigurer::jwt)

        ;
        return http.build();
    }

    @Bean
    @Order(3)
    public SecurityFilterChain defaultSecurityFilterChain(HttpSecurity http) throws Exception {
        http
                .authorizeHttpRequests((authorize) -> authorize
                        .antMatchers("/index.html", "/js/**", "/images/**", "/css/**").permitAll()
                        .anyRequest().authenticated()
                )
                .oauth2Login(oauth2 -> {
                    oauth2.loginPage("/index.html")
                            .authorizationEndpoint(authorizationEndpointConfig -> {
                                authorizationEndpointConfig.baseUri("/login/oauth2/authorization")
                                ;
                            })
                    ;
                })
                .httpBasic(Customizer.withDefaults())
                .csrf().disable()
                .logout((logout) -> logout.logoutUrl("/logout"))
        ;
        return http.build();
    }

    @Bean
    public UserDetailsService userDetailsService() {
        return new SysUserDetailsManager();
    }
}