package com.cabaggregator.authservice.controller.doc;

import com.cabaggregator.authservice.core.enums.KeycloakRole;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.constraints.NotNull;
import org.keycloak.representations.idm.RoleRepresentation;
import org.keycloak.representations.idm.UserRepresentation;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.UUID;

@SecurityRequirement(name = "token")
@Tag(name = "User API Controller", description = "Provides operations with users and their roles")
public interface UserControllerDocumentation {

    @Operation(summary = "Get user roles", description = "Allows retrieval of roles assigned to a user")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successful response"),
            @ApiResponse(responseCode = "400", description = "Bad request: invalid parameters or missing required fields"),
            @ApiResponse(responseCode = "401", description = "Unauthorized: missing or invalid access token"),
            @ApiResponse(responseCode = "403", description = "Forbidden: insufficient access rights"),
            @ApiResponse(responseCode = "404", description = "Not found: user with the given ID does not exist")
    })
    ResponseEntity<List<RoleRepresentation>> getUserRoles(
            @Parameter(
                    description = "ID of the user",
                    example = "123e4567-e89b-12d3-a456-426614174000")
            @PathVariable UUID id);

    @Operation(summary = "Get user", description = "Allows retrieval of a user by ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successful response"),
            @ApiResponse(responseCode = "400", description = "Bad request: invalid parameters or missing required fields"),
            @ApiResponse(responseCode = "401", description = "Unauthorized: missing or invalid access token"),
            @ApiResponse(responseCode = "403", description = "Forbidden: insufficient access rights"),
            @ApiResponse(responseCode = "404", description = "Not found: user with the given ID does not exist")
    })
    ResponseEntity<UserRepresentation> getUser(
            @Parameter(
                    description = "ID of the user",
                    example = "123e4567-e89b-12d3-a456-426614174000")
            @PathVariable UUID id);

    @Operation(summary = "Delete user", description = "Allows deletion of a user by ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "User successfully deleted"),
            @ApiResponse(responseCode = "400", description = "Bad request: invalid parameters or missing required fields"),
            @ApiResponse(responseCode = "401", description = "Unauthorized: missing or invalid access token"),
            @ApiResponse(responseCode = "403", description = "Forbidden: insufficient access rights"),
            @ApiResponse(responseCode = "404", description = "Not found: user with the given ID does not exist")
    })
    ResponseEntity<Void> deleteUser(
            @Parameter(
                    description = "ID of the user",
                    example = "123e4567-e89b-12d3-a456-426614174000")
            @PathVariable UUID id);

    @Operation(summary = "Assign role", description = "Assigns a role to an existing user")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Role successfully assigned"),
            @ApiResponse(responseCode = "400", description = "Bad request: invalid parameters or missing required fields"),
            @ApiResponse(responseCode = "401", description = "Unauthorized: missing or invalid access token"),
            @ApiResponse(responseCode = "403", description = "Forbidden: insufficient access rights"),
            @ApiResponse(responseCode = "404", description = "Not found: user with the given ID does not exist")
    })
    ResponseEntity<Void> assignRole(
            @Parameter(
                    description = "ID of the user",
                    example = "123e4567-e89b-12d3-a456-426614174000")
            @PathVariable UUID id,
            @Parameter(
                    description = "Role to assign",
                    example = "PASSENGER")
            @RequestParam("role") @NotNull KeycloakRole keycloakRole);

    @Operation(summary = "Unassign role", description = "Removes a role from an existing user")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Role successfully removed"),
            @ApiResponse(responseCode = "400", description = "Bad request: invalid parameters or missing required fields"),
            @ApiResponse(responseCode = "401", description = "Unauthorized: missing or invalid access token"),
            @ApiResponse(responseCode = "403", description = "Forbidden: insufficient access rights"),
            @ApiResponse(responseCode = "404", description = "Not found: user with the given ID does not exist")
    })
    ResponseEntity<Void> unassignRole(
            @Parameter(
                    description = "ID of the user",
                    example = "123e4567-e89b-12d3-a456-426614174000")
            @PathVariable UUID id,
            @Parameter(
                    description = "Role to unassign",
                    example = "DRIVER")
            @RequestParam("role") @NotNull KeycloakRole keycloakRole);
}
