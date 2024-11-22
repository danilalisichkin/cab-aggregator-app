package com.cabaggregator.authservice.controller.api.doc;

import com.cabaggregator.authservice.core.dto.KeycloakAccessTokenDto;
import com.cabaggregator.authservice.core.dto.UserLoginDto;
import com.cabaggregator.authservice.core.dto.UserRegisterDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;

@Tag(name = "Auth API Controller", description = "Provides sign in, sign up and refresh access token operations")
public interface AuthControllerDocumentation {

    @Operation(summary = "Sign in", description = "Allows to sign in user in application")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successful response"),
            @ApiResponse(responseCode = "400", description = "Bad request: invalid parameters"),
            @ApiResponse(responseCode = "400", description = "Bad request: missing required fields"),
            @ApiResponse(responseCode = "400", description = "Bad request: invalid login identifier"),
            @ApiResponse(responseCode = "400", description = "Bad request: login identifier has not email/phone format"),
            @ApiResponse(responseCode = "401", description = "Unauthorized: invalid login identifier or password")
    })
    ResponseEntity<KeycloakAccessTokenDto> signIn(
            @Parameter(name = "user credentials", description = "User credentials", required = true)
            @RequestBody @Valid UserLoginDto loginDto);

    @Operation(summary = "Sign up", description = "Allows to sign up new user in application")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successful response"),
            @ApiResponse(responseCode = "400", description = "Bad request: invalid parameters"),
            @ApiResponse(responseCode = "400", description = "Bad request: missing required fields"),
            @ApiResponse(responseCode = "400", description = "Bad request: email is not fund or unreachable"),
            @ApiResponse(responseCode = "409", description = "Conflict: phone number is already used"),
            @ApiResponse(responseCode = "409", description = "Conflict: email is already used")
    })
    ResponseEntity<Void> signUp(
            @Parameter(name = "user registration details",
                    description = "Details, that user must provide to register itself", required = true)
            @RequestBody @Valid UserRegisterDto registerDto);

    @Operation(summary = "Refresh token", description = "Allows to refresh user's access token by refresh token")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successful response"),
            @ApiResponse(responseCode = "400", description = "Bad request: invalid parameters"),
            @ApiResponse(responseCode = "400", description = "Bad request: missing required fields"),
            @ApiResponse(responseCode = "400", description = "Bad request: refresh token is expired"),
    })
    ResponseEntity<KeycloakAccessTokenDto> refreshToken(
            @Parameter(name = "refresh token", description = "Refresh token that refreshes access token", required = true)
            @RequestBody @NotEmpty String refreshToken);
}
