package com.cabaggregator.authservice.controller.api.doc;

import com.cabaggregator.authservice.core.enums.KeycloakRole;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.constraints.NotNull;
import org.keycloak.representations.idm.RoleRepresentation;
import org.keycloak.representations.idm.UserRepresentation;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.UUID;

@Tag(name = "User API Controller", description = "Provides operations with users and their roles")
public interface UserControllerDocumentation {

    @Operation(summary = "Get user roles", description = "Allows to get roles of existing user")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Successful response"),
            @ApiResponse(responseCode = "400", description = "Bad request: invalid parameters"),
            @ApiResponse(responseCode = "400", description = "Bad request: missing required fields"),
            @ApiResponse(responseCode = "401", description = "Unauthorized: missing access token"),
            @ApiResponse(responseCode = "403", description = "Forbidden: insufficient access rights"),
            @ApiResponse(responseCode = "404", description = "User not found: user with ID does not exist")
    })
    ResponseEntity<List<RoleRepresentation>> getUserRoles(
            @Parameter(name = "id", description = "ID of the user", required = true)
            @PathVariable UUID id);

    @Operation(summary = "Get user", description = "Allows to get existing user by its ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successful response"),
            @ApiResponse(responseCode = "400", description = "Bad request: invalid parameters"),
            @ApiResponse(responseCode = "400", description = "Bad request: missing required fields"),
            @ApiResponse(responseCode = "401", description = "Unauthorized: missing access token"),
            @ApiResponse(responseCode = "403", description = "Forbidden: insufficient access rights"),
            @ApiResponse(responseCode = "404", description = "User not found: user with ID does not exist")
    })
    ResponseEntity<UserRepresentation> getUser(
            @Parameter(name = "id", description = "ID of the user", required = true)
            @PathVariable UUID id);

    @Operation(summary = "Delete user", description = "Allows to delete existing user by its ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Successful response"),
            @ApiResponse(responseCode = "400", description = "Bad request: invalid parameters"),
            @ApiResponse(responseCode = "400", description = "Bad request: missing required fields"),
            @ApiResponse(responseCode = "401", description = "Unauthorized: missing access token"),
            @ApiResponse(responseCode = "403", description = "Forbidden: insufficient access rights"),
            @ApiResponse(responseCode = "404", description = "User not found: user with ID does not exist")
    })
    ResponseEntity<Void> deleteUser(
            @Parameter(name = "id", description = "ID of the user", required = true)
            @PathVariable UUID id);

    @Operation(summary = "Assign role", description = "Allows to assign role to existing user")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Successful response"),
            @ApiResponse(responseCode = "400", description = "Bad request: invalid parameters"),
            @ApiResponse(responseCode = "400", description = "Bad request: missing required fields"),
            @ApiResponse(responseCode = "401", description = "Unauthorized: missing access token"),
            @ApiResponse(responseCode = "403", description = "Forbidden: insufficient access rights"),
            @ApiResponse(responseCode = "404", description = "User not found: user with ID does not exist")
    })
    ResponseEntity<Void> assignRole(
            @Parameter(name = "id", description = "ID of the user", required = true)
            @PathVariable UUID id,
            @Parameter(name = "role", description = "Role to be assign", required = true)
            @RequestParam("role") @NotNull KeycloakRole keycloakRole);

    @Operation(summary = "Unassign role", description = "Allows to unassign role from existing user")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Successful response"),
            @ApiResponse(responseCode = "400", description = "Bad request: invalid parameters"),
            @ApiResponse(responseCode = "400", description = "Bad request: missing required fields"),
            @ApiResponse(responseCode = "401", description = "Unauthorized: missing access token"),
            @ApiResponse(responseCode = "403", description = "Forbidden: insufficient access rights"),
            @ApiResponse(responseCode = "404", description = "User not found: user with ID does not exist")
    })
    ResponseEntity<Void> unassignRole(
            @Parameter(name = "id", description = "ID of the user", required = true)
            @PathVariable UUID id,
            @Parameter(name = "role", description = "Role to be unassign", required = true)
            @RequestParam("role") @NotNull KeycloakRole keycloakRole);
}
