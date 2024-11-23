package com.cabaggregator.authservice.sevice.impl;

import com.cabaggregator.authservice.core.constant.ApplicationMessages;
import com.cabaggregator.authservice.core.dto.KeycloakAccessTokenDto;
import com.cabaggregator.authservice.core.dto.UserLoginDto;
import com.cabaggregator.authservice.core.dto.UserRegisterDto;
import com.cabaggregator.authservice.core.enums.KeycloakRole;
import com.cabaggregator.authservice.core.enums.UserRole;
import com.cabaggregator.authservice.core.mapper.KeycloakAccessTokenMapper;
import com.cabaggregator.authservice.exception.BadRequestException;
import com.cabaggregator.authservice.sevice.AuthenticationService;
import com.cabaggregator.authservice.sevice.KeycloakResourceService;
import com.cabaggregator.authservice.sevice.KeycloakRoleService;
import lombok.RequiredArgsConstructor;
import org.keycloak.admin.client.resource.UserResource;
import org.keycloak.representations.AccessTokenResponse;
import org.keycloak.representations.idm.UserRepresentation;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthenticationServiceImpl implements AuthenticationService {
    private final KeycloakResourceService kcResourceService;
    private final KeycloakRoleService kcRoleService;

    private final KeycloakAccessTokenMapper accessTokenMapper;

    @Override
    public void registerUser(UserRegisterDto userRegisterDto) {
        UserRole userRole = userRegisterDto.role();

        switch (userRole) {
            case UserRole.DRIVER -> createKeycloakUser(userRegisterDto, KeycloakRole.DRIVER);
            case UserRole.PASSENGER -> createKeycloakUser(userRegisterDto, KeycloakRole.PASSENGER);
            default -> throw new BadRequestException(
                    ApplicationMessages.REGISTER_USER_WITH_ROLE_PROHIBITED,
                    userRegisterDto.role().getValue());
        }
    }

    @Override
    public KeycloakAccessTokenDto loginUser(UserLoginDto userLoginDto) {
        AccessTokenResponse token = kcResourceService.getUserAccessToken(
                userLoginDto.identifier(),
                userLoginDto.password());

        return accessTokenMapper.tokenToDto(token);
    }

    @Override
    public KeycloakAccessTokenDto refreshUserAccessToken(String refreshToken) {
        AccessTokenResponse token = kcResourceService.refreshUserAccessToken(refreshToken);

        return accessTokenMapper.tokenToDto(token);
    }

    private void createKeycloakUser(UserRegisterDto userRegisterDto, KeycloakRole role) {
        UserRepresentation user = kcResourceService.createUser(
                userRegisterDto.phoneNumber(),
                userRegisterDto.email(),
                userRegisterDto.firstName(),
                userRegisterDto.lastName(),
                userRegisterDto.password());

        UserResource userResource = kcResourceService.findUserResourceByUserId(user.getId());

        kcRoleService.assignRoleToUser(role, userResource);
    }
}
