package com.cabaggregator.authservice.controller.api;

import com.cabaggregator.authservice.controller.doc.UserControllerDocumentation;
import com.cabaggregator.authservice.core.enums.KeycloakRole;
import com.cabaggregator.authservice.sevice.UserService;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.keycloak.representations.idm.RoleRepresentation;
import org.keycloak.representations.idm.UserRepresentation;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.UUID;

@Validated
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/users")
public class UserController implements UserControllerDocumentation {
    private final UserService userService;

    @GetMapping("/{id}/roles")
    public ResponseEntity<List<RoleRepresentation>> getUserRoles(@PathVariable UUID id) {
        List<RoleRepresentation> roles = userService.getUserRoles(id);

        return ResponseEntity.status(HttpStatus.OK).body(roles);
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserRepresentation> getUser(@PathVariable UUID id) {
        UserRepresentation user = userService.getUserById(id);

        return ResponseEntity.status(HttpStatus.OK).body(user);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable UUID id) {
        userService.deleteUser(id);

        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    @PostMapping("/{id}/role")
    public ResponseEntity<Void> assignRole(
            @PathVariable UUID id,
            @RequestParam("role") @NotNull KeycloakRole keycloakRole) {

        userService.assignRoleToUser(id, keycloakRole);

        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    @DeleteMapping("/{id}/role")
    public ResponseEntity<Void> unassignRole(
            @PathVariable UUID id,
            @RequestParam("role") @NotNull KeycloakRole keycloakRole) {

        userService.unassignRoleFromUser(id, keycloakRole);

        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}
