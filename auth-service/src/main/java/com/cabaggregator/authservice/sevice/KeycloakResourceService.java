package com.cabaggregator.authservice.sevice;

import org.keycloak.admin.client.resource.UserResource;
import org.keycloak.representations.AccessTokenResponse;
import org.keycloak.representations.idm.UserRepresentation;

public interface KeycloakResourceService {
    UserRepresentation createUser(
            String username, String email, String firstName, String lastName, String password);

    AccessTokenResponse getUserAccessToken(String username, String password);

    AccessTokenResponse refreshUserAccessToken(String refreshToken);

    UserResource findUserResourceByUserId(String userId);

    UserRepresentation findUserRepresentationByUserId(String userId);

    void deleteUser(String userId);

    void sendVerificationEmail(String userId);
}
