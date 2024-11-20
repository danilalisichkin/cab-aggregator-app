package com.cabaggregator.authservice.core.mapper;

import com.cabaggregator.authservice.core.dto.KeycloakAccessTokenDto;
import org.keycloak.representations.AccessTokenResponse;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface KeycloakAccessTokenMapper {
    KeycloakAccessTokenDto tokenToDto(AccessTokenResponse accessToken);
}
