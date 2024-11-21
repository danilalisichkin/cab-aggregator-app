package com.cabaggregator.rideservice.service.impl;

import com.cabaggregator.rideservice.core.enums.UserRole;
import com.cabaggregator.rideservice.service.UserCredentialsService;
import com.cabaggregator.rideservice.util.JwtClaimsExtractor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserCredentialsServiceImpl implements UserCredentialsService {
    @Override
    public Optional<UserRole> getUserRole(String accessToken) {
        List<UserRole> roles = JwtClaimsExtractor.extractUserRoles(accessToken);

        if (roles.size() > 1) {
            throw new RuntimeException("User with id=" + getUserId(accessToken) + " has multiple roles.");
        }

        return roles.isEmpty() ? Optional.empty() : Optional.of(roles.getFirst());
    }

    @Override
    public String getUserId(String accessToken) {
        return JwtClaimsExtractor.extractUserId(accessToken);
    }
}
