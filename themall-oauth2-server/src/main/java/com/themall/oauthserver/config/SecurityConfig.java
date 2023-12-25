package com.themall.oauthserver.config;

import com.themall.oauthserver.security.FederatedIdentityConfigurer;
import com.themall.oauthserver.security.UserRepositoryOAuth2UserHandler;
import com.themall.oauthserver.userdetails.SysUserDetailsManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.oauth2.resource.OAuth2ResourceServerProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.oauth2.client.CommonOAuth2Provider;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.client.JdbcOAuth2AuthorizedClientService;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClientService;
import org.springframework.security.oauth2.client.registration.ClientRegistration;
import org.springframework.security.oauth2.client.registration.ClientRegistrationRepository;
import org.springframework.security.oauth2.client.registration.InMemoryClientRegistrationRepository;
import org.springframework.security.oauth2.client.web.AuthenticatedPrincipalOAuth2AuthorizedClientRepository;
import org.springframework.security.oauth2.client.web.OAuth2AuthorizedClientRepository;
import org.springframework.security.web.SecurityFilterChain;

/**
 * @author poo0054
 */
@EnableWebSecurity(debug = true)
@EnableGlobalMethodSecurity(prePostEnabled = true)
@EnableConfigurationProperties(OAuth2ResourceServerProperties.class)
public class SecurityConfig {

    OAuth2ResourceServerProperties oAuth2ResourceServerProperties;
    UserRepositoryOAuth2UserHandler userRepositoryOAuth2UserHandler;

    @Autowired
    public void setoAuth2ResourceServerProperties(OAuth2ResourceServerProperties oAuth2ResourceServerProperties) {
        this.oAuth2ResourceServerProperties = oAuth2ResourceServerProperties;
    }

    @Autowired
    public void setUserRepositoryOAuth2UserHandler(UserRepositoryOAuth2UserHandler userRepositoryOAuth2UserHandler) {
        this.userRepositoryOAuth2UserHandler = userRepositoryOAuth2UserHandler;
    }

    // @formatter:off
    @Bean
    public SecurityFilterChain defaultSecurityFilterChain(HttpSecurity http) throws Exception {
        FederatedIdentityConfigurer federatedIdentityConfigurer = new FederatedIdentityConfigurer()
                .oauth2UserHandler(userRepositoryOAuth2UserHandler);
//        http://127.0.0.1:8085/themall-oauthserver
//        federatedIdentityConfigurer.loginPageUrl("http://127.0.0.1:8085/themall-oauthserver/login");
//        federatedIdentityConfigurer.authorizationRequestUri("http://127.0.0.1:8085/themall-oauthserver/oauth2/authorization/{registrationId}");
        http
                .cors().disable()
                .csrf().disable()
                .authorizeRequests(authorizeRequests ->
                        authorizeRequests
                                .mvcMatchers("/assets/**", "/webjars/**", "/login").permitAll()
                                .anyRequest().authenticated()
                )
                .oauth2ResourceServer((oauth2) ->
                        oauth2.jwt(Customizer.withDefaults()
                        ))
                .formLogin(Customizer.withDefaults())
                .apply(federatedIdentityConfigurer)
                .and()
                .logout(Customizer.withDefaults())
        ;
        return http.build();
    }

    @Bean
    public UserDetailsService userDetailsService() {
        return new SysUserDetailsManager();
    }

    @Bean
    public  PasswordEncoder passwordEncoder(){
        return PasswordEncoderFactories.createDelegatingPasswordEncoder();
    }

    @Bean
    public ClientRegistrationRepository clientRegistrationRepository() {
        return new InMemoryClientRegistrationRepository(this.githubClientRegistration(),this.googleClientRegistration());
    }

    @Bean
    public OAuth2AuthorizedClientService authorizedClientService(JdbcTemplate jdbcTemplate,
            ClientRegistrationRepository clientRegistrationRepository) {
        return new JdbcOAuth2AuthorizedClientService(jdbcTemplate,clientRegistrationRepository);
    }

    @Bean
    public OAuth2AuthorizedClientRepository authorizedClientRepository(
            OAuth2AuthorizedClientService authorizedClientService) {
        return new AuthenticatedPrincipalOAuth2AuthorizedClientRepository(authorizedClientService);
    }

    private ClientRegistration githubClientRegistration() {
        String clientSecret = oAuth2ResourceServerProperties.getJwt().getIssuerUri()+"/login/oauth2/code/{registrationId}" ;
        return CommonOAuth2Provider.GITHUB
                .getBuilder("github")
                .clientId("162f2f2ef75cc236d6f1")
                .clientSecret("d43ff1248dad544c5c61f9b48642551e0e00f668")
//                .redirectUri("https://auth.poo0054.top/login/oauth2/code/{registrationId}")
                .redirectUri(clientSecret)
               .build();
    }
    private ClientRegistration googleClientRegistration() {
        String clientSecret = oAuth2ResourceServerProperties.getJwt().getIssuerUri()+"/login/oauth2/code/{registrationId}" ;
        return CommonOAuth2Provider.GOOGLE
                .getBuilder("google")
                .clientId("237327413162-542qg7pjo4esi81mbuo9s27lrd6rp9i5.apps.googleusercontent.com")
                .clientSecret("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")
//                .redirectUri("https://auth.poo0054.top/login/oauth2/code/{registrationId}")
                .redirectUri(clientSecret)
               .build();
    }

}