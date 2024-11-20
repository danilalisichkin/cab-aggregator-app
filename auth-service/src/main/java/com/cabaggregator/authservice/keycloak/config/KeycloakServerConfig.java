package com.cabaggregator.authservice.keycloak.config;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Getter
@RequiredArgsConstructor
@ConfigurationProperties(prefix = "app.keycloak")
public class KeycloakServerConfig {
    private final String serverUrl;
    private final String realm;
    private final String grantType;
}
