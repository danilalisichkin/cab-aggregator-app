package com.cabaggregator.rideservice.security.util;

import com.cabaggregator.rideservice.exception.InternalErrorException;
import com.nimbusds.jwt.JWTClaimsSet;
import com.nimbusds.jwt.SignedJWT;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.text.ParseException;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class JwtClaimsExtractor {

    private static JWTClaimsSet getClaims(final String token) {
        try {
            return SignedJWT
                    .parse(token)
                    .getJWTClaimsSet();
        } catch (ParseException e) {
            throw new InternalErrorException(e);
        }
    }

    /**
     * Extracts user id from JWT.
     **/
    public static String extractUserId(String token) {
        try {
            return getClaims(token)
                    .getStringClaim("sub");
        } catch (ParseException e) {
            throw new InternalErrorException(e);
        }
    }

    /**
     * Extracts user roles as GrantedAuthorities from JWT.
     **/
    public static List<GrantedAuthority> extractUserRoles(String token) {
        Map<String, Object> realmAccess;

        try {
            realmAccess = getClaims(token)
                    .getJSONObjectClaim("realm_access");
        } catch (ParseException e) {
            throw new InternalErrorException(e);
        }

        @SuppressWarnings("unchecked")
        List<String> roles = (List<String>) realmAccess.get("roles");

        return roles.stream()
                .map(SimpleGrantedAuthority::new)
                .collect(Collectors.toList());
    }
}