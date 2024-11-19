package com.cabaggregator.authservice.sevice;

import com.cabaggregator.authservice.core.dto.UserLoginDto;
import com.cabaggregator.authservice.core.dto.UserRegisterDto;
import org.keycloak.admin.client.resource.UserResource;
import org.keycloak.representations.AccessTokenResponse;
import org.keycloak.representations.idm.UserRepresentation;

public interface KeyCloakService {
    UserRepresentation createUser(UserRegisterDto userRegisterDto);

    AccessTokenResponse getAccessToken(UserLoginDto userLoginDto);

    AccessTokenResponse refreshAccessToken(String refreshToken);

    UserResource findUserResourceByUserId(String userId);

    UserRepresentation findUserRepresentationByUserId(String userId);

    void deleteUser(String userId);

    void sendVerificationEmail(String userId);
}
