package com.cabaggregator.authservice.keycloak.config;

import lombok.RequiredArgsConstructor;
import org.keycloak.admin.client.Keycloak;
import org.keycloak.admin.client.KeycloakBuilder;
import org.keycloak.admin.client.resource.GroupsResource;
import org.keycloak.admin.client.resource.RolesResource;
import org.keycloak.admin.client.resource.UsersResource;
import org.keycloak.representations.idm.CredentialRepresentation;
import org.keycloak.representations.idm.UserRepresentation;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.oauth2.server.resource.authentication.JwtGrantedAuthoritiesConverter;

@Configuration
@RequiredArgsConstructor
public class KeycloakResourceConfig {
    private final KeycloakServerConfig kcServerConfig;
    private final KeycloakAdminConfig kcAdminConfig;

    @Bean
    public Keycloak keycloak() {
        return KeycloakBuilder.builder()
                .serverUrl(kcServerConfig.getServerUrl())
                .realm(kcServerConfig.getRealm())
                .clientId(kcAdminConfig.getClientId())
                .clientSecret(kcAdminConfig.getClientSecret())
                .grantType(kcServerConfig.getGrantType())
                .build();
    }

    @Bean
    public UserRepresentation userRepresentation() {
        return new UserRepresentation();
    }

    @Bean
    public UsersResource usersResource(Keycloak keycloak) {
        return keycloak.realm(kcServerConfig.getRealm()).users();
    }

    @Bean
    public CredentialRepresentation credentialRepresentation() {
        return new CredentialRepresentation();
    }

    @Bean
    public RolesResource rolesResource(Keycloak keycloak) {
        return keycloak.realm(kcServerConfig.getRealm()).roles();
    }

    @Bean
    public GroupsResource groupsResource(Keycloak keycloak) {
        return keycloak.realm(kcServerConfig.getRealm()).groups();
    }

    @Bean
    JwtGrantedAuthoritiesConverter jwtGrantedAuthoritiesConverter() {
        return new JwtGrantedAuthoritiesConverter();
    }
}