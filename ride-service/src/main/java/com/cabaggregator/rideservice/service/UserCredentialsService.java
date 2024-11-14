package com.cabaggregator.rideservice.service;

import com.cabaggregator.rideservice.core.enums.UserRole;

import java.util.Optional;

public interface UserCredentialsService {
    Optional<UserRole> getUserRole(String accessToken);
    String getUserId(String accessToken);
}
