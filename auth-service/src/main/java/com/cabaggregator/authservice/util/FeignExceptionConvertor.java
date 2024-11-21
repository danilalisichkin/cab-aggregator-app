package com.cabaggregator.authservice.util;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import feign.FeignException;
import jakarta.ws.rs.InternalServerErrorException;
import jakarta.ws.rs.core.Response;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.keycloak.representations.idm.ErrorRepresentation;

import java.nio.ByteBuffer;
import java.nio.charset.StandardCharsets;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class FeignExceptionConvertor {

    public static Response convertToKeycloakResponse(final FeignException exception) {
        ErrorRepresentation keycloakError = new ErrorRepresentation();
        ObjectMapper objectMapper = new ObjectMapper();

        try {
            ByteBuffer responseBody = exception
                    .responseBody()
                    .orElseThrow(() -> new InternalServerErrorException(exception.getMessage()));

            String body = new String(
                    responseBody.array(),
                    StandardCharsets.UTF_8);

            JsonNode jsonNode = objectMapper.readTree(body);
            String errorDescription = jsonNode.get("error_description").asText();

            keycloakError.setErrorMessage(errorDescription);
        } catch (Exception e) {
            throw new InternalServerErrorException();
        }

        return Response
                .status(exception.status())
                .entity(keycloakError)
                .build();
    }
}