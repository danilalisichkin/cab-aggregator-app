package com.cabaggregator.ratingservice.security.util;

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

    /**
     * Extracts user id from Authentication object.
     **/
    public UUID getUserIdFromSecurityContext() {
        return (UUID) getAuthentication()
                .getPrincipal();
    }

    /**
     * Extracts user authorities from Authentication object.
     **/
    public List<String> getUserAuthoritiesFromSecurityContext() {
        return getAuthentication()
                .getAuthorities()
                .stream()
                .map(GrantedAuthority::getAuthority)
                .toList();
    }
}
