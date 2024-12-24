package com.cabaggregator.authservice.sevice.impl;

import com.cabaggregator.authservice.core.constant.ApplicationMessages;
import com.cabaggregator.authservice.core.dto.KeycloakAccessTokenDto;
import com.cabaggregator.authservice.core.dto.UserLoginDto;
import com.cabaggregator.authservice.core.dto.UserRegisterDto;
import com.cabaggregator.authservice.core.enums.KeycloakRole;
import com.cabaggregator.authservice.core.enums.UserRole;
import com.cabaggregator.authservice.core.mapper.KeycloakAccessTokenMapper;
import com.cabaggregator.authservice.core.mapper.UserMapper;
import com.cabaggregator.authservice.exception.BadRequestException;
import com.cabaggregator.authservice.kafka.dto.DriverAddingDto;
import com.cabaggregator.authservice.kafka.dto.PassengerAddingDto;
import com.cabaggregator.authservice.kafka.producer.DriverProducer;
import com.cabaggregator.authservice.kafka.producer.PassengerProducer;
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

    private final DriverProducer driverProducer;
    private final PassengerProducer passengerProducer;

    private final KeycloakAccessTokenMapper accessTokenMapper;
    private final UserMapper userMapper;

    @Override
    public void registerUser(UserRegisterDto userRegisterDto) {
        UserRole userRole = userRegisterDto.role();

        UserRepresentation createdUser;
        switch (userRole) {
            case UserRole.DRIVER:
                createdUser = createKeycloakUser(userRegisterDto, KeycloakRole.DRIVER);
                DriverAddingDto driverToCreate = userMapper.userToDriver(userRegisterDto);
                driverToCreate.setId(createdUser.getId());
                driverProducer.sendMessage(driverToCreate);
                break;

            case UserRole.PASSENGER:
                createdUser = createKeycloakUser(userRegisterDto, KeycloakRole.PASSENGER);
                PassengerAddingDto passengerToCreate = userMapper.userToPassenger(userRegisterDto);
                passengerToCreate.setId(createdUser.getId());
                passengerProducer.sendMessage(passengerToCreate);
                break;

            default:
                throw new BadRequestException(
                        ApplicationMessages.REGISTER_USER_WITH_ROLE_PROHIBITED,
                        userRole.getValue());
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

    private UserRepresentation createKeycloakUser(UserRegisterDto userRegisterDto, KeycloakRole role) {
        UserRepresentation user = kcResourceService.createUser(
                userRegisterDto.phoneNumber(),
                userRegisterDto.email(),
                userRegisterDto.firstName(),
                userRegisterDto.lastName(),
                userRegisterDto.password());

        UserResource userResource = kcResourceService.findUserResourceByUserId(user.getId());

        kcRoleService.assignRoleToUser(role, userResource);

        return user;
    }
}
