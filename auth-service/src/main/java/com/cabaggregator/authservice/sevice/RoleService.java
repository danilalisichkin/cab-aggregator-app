package com.cabaggregator.authservice.sevice;

import com.cabaggregator.authservice.core.enums.KeyCloakRole;
import org.keycloak.admin.client.resource.UserResource;
import org.keycloak.representations.idm.RoleRepresentation;

import java.util.List;

public interface RoleService {
    void assignRoleToUser(KeyCloakRole role, UserResource userResource);

    void unassignRoleFromUser(KeyCloakRole role, UserResource userResource);

    List<RoleRepresentation> getAllUserRoles(UserResource userResource);
}
