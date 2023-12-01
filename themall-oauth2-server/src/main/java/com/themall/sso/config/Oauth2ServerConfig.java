package com.themall.sso.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.security.oauth2.core.AuthorizationGrantType;
import org.springframework.security.oauth2.core.ClientAuthenticationMethod;
import org.springframework.security.oauth2.core.oidc.OidcScopes;
import org.springframework.security.oauth2.server.authorization.client.JdbcRegisteredClientRepository;
import org.springframework.security.oauth2.server.authorization.client.RegisteredClient;
import org.springframework.security.oauth2.server.authorization.client.RegisteredClientRepository;
import org.springframework.security.oauth2.server.authorization.settings.ClientSettings;

import java.util.UUID;

/**
 * @author poo0054
 */
@Configuration
public class Oauth2ServerConfig {

    @Bean
    public RegisteredClientRepository registeredClientRepository(JdbcTemplate jdbcTemplate) {
        RegisteredClient registeredClient =
                RegisteredClient.withId(UUID.randomUUID().toString()).clientId("message-client")
                        .clientSecret("{noop}secret").clientAuthenticationMethod(ClientAuthenticationMethod.CLIENT_SECRET_BASIC)
                        .authorizationGrantType(AuthorizationGrantType.AUTHORIZATION_CODE)
                        .authorizationGrantType(AuthorizationGrantType.REFRESH_TOKEN)
                        .authorizationGrantType(AuthorizationGrantType.CLIENT_CREDENTIALS)
                        .redirectUri("http://127.0.0.1:8080/login/oauth2/code/messaging-client-oidc")
                        .redirectUri("http://127.0.0.1:8080/test").scope(OidcScopes.OPENID).scope(OidcScopes.PROFILE)
                        .scope("message.read").scope("message.write")
                        .clientSettings(ClientSettings.builder().requireAuthorizationConsent(true).build()).build();
        JdbcRegisteredClientRepository jdbcRegisteredClientRepository =

                new JdbcRegisteredClientRepository(jdbcTemplate);
        return jdbcRegisteredClientRepository;
    }
}
