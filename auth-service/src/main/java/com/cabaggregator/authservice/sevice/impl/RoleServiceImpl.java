package com.cabaggregator.authservice.sevice.impl;

import com.cabaggregator.authservice.core.constant.ApplicationMessages;
import com.cabaggregator.authservice.core.enums.KeyCloakRole;
import com.cabaggregator.authservice.exception.BadRequestException;
import com.cabaggregator.authservice.exception.ResourceNotFoundException;
import jakarta.ws.rs.NotFoundException;
import lombok.RequiredArgsConstructor;
import org.keycloak.admin.client.resource.RolesResource;
import org.keycloak.admin.client.resource.UserResource;
import org.keycloak.representations.idm.RoleRepresentation;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;

@Service
@RequiredArgsConstructor
public class RoleServiceImpl implements com.cabaggregator.authservice.sevice.RoleService {
    private final RolesResource rolesResource;

    @Override
    public void assignRoleToUser(KeyCloakRole role, UserResource userResource) {
        RoleRepresentation keycloakRole = getRoleRepresentation(role);

        try {
            userResource.roles().realmLevel().add(Collections.singletonList(keycloakRole));
        } catch (NotFoundException e) {
            throw new BadRequestException(ApplicationMessages.USER_NOT_FOUND);
        }
    }

    @Override
    public void unassignRoleFromUser(KeyCloakRole role, UserResource userResource) {
        RoleRepresentation keycloakRole = getRoleRepresentation(role);
        try {
            userResource.roles().realmLevel().remove(Collections.singletonList(keycloakRole));
        } catch (NotFoundException e) {
            throw new ResourceNotFoundException(ApplicationMessages.USER_NOT_FOUND);
        }
    }

    @Override
    public List<RoleRepresentation> getAllUserRoles(UserResource userResource) {
        try {
            return userResource.roles().realmLevel().listAll();
        } catch (NotFoundException e) {
            throw new ResourceNotFoundException(
                    ApplicationMessages.USER_WITH_ID_NOT_FOUND,
                    userResource.toRepresentation().getId());
        }
    }

    private RoleRepresentation getRoleRepresentation(KeyCloakRole keycloakRole) {
        try {
            return rolesResource.get(keycloakRole.getValue()).toRepresentation();
        } catch (NotFoundException e) {
            throw new ResourceNotFoundException(
                    ApplicationMessages.USER_ROLE_NOT_FOUND,
                    keycloakRole.getValue());
        }
    }
}
