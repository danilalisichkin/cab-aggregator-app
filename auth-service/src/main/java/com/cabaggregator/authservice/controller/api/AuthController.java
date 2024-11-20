package com.cabaggregator.authservice.controller.api;

import com.cabaggregator.authservice.core.dto.KeycloakAccessTokenDto;
import com.cabaggregator.authservice.core.dto.UserLoginDto;
import com.cabaggregator.authservice.core.dto.UserRegisterDto;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Validated
@RestController
@RequestMapping("/api/v1/auth")
public class AuthController {

    @PostMapping("/sign-in")
    public ResponseEntity<KeycloakAccessTokenDto> signIn(@RequestBody @Valid UserLoginDto loginDto) {

        return ResponseEntity.status(HttpStatus.OK).build();
    }

    @PostMapping("/sign-up")
    public ResponseEntity<Void> signUp(@RequestBody @Valid UserRegisterDto registerDto) {

        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @PostMapping("/refresh-token")
    public ResponseEntity<KeycloakAccessTokenDto> refreshToken(@RequestBody @NotEmpty String refreshToken) {

        return ResponseEntity.status(HttpStatus.OK).build();
    }
}
