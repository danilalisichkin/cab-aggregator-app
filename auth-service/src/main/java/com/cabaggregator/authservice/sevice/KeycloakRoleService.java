package com.cabaggregator.authservice.sevice;

import com.cabaggregator.authservice.core.enums.KeycloakRole;
import org.keycloak.admin.client.resource.UserResource;
import org.keycloak.representations.idm.RoleRepresentation;

import java.util.List;

public interface KeycloakRoleService {
    void assignRoleToUser(KeycloakRole role, UserResource userResource);

    void unassignRoleFromUser(KeycloakRole role, UserResource userResource);

    List<RoleRepresentation> getAllUserRoles(UserResource userResource);
}
