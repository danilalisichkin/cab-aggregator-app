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

@Tag(name = "Auth API Controller", description = "Provides sign in, sign up, and refresh access token operations")
public interface AuthControllerDocumentation {

    @Operation(summary = "Sign in", description = "Allows the user to sign in to the application")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successful response"),
            @ApiResponse(responseCode = "400", description = "Bad request: invalid parameters or missing required fields"),
            @ApiResponse(responseCode = "401", description = "Unauthorized: invalid login identifier or password")
    })
    ResponseEntity<KeycloakAccessTokenDto> signIn(
            @Parameter(name = "User credentials", description = "Credentials required for signing in", required = true)
            @RequestBody @Valid UserLoginDto loginDto);

    @Operation(summary = "Sign up", description = "Allows a new user to sign up in the application")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successful response"),
            @ApiResponse(responseCode = "400", description = "Bad request: invalid parameters or missing required fields"),
            @ApiResponse(responseCode = "409", description = "Conflict: phone number or email is already used")
    })
    ResponseEntity<Void> signUp(
            @Parameter(name = "User registration details",
                    description = "Details required for user registration", required = true)
            @RequestBody @Valid UserRegisterDto registerDto);

    @Operation(summary = "Refresh token", description = "Allows the user to refresh their access token using a refresh token")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successful response"),
            @ApiResponse(responseCode = "400", description = "Bad request: invalid parameters or refresh token expired")
    })
    ResponseEntity<KeycloakAccessTokenDto> refreshToken(
            @Parameter(name = "Refresh token", description = "Refresh token used to obtain a new access token", required = true)
            @RequestBody @NotEmpty String refreshToken);
}
