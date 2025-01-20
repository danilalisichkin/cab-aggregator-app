package com.cabaggregator.passengerservice.security.util;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.UUID;

@Component
public final class SecurityUtil {
    private static Authentication getAuthentication() {
        return SecurityContextHolder
                .getContext()
                .getAuthentication();
    }

    public UUID getUserIdFromSecurityContext() {
        return (UUID) getAuthentication()
                .getPrincipal();
    }

    public List<String> getUserAuthoritiesFromSecurityContext() {
        return getAuthentication()
                .getAuthorities()
                .stream()
                .map(GrantedAuthority::getAuthority)
                .toList();
    }
}
