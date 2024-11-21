package com.cabaggregator.authservice.keycloak.util;

import com.cabaggregator.authservice.core.constant.ApplicationMessages;
import com.cabaggregator.authservice.exception.BadRequestException;
import com.cabaggregator.authservice.exception.DataUniquenessConflictException;
import com.cabaggregator.authservice.exception.UnauthorizedException;
import jakarta.ws.rs.InternalServerErrorException;
import jakarta.ws.rs.core.Response;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.keycloak.representations.idm.ErrorRepresentation;
import org.springframework.http.HttpStatus;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class KeycloakResponseValidator {

    public static void validate(Response response) {
        HttpStatus statusCode = HttpStatus.valueOf(response.getStatus());
        String errorMessage = response.readEntity(ErrorRepresentation.class).getErrorMessage();

        String responseMessage = switch (statusCode) {
            case CONFLICT -> handleConflict(errorMessage);
            case UNAUTHORIZED -> handleUnauthorized(errorMessage);
            case BAD_REQUEST -> handleBadRequest(errorMessage);
            default -> throw new InternalServerErrorException(
                    buildExceptionMessage(errorMessage, statusCode));
        };

        throwExceptionForStatus(statusCode, responseMessage);
    }

    private static String handleConflict(String errorMessage) {
        return switch (errorMessage) {
            case KeycloakErrorMessages.USER_WITH_SAME_EMAIL_EXISTS -> ApplicationMessages.REGISTER_USER_WITH_USED_EMAIL;
            case KeycloakErrorMessages.USER_WITH_SAME_USERNAME_EXISTS ->
                    ApplicationMessages.REGISTER_USER_WITH_USED_PHONE;
            default -> throw new InternalServerErrorException(
                    buildExceptionMessage(errorMessage, HttpStatus.CONFLICT));
        };
    }

    private static String handleUnauthorized(String errorMessage) {
        return switch (errorMessage) {
            case KeycloakErrorMessages.INVALID_REFRESH_TOKEN -> ApplicationMessages.INVALID_REFRESH_TOKEN;
            default -> throw new InternalServerErrorException(
                    buildExceptionMessage(errorMessage, HttpStatus.UNAUTHORIZED));
        };
    }

    private static String handleBadRequest(String errorMessage) {
        return switch (errorMessage) {
            case KeycloakErrorMessages.INVALID_REFRESH_TOKEN -> ApplicationMessages.INVALID_REFRESH_TOKEN;
            case KeycloakErrorMessages.REFRESH_TOKEN_EXPIRED -> ApplicationMessages.REFRESH_TOKEN_EXPIRED;
            default -> throw new InternalServerErrorException(
                    buildExceptionMessage(errorMessage, HttpStatus.BAD_REQUEST));
        };
    }

    private static void throwExceptionForStatus(HttpStatus status, String message) {
        switch (status) {
            case CONFLICT -> throw new DataUniquenessConflictException(message);
            case UNAUTHORIZED -> throw new UnauthorizedException(message);
            case BAD_REQUEST -> throw new BadRequestException(message);
            default -> throw new InternalServerErrorException(
                    buildExceptionMessage(message, status));
        }
    }

    private static String buildExceptionMessage(String message, HttpStatus status) {
        return String.format("KeyCloak error: status: %d, response message: %s", status.value(), message);
    }
}
