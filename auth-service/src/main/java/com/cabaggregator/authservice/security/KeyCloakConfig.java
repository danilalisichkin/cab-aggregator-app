package com.cabaggregator.authservice.security;

import org.keycloak.admin.client.Keycloak;
import org.keycloak.admin.client.KeycloakBuilder;
import org.keycloak.admin.client.resource.GroupsResource;
import org.keycloak.admin.client.resource.RolesResource;
import org.keycloak.admin.client.resource.UsersResource;
import org.keycloak.representations.idm.CredentialRepresentation;
import org.keycloak.representations.idm.UserRepresentation;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.oauth2.server.resource.authentication.JwtGrantedAuthoritiesConverter;

@Configuration
public class KeyCloakConfig {
    private final String clientId;
    private final String clientSecret;
    private final String realm;
    private final String serverUrl;
    private final String grantType;

    public KeyCloakConfig(
            @Value("${app.keycloak.admin.client-id}")
            String clientId,
            @Value("${app.keycloak.admin.client-secret}")
            String clientSecret,
            @Value("${app.keycloak.realm}")
            String realm,
            @Value("${app.keycloak.server-url}")
            String serverUrl,
            @Value("${app.keycloak.grant-type}")
            String grantType) {

        this.clientId = clientId;
        this.clientSecret = clientSecret;
        this.realm = realm;
        this.serverUrl = serverUrl;
        this.grantType = grantType;
    }

    @Bean
    public Keycloak keycloak() {
        return KeycloakBuilder.builder()
                .serverUrl(serverUrl)
                .realm(realm)
                .clientId(clientId)
                .clientSecret(clientSecret)
                .grantType(grantType)
                .build();
    }

    @Bean
    public UserRepresentation userRepresentation() {
        return new UserRepresentation();
    }

    @Bean
    public UsersResource usersResource(Keycloak keycloak) {
        return keycloak.realm(realm).users();
    }

    @Bean
    public CredentialRepresentation credentialRepresentation() {
        return new CredentialRepresentation();
    }

    @Bean
    public RolesResource rolesResource(Keycloak keycloak) {
        return keycloak.realm(realm).roles();
    }

    @Bean
    public GroupsResource groupsResource(Keycloak keycloak) {
        return keycloak.realm(realm).groups();
    }

    @Bean
    JwtGrantedAuthoritiesConverter jwtGrantedAuthoritiesConverter() {
        return new JwtGrantedAuthoritiesConverter();
    }
}
