package com.cabaggregator.authservice.controller.api;

import com.cabaggregator.authservice.core.enums.KeycloakRole;
import jakarta.validation.constraints.NotNull;
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
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/users")
public class UserController {

    @GetMapping("/{id}/roles")
    public ResponseEntity<List<RoleRepresentation>> getUserRoles(@PathVariable UUID id) {

        return ResponseEntity.status(HttpStatus.OK).build();
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserRepresentation> getUser(@PathVariable UUID id) {

        return ResponseEntity.status(HttpStatus.OK).build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable UUID id) {

        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    @PostMapping("/{id}/role")
    public ResponseEntity<Void> assignRole(
            @PathVariable UUID id,
            @RequestParam("role") @NotNull KeycloakRole keycloakRole) {

        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    @DeleteMapping("/{id}/role")
    public ResponseEntity<Void> unassignRole(
            @PathVariable UUID id,
            @RequestParam("role") @NotNull KeycloakRole keycloakRole) {

        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}
