package com.cabaggregator.passengerservice.unit.util;

import com.cabaggregator.passengerservice.exception.InternalErrorException;
import com.cabaggregator.passengerservice.security.enums.UserRole;
import com.cabaggregator.passengerservice.security.util.SecurityUtil;
import com.cabaggregator.passengerservice.util.UserRoleExtractor;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Collections;
import java.util.List;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@Tag("unit")
@ExtendWith(MockitoExtension.class)
class UserRoleExtractorTest {
    @InjectMocks
    private UserRoleExtractor userRoleExtractor;

    @Mock
    private SecurityUtil securityUtil;

    @Test
    void extractCurrentUserRole_ShouldReturnUserRole_WhenUserHasOnlyOneRole() {
        UserRole role = UserRole.PASSENGER;
        List<String> authorities = List.of(role.name());

        when(securityUtil.getUserAuthoritiesFromSecurityContext())
                .thenReturn(authorities);

        UserRole actual = userRoleExtractor.extractCurrentUserRole();

        verify(securityUtil).getUserAuthoritiesFromSecurityContext();

        assertThat(actual).isEqualTo(role);
    }

    @Test
    void extractCurrentUserRole_ShouldThrowInternalErrorException_WhenUserHasNoRole() {
        when(securityUtil.getUserAuthoritiesFromSecurityContext())
                .thenReturn(Collections.emptyList());
        when(securityUtil.getUserIdFromSecurityContext())
                .thenReturn(UUID.randomUUID());

        assertThatThrownBy(
                () -> userRoleExtractor.extractCurrentUserRole())
                .isInstanceOf(InternalErrorException.class);

        verify(securityUtil).getUserAuthoritiesFromSecurityContext();
        verify(securityUtil).getUserIdFromSecurityContext();
    }

    @Test
    void extractCurrentUserRole_ShouldThrowInternalErrorException_WhenUserHasMoreThanOneRole() {
        List<String> authorities = List.of(UserRole.PASSENGER.name(), UserRole.DRIVER.name());

        when(securityUtil.getUserAuthoritiesFromSecurityContext())
                .thenReturn(authorities);
        when(securityUtil.getUserIdFromSecurityContext())
                .thenReturn(UUID.randomUUID());

        assertThatThrownBy(
                () -> userRoleExtractor.extractCurrentUserRole())
                .isInstanceOf(InternalErrorException.class);

        verify(securityUtil).getUserAuthoritiesFromSecurityContext();
        verify(securityUtil).getUserIdFromSecurityContext();
    }
}
