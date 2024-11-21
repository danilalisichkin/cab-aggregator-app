package com.cabaggregator.authservice.sevice;

import com.cabaggregator.authservice.core.dto.UserRegisterDto;

public interface AuthenticationService {
    void registerUser(UserRegisterDto userRegisterDto);
}
