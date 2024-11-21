package com.cabaggregator.authservice.sevice;

import com.cabaggregator.authservice.core.enums.KeycloakRole;
import org.keycloak.representations.idm.RoleRepresentation;
import org.keycloak.representations.idm.UserRepresentation;

import java.util.List;
import java.util.UUID;

public interface UserService {
    List<RoleRepresentation> getUserRoles(UUID id);

    UserRepresentation getUserById(UUID id);

    void deleteUser(UUID id);

    void assignRoleToUser(UUID id, KeycloakRole role);

    void unassignRoleFromUser(UUID id, KeycloakRole role);
}
