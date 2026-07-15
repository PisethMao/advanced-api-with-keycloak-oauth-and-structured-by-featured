package com.co.istad.piseth.spring_web_mvc.security;

import lombok.RequiredArgsConstructor;
import org.keycloak.OAuth2Constants;
import org.keycloak.admin.client.Keycloak;
import org.keycloak.admin.client.KeycloakBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@RequiredArgsConstructor
public class KeycloakAdminClientConfig {
    private final KeycloakProperties keycloakProperties;

    @Bean
    public Keycloak configureAdminClient() {
        IO.println("Keycloak Properties: " + keycloakProperties);
        return KeycloakBuilder.builder()
                .grantType(OAuth2Constants.CLIENT_CREDENTIALS)
                .serverUrl(keycloakProperties.getServerUrl())
                .realm(keycloakProperties.getRealm())
                .clientId(keycloakProperties.getClientId())
                .clientSecret(keycloakProperties.getClientSecret())
                .build();
    }
}
