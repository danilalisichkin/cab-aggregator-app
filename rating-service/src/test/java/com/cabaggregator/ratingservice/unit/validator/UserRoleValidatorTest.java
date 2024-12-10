package com.cabaggregator.ratingservice.unit.validator;

import com.cabaggregator.ratingservice.exception.ForbiddenException;
import com.cabaggregator.ratingservice.security.enums.UserRole;
import com.cabaggregator.ratingservice.security.util.SecurityUtil;
import com.cabaggregator.ratingservice.util.DriverRateTestUtil;
import com.cabaggregator.ratingservice.util.PassengerRateTestUtil;
import com.cabaggregator.ratingservice.validator.UserRoleValidator;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.MockedStatic;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Collections;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThatCode;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.mockStatic;

@Tag("unit")
@ExtendWith(MockitoExtension.class)
class UserRoleValidatorTest {
    @InjectMocks
    private UserRoleValidator userRoleValidator;

    @Test
    void validateUserIsDriverOrAdmin_ShouldThrowForbiddenException_WhenUserIsDriverButWithDifferentId() {
        List<String> authorities = Collections.singletonList(UserRole.DRIVER.name());

        try (MockedStatic<SecurityUtil> mockedStatic = mockStatic(SecurityUtil.class)) {
            mockedStatic.when(SecurityUtil::getUserAuthoritiesFromSecurityContext)
                    .thenReturn(authorities);
            mockedStatic.when(SecurityUtil::getUserIdFromSecurityContext)
                    .thenReturn(PassengerRateTestUtil.OTHER_DRIVER_ID);

            assertThatThrownBy(
                    () -> userRoleValidator.validateUserIsDriverOrAdmin(PassengerRateTestUtil.DRIVER_ID))
                    .isInstanceOf(ForbiddenException.class);

            mockedStatic.verify(SecurityUtil::getUserIdFromSecurityContext);
            mockedStatic.verify(SecurityUtil::getUserAuthoritiesFromSecurityContext);
        }
    }

    @Test
    void validateUserIsDriverOrAdmin_ShouldNotThrowException_WhenUserIsDriverWithSameId() {
        List<String> authorities = Collections.singletonList(UserRole.DRIVER.name());

        try (MockedStatic<SecurityUtil> mockedStatic = mockStatic(SecurityUtil.class)) {
            mockedStatic.when(SecurityUtil::getUserAuthoritiesFromSecurityContext)
                    .thenReturn(authorities);
            mockedStatic.when(SecurityUtil::getUserIdFromSecurityContext)
                    .thenReturn(PassengerRateTestUtil.DRIVER_ID);

            assertThatCode(
                    () -> userRoleValidator.validateUserIsDriverOrAdmin(PassengerRateTestUtil.DRIVER_ID))
                    .doesNotThrowAnyException();

            mockedStatic.verify(SecurityUtil::getUserIdFromSecurityContext);
            mockedStatic.verify(SecurityUtil::getUserAuthoritiesFromSecurityContext);
        }
    }

    @Test
    void validateUserIsDriverOrAdmin_ShouldNotThrowException_WhenUserIsAdmin() {
        List<String> authorities = Collections.singletonList(UserRole.ADMIN.name());

        try (MockedStatic<SecurityUtil> mockedStatic = mockStatic(SecurityUtil.class)) {
            mockedStatic.when(SecurityUtil::getUserAuthoritiesFromSecurityContext)
                    .thenReturn(authorities);
            mockedStatic.when(SecurityUtil::getUserIdFromSecurityContext)
                    .thenReturn(PassengerRateTestUtil.DRIVER_ID);

            assertThatCode(
                    () -> userRoleValidator.validateUserIsDriverOrAdmin(PassengerRateTestUtil.OTHER_DRIVER_ID))
                    .doesNotThrowAnyException();

            mockedStatic.verify(SecurityUtil::getUserIdFromSecurityContext);
            mockedStatic.verify(SecurityUtil::getUserAuthoritiesFromSecurityContext);
        }
    }

    @Test
    void validateUserIsPassengerOrAdmin_ShouldThrowForbiddenException_WhenUserIsPassengerButWithDifferentId() {
        List<String> authorities = Collections.singletonList(UserRole.PASSENGER.name());

        try (MockedStatic<SecurityUtil> mockedStatic = mockStatic(SecurityUtil.class)) {
            mockedStatic.when(SecurityUtil::getUserAuthoritiesFromSecurityContext)
                    .thenReturn(authorities);
            mockedStatic.when(SecurityUtil::getUserIdFromSecurityContext)
                    .thenReturn(DriverRateTestUtil.OTHER_PASSENGER_ID);

            assertThatThrownBy(
                    () -> userRoleValidator.validateUserIsPassengerOrAdmin(DriverRateTestUtil.PASSENGER_ID))
                    .isInstanceOf(ForbiddenException.class);

            mockedStatic.verify(SecurityUtil::getUserIdFromSecurityContext);
            mockedStatic.verify(SecurityUtil::getUserAuthoritiesFromSecurityContext);
        }
    }

    @Test
    void validateUserIsPassengerOrAdmin_ShouldNotThrowException_WhenUserIsPassengerWithSameId() {
        List<String> authorities = Collections.singletonList(UserRole.PASSENGER.name());

        try (MockedStatic<SecurityUtil> mockedStatic = mockStatic(SecurityUtil.class)) {
            mockedStatic.when(SecurityUtil::getUserAuthoritiesFromSecurityContext)
                    .thenReturn(authorities);
            mockedStatic.when(SecurityUtil::getUserIdFromSecurityContext)
                    .thenReturn(DriverRateTestUtil.PASSENGER_ID);

            assertThatCode(
                    () -> userRoleValidator.validateUserIsPassengerOrAdmin(DriverRateTestUtil.PASSENGER_ID))
                    .doesNotThrowAnyException();

            mockedStatic.verify(SecurityUtil::getUserIdFromSecurityContext);
            mockedStatic.verify(SecurityUtil::getUserAuthoritiesFromSecurityContext);
        }
    }

    @Test
    void validateUserIsPassengerOrAdmin_ShouldNotThrowException_WhenUserIsAdmin() {
        List<String> authorities = Collections.singletonList(UserRole.ADMIN.name());

        try (MockedStatic<SecurityUtil> mockedStatic = mockStatic(SecurityUtil.class)) {
            mockedStatic.when(SecurityUtil::getUserAuthoritiesFromSecurityContext)
                    .thenReturn(authorities);
            mockedStatic.when(SecurityUtil::getUserIdFromSecurityContext)
                    .thenReturn(DriverRateTestUtil.OTHER_PASSENGER_ID);

            assertThatCode(
                    () -> userRoleValidator.validateUserIsPassengerOrAdmin(DriverRateTestUtil.PASSENGER_ID))
                    .doesNotThrowAnyException();

            mockedStatic.verify(SecurityUtil::getUserIdFromSecurityContext);
            mockedStatic.verify(SecurityUtil::getUserAuthoritiesFromSecurityContext);
        }
    }
}
