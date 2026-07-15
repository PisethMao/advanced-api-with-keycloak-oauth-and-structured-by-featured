package com.co.istad.piseth.spring_web_mvc.security;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties(prefix = "keycloak")
@Getter
@Setter
@ToString
public class KeycloakProperties {
    private String clientId;
    private String clientSecret;
    private String realm;
    private String serverUrl;
}
