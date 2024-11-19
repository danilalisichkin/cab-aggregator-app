package com.cabaggregator.authservice.controller.api;

import com.cabaggregator.authservice.core.constant.ValidationErrors;
import com.cabaggregator.authservice.core.enums.KeyCloakRole;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import org.hibernate.validator.constraints.UUID;
import org.keycloak.representations.idm.RoleRepresentation;
import org.keycloak.representations.idm.UserRepresentation;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/v1/users")
public class UserController {

    @GetMapping("/{id}/roles")
    public ResponseEntity<List<RoleRepresentation>> getUserRoles(
            @PathVariable @NotEmpty @UUID(message = ValidationErrors.INVALID_UUID_FORMAT) String id) {

        return ResponseEntity.status(HttpStatus.OK).build();
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserRepresentation> getUser(
            @PathVariable @NotEmpty @UUID(message = ValidationErrors.INVALID_UUID_FORMAT) String id) {

        return ResponseEntity.status(HttpStatus.OK).build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUser(
            @PathVariable @NotEmpty @UUID(message = ValidationErrors.INVALID_UUID_FORMAT) String id) {

        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    @PostMapping("/{id}/role")
    public ResponseEntity<Void> assignRole(
            @PathVariable @NotEmpty @UUID(message = ValidationErrors.INVALID_UUID_FORMAT) String id,
            @RequestParam("role") @NotNull KeyCloakRole keycloakRole) {

        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    @DeleteMapping("/{id}/role")
    public ResponseEntity<Void> unassignRole(
            @PathVariable @NotEmpty @UUID(message = ValidationErrors.INVALID_UUID_FORMAT) String id,
            @RequestParam("role") @NotNull KeyCloakRole keycloakRole) {

        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}
