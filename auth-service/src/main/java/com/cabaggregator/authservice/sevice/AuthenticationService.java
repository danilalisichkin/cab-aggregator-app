package com.cabaggregator.authservice.sevice;

import com.cabaggregator.authservice.core.dto.KeycloakAccessTokenDto;
import com.cabaggregator.authservice.core.dto.UserLoginDto;
import com.cabaggregator.authservice.core.dto.UserRegisterDto;

public interface AuthenticationService {
    void registerUser(UserRegisterDto userRegisterDto);

    KeycloakAccessTokenDto loginUser(UserLoginDto userLoginDto);

    KeycloakAccessTokenDto refreshUserAccessToken(String refreshToken);
}
