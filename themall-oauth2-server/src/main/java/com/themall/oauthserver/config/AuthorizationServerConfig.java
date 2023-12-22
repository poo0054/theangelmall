package com.themall.oauthserver.config;

import com.nimbusds.jose.jwk.JWKSet;
import com.nimbusds.jose.jwk.RSAKey;
import com.nimbusds.jose.jwk.source.ImmutableJWKSet;
import com.nimbusds.jose.jwk.source.JWKSource;
import com.nimbusds.jose.proc.SecurityContext;
import com.themall.common.utils.KeyUtils;
import com.themall.oauthserver.security.FederatedIdentityConfigurer;
import com.themall.oauthserver.security.FederatedIdentityIdTokenCustomizer;
import com.themall.oauthserver.service.SysUserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.security.oauth2.resource.OAuth2ResourceServerProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.oauth2.core.AuthorizationGrantType;
import org.springframework.security.oauth2.core.ClientAuthenticationMethod;
import org.springframework.security.oauth2.core.oidc.OidcScopes;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.server.authorization.JdbcOAuth2AuthorizationConsentService;
import org.springframework.security.oauth2.server.authorization.JdbcOAuth2AuthorizationService;
import org.springframework.security.oauth2.server.authorization.OAuth2AuthorizationConsentService;
import org.springframework.security.oauth2.server.authorization.OAuth2AuthorizationService;
import org.springframework.security.oauth2.server.authorization.client.JdbcRegisteredClientRepository;
import org.springframework.security.oauth2.server.authorization.client.RegisteredClient;
import org.springframework.security.oauth2.server.authorization.client.RegisteredClientRepository;
import org.springframework.security.oauth2.server.authorization.config.annotation.web.configuration.OAuth2AuthorizationServerConfiguration;
import org.springframework.security.oauth2.server.authorization.config.annotation.web.configurers.OAuth2AuthorizationServerConfigurer;
import org.springframework.security.oauth2.server.authorization.settings.AuthorizationServerSettings;
import org.springframework.security.oauth2.server.authorization.settings.ClientSettings;
import org.springframework.security.oauth2.server.authorization.settings.TokenSettings;
import org.springframework.security.oauth2.server.authorization.token.JwtEncodingContext;
import org.springframework.security.oauth2.server.authorization.token.OAuth2TokenCustomizer;
import org.springframework.security.web.SecurityFilterChain;

import java.security.interfaces.RSAPublicKey;
import java.time.Duration;
import java.util.UUID;

/**
 * @author poo0054
 */
@Slf4j
@Configuration(proxyBeanMethods = false)
@EnableConfigurationProperties(OAuth2ResourceServerProperties.class)
public class AuthorizationServerConfig {

    @Value("${jwt.private}")
    String privateKey;

    @Value("${jwt.public}")
    String publicKey;

    OAuth2ResourceServerProperties oAuth2ResourceServerProperties;

    @Autowired
    public void setoAuth2ResourceServerProperties(OAuth2ResourceServerProperties oAuth2ResourceServerProperties) {
        this.oAuth2ResourceServerProperties = oAuth2ResourceServerProperties;
    }

    @Bean
    @Order(Ordered.HIGHEST_PRECEDENCE)
    public SecurityFilterChain authorizationServerSecurityFilterChain(HttpSecurity http) throws Exception {
        OAuth2AuthorizationServerConfiguration.applyDefaultSecurity(http);
        http.getConfigurer(OAuth2AuthorizationServerConfigurer.class)
                .oidc(Customizer.withDefaults());    // Enable OpenID Connect 1.0
        http.apply(new FederatedIdentityConfigurer());
        return http.build();
    }

    @Bean
    public OAuth2TokenCustomizer<JwtEncodingContext> idTokenCustomizer(SysUserService userService) {
        return new FederatedIdentityIdTokenCustomizer(userService);
    }


    @Bean
    public OAuth2AuthorizationService oAuth2AuthorizationService(JdbcTemplate jdbcTemplate,
                                                                 RegisteredClientRepository registeredClientRepository) {
        return new JdbcOAuth2AuthorizationService(jdbcTemplate, registeredClientRepository);
    }

    @Bean
    public OAuth2AuthorizationConsentService authorizationConsentService(JdbcTemplate jdbcTemplate,
                                                                         RegisteredClientRepository registeredClientRepository) {
        return new JdbcOAuth2AuthorizationConsentService(jdbcTemplate, registeredClientRepository);
    }

    private static RegisteredClient getRegisteredClient() {
        TokenSettings tokenSettings = TokenSettings.builder().accessTokenTimeToLive(Duration.ofDays(7))
                .refreshTokenTimeToLive(Duration.ofDays(15)).build();
        return RegisteredClient.withId(UUID.randomUUID().toString())
                .clientAuthenticationMethod(ClientAuthenticationMethod.CLIENT_SECRET_BASIC)
                .authorizationGrantType(AuthorizationGrantType.AUTHORIZATION_CODE)
                .authorizationGrantType(AuthorizationGrantType.REFRESH_TOKEN)
                .authorizationGrantType(AuthorizationGrantType.CLIENT_CREDENTIALS)
                .redirectUri("https://poo0054.top/renren-fast/sys/authorized")
                .redirectUri("http://127.0.0.1:8085/renren-fast/sys/authorized")
                .scope(OidcScopes.OPENID)
                .scope(OidcScopes.PROFILE)
                .scope("themall")
                .clientSettings(ClientSettings.builder().requireAuthorizationConsent(true).build())
                .tokenSettings(tokenSettings)
                .build();
    }


    @Bean
    public AuthorizationServerSettings authorizationServerSettings() {
        return AuthorizationServerSettings.builder()
                .issuer(oAuth2ResourceServerProperties.getJwt().getIssuerUri())
                .build();
    }

    @Bean
    public JWKSource<SecurityContext> jwkSource() {
        RSAKey rsaKey =
                new RSAKey.Builder((RSAPublicKey) KeyUtils.getPublicKey(publicKey)).privateKey(KeyUtils.getPrivateKey(privateKey)).keyID("b5da22e6-4a58-4c71-83cf-b717f41f89d1").build();
        JWKSet jwkSet = new JWKSet(rsaKey);
        return new ImmutableJWKSet<>(jwkSet);
    }

    @Bean
    public JwtDecoder jwtDecoder(JWKSource<SecurityContext> jwkSource) {
        return OAuth2AuthorizationServerConfiguration.jwtDecoder(jwkSource);
    }

    @Bean
    public RegisteredClientRepository registeredClientRepository(JdbcTemplate jdbcTemplate) {
        getRegisteredClient();
        // Save registered client in db as if in-memory
        JdbcRegisteredClientRepository registeredClientRepository = new JdbcRegisteredClientRepository(jdbcTemplate);
        return registeredClientRepository;
    }
}
