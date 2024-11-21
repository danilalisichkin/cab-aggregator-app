package com.cabaggregator.authservice.keycloak.config;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Getter
@RequiredArgsConstructor
@ConfigurationProperties(prefix = "app.keycloak.admin")
public class KeycloakAdminConfig {
    private final String clientId;
    private final String clientSecret;
}
