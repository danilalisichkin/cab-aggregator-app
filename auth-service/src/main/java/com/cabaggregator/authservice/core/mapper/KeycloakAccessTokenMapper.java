package com.cabaggregator.authservice.core.mapper;

import com.cabaggregator.authservice.core.dto.KeycloakAccessTokenDto;
import org.keycloak.representations.AccessTokenResponse;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface KeycloakAccessTokenMapper {
    @Mapping(source = "token", target = "accessToken")
    KeycloakAccessTokenDto tokenToDto(AccessTokenResponse accessToken);
}
