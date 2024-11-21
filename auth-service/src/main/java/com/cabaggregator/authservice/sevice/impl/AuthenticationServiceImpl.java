package com.cabaggregator.authservice.sevice.impl;

import com.cabaggregator.authservice.core.constant.ApplicationMessages;
import com.cabaggregator.authservice.core.dto.UserRegisterDto;
import com.cabaggregator.authservice.core.enums.KeycloakRole;
import com.cabaggregator.authservice.core.enums.UserRole;
import com.cabaggregator.authservice.exception.BadRequestException;
import com.cabaggregator.authservice.sevice.AuthenticationService;
import com.cabaggregator.authservice.sevice.KeycloakService;
import com.cabaggregator.authservice.sevice.RoleService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthenticationServiceImpl implements AuthenticationService {
    private final KeycloakService keyCloakService;

    private final RoleService roleService;

    @Override
    public void registerUser(UserRegisterDto userRegisterDto) {
        UserRole userRole = userRegisterDto.role();

        switch (userRole) {
            case UserRole.DRIVER -> createKeyCloakUser(userRegisterDto, KeycloakRole.PASSENGER);
            case UserRole.PASSENGER -> createKeyCloakUser(userRegisterDto, KeycloakRole.DRIVER);
            default -> throw new BadRequestException(
                    ApplicationMessages.REGISTER_USER_WITH_ROLE_PROHIBITED,
                    userRegisterDto.role().getValue());
        }
    }

    private void createKeyCloakUser(UserRegisterDto userRegisterDto, KeycloakRole role) {
        String userId = keyCloakService.createUser(userRegisterDto).getId();
        roleService.assignRoleToUser(
                role,
                keyCloakService.findUserResourceByUserId(userId));
    }
}
