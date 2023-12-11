package com.themall.oauthserver.config;

import com.themall.oauthserver.security.FederatedIdentityConfigurer;
import com.themall.oauthserver.security.UserRepositoryOAuth2UserHandler;
import com.themall.oauthserver.userdetails.SysUserDetailsManager;
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
public class SecurityConfig {


    // @formatter:off
    @Bean
    public SecurityFilterChain defaultSecurityFilterChain(HttpSecurity http) throws Exception {
        FederatedIdentityConfigurer federatedIdentityConfigurer = new FederatedIdentityConfigurer()
                .oauth2UserHandler(new UserRepositoryOAuth2UserHandler());

        http
                .authorizeRequests(authorizeRequests ->
                        authorizeRequests
                                .mvcMatchers("/assets/**", "/webjars/**", "/login").permitAll()
                                .anyRequest().authenticated()
                )
                .formLogin(Customizer.withDefaults())
//                .logout(Customizer.withDefaults())
                .apply(federatedIdentityConfigurer)
                .and().cors().disable()
                .csrf().disable()
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
        return new InMemoryClientRegistrationRepository(this.githubClientRegistration());
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
        return CommonOAuth2Provider.GITHUB.getBuilder("github")
                .clientId("162f2f2ef75cc236d6f1").clientSecret("d43ff1248dad544c5c61f9b48642551e0e00f668")
                .redirectUri("http://127.0.0.1:9000/login/oauth2/code/github").build();
    }


}