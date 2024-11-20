package com.cabaggregator.authservice.keycloak.config;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Getter
@RequiredArgsConstructor
@ConfigurationProperties(prefix = "app.keycloak.client")
public class KeycloakClientConfig {
    private final String clientId;
    private final String clientSecret;
}
