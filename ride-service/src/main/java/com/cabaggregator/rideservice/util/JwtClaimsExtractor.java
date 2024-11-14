package com.cabaggregator.rideservice.util;

import com.cabaggregator.rideservice.core.enums.UserRole;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import lombok.experimental.UtilityClass;

import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

@UtilityClass
public class JwtClaimsExtractor {
    private Claims extractClaims(String token) {
        token = token.replace("Bearer ", "");

        return Jwts.parser()
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    public String extractUserId(String token) {
        Claims claims = extractClaims(token);
        return claims.get("userId", String.class);
    }

    public List<UserRole> extractUserRoles(String token) {
        Claims claims = extractClaims(token);
        Map<String, Object> realmAccess = claims.get("realm_access", Map.class);
        List<String> roles = (List<String>) realmAccess.get("roles");

        return roles.stream()
                .map(UserRole::fromValue)
                .filter(Objects::nonNull)
                .collect(Collectors.toList());
    }
}
