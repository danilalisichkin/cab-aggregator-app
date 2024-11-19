package com.cabaggregator.authservice.sevice.impl;

import com.cabaggregator.authservice.core.constant.ApplicationMessages;
import com.cabaggregator.authservice.core.dto.UserLoginDto;
import com.cabaggregator.authservice.core.dto.UserRegisterDto;
import com.cabaggregator.authservice.exception.ResourceNotFoundException;
import com.cabaggregator.authservice.exception.UnauthorizedException;
import com.cabaggregator.authservice.sevice.KeyCloakService;
import com.cabaggregator.authservice.util.KeyCloakResponseValidator;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.ws.rs.NotAuthorizedException;
import jakarta.ws.rs.core.Response;
import lombok.extern.slf4j.Slf4j;
import org.keycloak.OAuth2Constants;
import org.keycloak.admin.client.Keycloak;
import org.keycloak.admin.client.KeycloakBuilder;
import org.keycloak.admin.client.resource.UserResource;
import org.keycloak.admin.client.resource.UsersResource;
import org.keycloak.representations.AccessTokenResponse;
import org.keycloak.representations.idm.CredentialRepresentation;
import org.keycloak.representations.idm.UserRepresentation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
public class KeyCloakServiceImpl implements KeyCloakService {
    private final UsersResource usersResource;
    private final UserRepresentation userRepresentation;
    private final CredentialRepresentation credentialRepresentation;

    private final String realm;
    private final String serverUrl;
    private final String clientId;
    private final String clientSecret;

    @Autowired
    public KeyCloakServiceImpl(
            UsersResource usersResource,
            UserRepresentation userRepresentation,
            CredentialRepresentation credentialRepresentation, ObjectMapper objectMapper,
            @Value("${app.keycloak.realm}")
            String realm,
            @Value("${app.keycloak.server-url}")
            String serverUrl,
            @Value("${app.keycloak.user.client-id}")
            String clientId,
            @Value("${app.keycloak.user.client-secret}")
            String clientSecret) {
        this.usersResource = usersResource;
        this.userRepresentation = userRepresentation;
        this.credentialRepresentation = credentialRepresentation;
        this.realm = realm;
        this.serverUrl = serverUrl;
        this.clientId = clientId;
        this.clientSecret = clientSecret;
    }

    @Override
    public UserRepresentation createUser(UserRegisterDto userRegisterDto) {
        buildUserRepresentation(userRegisterDto);
        Response response = usersResource.create(userRepresentation);
        KeyCloakResponseValidator.validate(response);
        List<UserRepresentation> users = usersResource.search(userRegisterDto.phoneNumber());
        UserRepresentation createdUser = users.getFirst();
        sendVerificationEmail(createdUser.getId());
        return createdUser;
    }

    @Override
    public AccessTokenResponse getAccessToken(UserLoginDto userLoginDto) {
        Keycloak keycloakUser = KeycloakBuilder.builder()
                .serverUrl(serverUrl)
                .realm(realm)
                .grantType(OAuth2Constants.PASSWORD)
                .clientId(clientId)
                .clientSecret(clientSecret)
                .username(userLoginDto.identifier())
                .password(userLoginDto.password())
                .build();
        try {
            return keycloakUser.tokenManager().getAccessToken();
        } catch (NotAuthorizedException e) {
            throw new UnauthorizedException(ApplicationMessages.WRONG_LOGIN_OR_PASSWORD);
        }
    }

    @Override
    public AccessTokenResponse refreshAccessToken(String refreshToken) {
        // TODO: implement, using Feign?

        return null;
    }

    @Override
    public UserResource findUserResourceByUserId(String userId) {
        return usersResource.get(userId);
    }

    @Override
    public UserRepresentation findUserRepresentationByUserId(String userId) {
        try {
            return findUserResourceByUserId(userId)
                    .toRepresentation();
        } catch (Exception e) {
            throw new ResourceNotFoundException(ApplicationMessages.USER_WITH_ID_NOT_FOUND, userId);
        }
    }

    @Override
    public void deleteUser(String userId) {
        Response response = usersResource.delete(userId);
        KeyCloakResponseValidator.validate(response);
    }

    @Override
    public void sendVerificationEmail(String userId) {
        usersResource.get(userId).sendVerifyEmail();
    }

    private void buildUserRepresentation(UserRegisterDto userRegisterDto) {
        userRepresentation.setEnabled(true);
        userRepresentation.setUsername(userRegisterDto.phoneNumber());
        userRepresentation.setEmail(userRegisterDto.email());
        userRepresentation.setEmailVerified(false);
        userRepresentation.setFirstName(userRegisterDto.firstName());
        userRepresentation.setLastName(userRegisterDto.lastName());
        userRepresentation.setCreatedTimestamp(System.currentTimeMillis());
        credentialRepresentation.setValue(userRegisterDto.password());
        credentialRepresentation.setType(CredentialRepresentation.PASSWORD);
        credentialRepresentation.setTemporary(false);
        userRepresentation.setCredentials(List.of(credentialRepresentation));
    }
}
