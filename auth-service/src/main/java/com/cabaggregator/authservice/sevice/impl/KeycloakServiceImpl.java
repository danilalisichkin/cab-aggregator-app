package com.cabaggregator.authservice.sevice.impl;

import com.cabaggregator.authservice.core.constant.ApplicationMessages;
import com.cabaggregator.authservice.core.dto.KeycloakAccessTokenDto;
import com.cabaggregator.authservice.core.dto.UserLoginDto;
import com.cabaggregator.authservice.core.dto.UserRegisterDto;
import com.cabaggregator.authservice.core.mapper.KeycloakAccessTokenMapper;
import com.cabaggregator.authservice.exception.ResourceNotFoundException;
import com.cabaggregator.authservice.exception.UnauthorizedException;
import com.cabaggregator.authservice.keycloak.config.KeycloakClientConfig;
import com.cabaggregator.authservice.keycloak.config.KeycloakServerConfig;
import com.cabaggregator.authservice.sevice.KeycloakService;
import com.cabaggregator.authservice.keycloak.util.KeycloakResponseValidator;
import jakarta.ws.rs.NotAuthorizedException;
import jakarta.ws.rs.core.Response;
import lombok.RequiredArgsConstructor;
import org.keycloak.OAuth2Constants;
import org.keycloak.admin.client.Keycloak;
import org.keycloak.admin.client.KeycloakBuilder;
import org.keycloak.admin.client.resource.UserResource;
import org.keycloak.admin.client.resource.UsersResource;
import org.keycloak.representations.AccessTokenResponse;
import org.keycloak.representations.idm.CredentialRepresentation;
import org.keycloak.representations.idm.UserRepresentation;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class KeycloakServiceImpl implements KeycloakService {
    private final KeycloakServerConfig kcServerConfig;
    private final KeycloakClientConfig kcClientConfig;

    private final UsersResource usersResource;
    private final UserRepresentation userRepresentation;
    private final CredentialRepresentation credentialRepresentation;

    private final KeycloakAccessTokenMapper accessTokenMapper;

    @Override
    public UserRepresentation createUser(UserRegisterDto userRegisterDto) {
        buildUserRepresentation(userRegisterDto);
        Response response = usersResource.create(userRepresentation);
        KeycloakResponseValidator.validate(response);
        List<UserRepresentation> users = usersResource.search(userRegisterDto.phoneNumber());
        UserRepresentation createdUser = users.getFirst();
        sendVerificationEmail(createdUser.getId());
        return createdUser;
    }

    @Override
    public KeycloakAccessTokenDto getAccessToken(UserLoginDto userLoginDto) {
        Keycloak keycloakUser = KeycloakBuilder.builder()
                .serverUrl(kcServerConfig.getServerUrl())
                .realm(kcServerConfig.getRealm())
                .grantType(OAuth2Constants.PASSWORD)
                .clientId(kcClientConfig.getClientId())
                .clientSecret(kcClientConfig.getClientSecret())
                .username(userLoginDto.identifier())
                .password(userLoginDto.password())
                .build();
        try {
            AccessTokenResponse accessToken = keycloakUser.tokenManager().getAccessToken();

            return accessTokenMapper.tokenToDto(accessToken);
        } catch (NotAuthorizedException e) {
            throw new UnauthorizedException(ApplicationMessages.WRONG_LOGIN_OR_PASSWORD);
        }
    }

    @Override
    public KeycloakAccessTokenDto refreshAccessToken(String refreshToken) {
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
        KeycloakResponseValidator.validate(response);
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
