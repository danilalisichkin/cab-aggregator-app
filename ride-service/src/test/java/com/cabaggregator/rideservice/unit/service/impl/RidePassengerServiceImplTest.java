package com.cabaggregator.rideservice.unit.service.impl;

import com.cabaggregator.rideservice.core.dto.page.PageDto;
import com.cabaggregator.rideservice.core.dto.ride.RideDto;
import com.cabaggregator.rideservice.core.dto.ride.RideUpdatingDto;
import com.cabaggregator.rideservice.core.enums.RideStatus;
import com.cabaggregator.rideservice.core.enums.sort.RideSortField;
import com.cabaggregator.rideservice.entity.Ride;
import com.cabaggregator.rideservice.exception.BadRequestException;
import com.cabaggregator.rideservice.exception.ForbiddenException;
import com.cabaggregator.rideservice.exception.ResourceNotFoundException;
import com.cabaggregator.rideservice.mapper.PageMapper;
import com.cabaggregator.rideservice.mapper.RideMapper;
import com.cabaggregator.rideservice.repository.RideRepository;
import com.cabaggregator.rideservice.security.enums.UserRole;
import com.cabaggregator.rideservice.security.util.SecurityUtil;
import com.cabaggregator.rideservice.service.impl.RidePassengerServiceImpl;
import com.cabaggregator.rideservice.strategy.manager.RideStatusChangingManager;
import com.cabaggregator.rideservice.util.PageRequestBuilder;
import com.cabaggregator.rideservice.util.PaginationTestUtil;
import com.cabaggregator.rideservice.util.RideTestUtil;
import com.cabaggregator.rideservice.util.UserRoleExtractor;
import com.cabaggregator.rideservice.validator.RideValidator;
import org.bson.types.ObjectId;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockedStatic;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;

import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.mockStatic;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoInteractions;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;

@Tag("unit")
@ExtendWith(MockitoExtension.class)
class RidePassengerServiceImplTest {

    @InjectMocks
    private RidePassengerServiceImpl ridePassengerService;

    @Mock
    private SecurityUtil securityUtil;

    @Mock
    private UserRoleExtractor userRoleExtractor;

    @Mock
    private RideStatusChangingManager rideStatusChangingManager;

    @Mock
    private RideValidator rideValidator;

    @Mock
    private RideRepository rideRepository;

    @Mock
    private RideMapper rideMapper;

    @Mock
    private PageMapper pageMapper;

    @Test
    void getPageOfPassengerRides_ShouldReturnPageDto_WhenCalledWithValidParameters() {
        Integer offset = 0;
        Integer limit = 10;
        RideSortField sortBy = RideSortField.ID;
        Sort.Direction sortOrder = Sort.Direction.ASC;

        Ride ride = RideTestUtil.buildDefaultRide();
        UUID passengerId = ride.getPassengerId();
        RideDto rideDto = RideTestUtil.buildRideDto();

        PageRequest pageRequest = PaginationTestUtil.buildPageRequest(offset, limit, sortBy.getValue(), sortOrder);
        Page<Ride> ridePage = PaginationTestUtil.buildPageFromSingleElement(ride);
        Page<RideDto> rideDtoPage = PaginationTestUtil.buildPageFromSingleElement(rideDto);
        PageDto<RideDto> pageDto = PaginationTestUtil.buildPageDto(rideDtoPage);

        try (MockedStatic<PageRequestBuilder> mockedStatic = mockStatic(PageRequestBuilder.class)) {
            when(securityUtil.getUserIdFromSecurityContext())
                    .thenReturn(passengerId);
            mockedStatic.when(() -> PageRequestBuilder.buildPageRequest(offset, limit, sortBy.getValue(), sortOrder))
                    .thenReturn(pageRequest);
            when(rideRepository.findAllByPassengerId(passengerId, pageRequest))
                    .thenReturn(ridePage);
            when(rideMapper.entityPageToDtoPage(ridePage))
                    .thenReturn(rideDtoPage);
            when(pageMapper.pageToPageDto(rideDtoPage))
                    .thenReturn(pageDto);

            PageDto<RideDto> actual =
                    ridePassengerService.getPageOfPassengerRides(passengerId, offset, limit, sortBy, sortOrder, null);

            assertThat(actual)
                    .isNotNull()
                    .isEqualTo(pageDto);

            verify(securityUtil).getUserIdFromSecurityContext();
            mockedStatic.verify(() -> PageRequestBuilder.buildPageRequest(offset, limit, sortBy.getValue(), sortOrder));
            verify(rideRepository).findAllByPassengerId(passengerId, pageRequest);
            verify(rideRepository, never()).findAllByPassengerIdAndStatus(passengerId, RideStatus.REQUESTED, pageRequest);
            verify(rideMapper).entityPageToDtoPage(ridePage);
            verify(pageMapper).pageToPageDto(rideDtoPage);
        }
    }

    @Test
    void getPageOfPassengerRides_ShouldThrowForbiddenException_WhenUserIsNotResourceOwner() {
        Integer offset = 0;
        Integer limit = 10;
        RideSortField sortBy = RideSortField.ID;
        Sort.Direction sortOrder = Sort.Direction.ASC;

        UUID passengerId = RideTestUtil.PASSENGER_ID;
        UUID userId = RideTestUtil.NOT_EXISTING_PASSENGER_ID;

        when(securityUtil.getUserIdFromSecurityContext())
                .thenReturn(userId);

        assertThatThrownBy(
                () -> ridePassengerService.getPageOfPassengerRides(passengerId, offset, limit, sortBy, sortOrder, null))
                .isInstanceOf(ForbiddenException.class);

        verify(securityUtil).getUserIdFromSecurityContext();
        verifyNoInteractions(rideRepository, rideMapper, pageMapper);
    }

    @Test
    void getRide_ShouldThrowResourceNotFoundException_WhenRideIsNotFound() {
        UUID passengerId = RideTestUtil.PASSENGER_ID;
        ObjectId rideId = RideTestUtil.NOT_EXISTING_ID;

        when(rideRepository.findById(rideId))
                .thenReturn(Optional.empty());

        assertThatThrownBy(
                () -> ridePassengerService.getRide(passengerId, rideId))
                .isInstanceOf(ResourceNotFoundException.class);

        verify(rideRepository).findById(rideId);
        verifyNoMoreInteractions(rideRepository);
        verifyNoInteractions(securityUtil, rideValidator, rideMapper);
    }

    @Test
    void getRide_ShouldThrowForbiddenException_WhenUserIsNotRideParticipant() {
        Ride ride = RideTestUtil.buildDefaultRide();
        UUID passengerId = RideTestUtil.NOT_EXISTING_PASSENGER_ID;
        ObjectId rideId = ride.getId();
        UUID userId = RideTestUtil.NOT_EXISTING_PASSENGER_ID;

        when(rideRepository.findById(rideId))
                .thenReturn(Optional.of(ride));
        when(userRoleExtractor.extractCurrentUserRole())
                .thenReturn(UserRole.PASSENGER);
        when(securityUtil.getUserIdFromSecurityContext())
                .thenReturn(userId);
        doThrow(new ForbiddenException("error"))
                .when(rideValidator).validatePassengerParticipation(ride, userId);

        assertThatThrownBy(
                () -> ridePassengerService.getRide(passengerId, rideId))
                .isInstanceOf(ForbiddenException.class);

        verify(rideRepository).findById(rideId);
        verify(userRoleExtractor).extractCurrentUserRole();
        verify(securityUtil).getUserIdFromSecurityContext();
        verify(rideValidator).validatePassengerParticipation(ride, userId);
        verifyNoMoreInteractions(rideRepository, rideValidator);
        verifyNoInteractions(rideMapper);
    }

    @Test
    void getRide_ShouldThrowForbiddenException_WhenPassengerTriesToGetRideOfOtherPassenger() {
        Ride ride = RideTestUtil.buildDefaultRide();
        UUID passengerId = ride.getPassengerId();
        ObjectId rideId = ride.getId();
        UUID userId = RideTestUtil.NOT_EXISTING_PASSENGER_ID;

        when(rideRepository.findById(rideId))
                .thenReturn(Optional.of(ride));
        when(userRoleExtractor.extractCurrentUserRole())
                .thenReturn(UserRole.PASSENGER);
        when(securityUtil.getUserIdFromSecurityContext())
                .thenReturn(userId);

        assertThatThrownBy(
                () -> ridePassengerService.getRide(passengerId, rideId))
                .isInstanceOf(ForbiddenException.class);

        verify(rideRepository).findById(rideId);
        verify(userRoleExtractor).extractCurrentUserRole();
        verify(securityUtil).getUserIdFromSecurityContext();
        verifyNoMoreInteractions(rideRepository, rideValidator);
        verifyNoInteractions(rideValidator, rideMapper);
    }

    @Test
    void getRide_ShouldReturnRide_WhenUserIsRideParticipant() {
        Ride ride = RideTestUtil.buildDefaultRide();
        UUID passengerId = ride.getPassengerId();
        RideDto rideDto = RideTestUtil.buildRideDto();
        ObjectId rideId = ride.getId();
        UUID userId = ride.getPassengerId();

        when(rideRepository.findById(rideId))
                .thenReturn(Optional.of(ride));
        when(userRoleExtractor.extractCurrentUserRole())
                .thenReturn(UserRole.PASSENGER);
        when(securityUtil.getUserIdFromSecurityContext())
                .thenReturn(userId);
        doNothing().when(rideValidator).validatePassengerParticipation(ride, userId);
        when(rideMapper.entityToDto(ride))
                .thenReturn(rideDto);

        RideDto actual = ridePassengerService.getRide(passengerId, rideId);

        assertThat(actual).isEqualTo(rideDto);

        verify(rideRepository).findById(rideId);
        verify(userRoleExtractor).extractCurrentUserRole();
        verify(securityUtil).getUserIdFromSecurityContext();
        verify(rideValidator).validatePassengerParticipation(ride, userId);
        verify(rideMapper).entityToDto(ride);
        verifyNoMoreInteractions(rideValidator);
    }

    @Test
    void updateRide_ShouldThrowResourceNotFoundException_WhenRideIsNotFound() {
        RideUpdatingDto updatingDto = RideTestUtil.buildRideUpdatingDto();
        ObjectId rideId = RideTestUtil.NOT_EXISTING_ID;
        UUID passengerId = RideTestUtil.PASSENGER_ID;

        when(rideRepository.findById(rideId))
                .thenReturn(Optional.empty());

        assertThatThrownBy(
                () -> ridePassengerService.updateRide(passengerId, rideId, updatingDto))
                .isInstanceOf(ResourceNotFoundException.class);

        verify(rideRepository).findById(rideId);
        verifyNoMoreInteractions(rideRepository);
        verifyNoInteractions(rideMapper);
    }

    @Test
    void updateRide_ShouldThrowForbiddenException_WhenPassengerTriesToUpdateRideOfOtherPassenger() {
        RideUpdatingDto updatingDto = RideTestUtil.buildRideUpdatingDto();
        Ride ride = RideTestUtil.buildDefaultRide().toBuilder()
                .status(RideStatus.REQUESTED)
                .build();
        UUID passengerId = ride.getPassengerId();
        UUID userId = RideTestUtil.NOT_EXISTING_PASSENGER_ID;
        ObjectId rideId = ride.getId();

        when(rideRepository.findById(rideId))
                .thenReturn(Optional.of(ride));
        when(securityUtil.getUserIdFromSecurityContext())
                .thenReturn(userId);

        assertThatThrownBy(
                () -> ridePassengerService.updateRide(passengerId, rideId, updatingDto))
                .isInstanceOf(ForbiddenException.class);

        verify(rideRepository).findById(rideId);
        verify(securityUtil).getUserIdFromSecurityContext();
        verifyNoMoreInteractions(rideRepository);
        verifyNoInteractions(rideValidator, rideMapper);
    }

    @Test
    void updateRide_ShouldThrowForbiddenException_WhenPassengerIsNotRideParticipant() {
        RideUpdatingDto updatingDto = RideTestUtil.buildRideUpdatingDto();
        Ride ride = RideTestUtil.buildDefaultRide().toBuilder()
                .status(RideStatus.REQUESTED)
                .build();
        UUID passengerId = ride.getPassengerId();
        ObjectId rideId = ride.getId();

        when(rideRepository.findById(rideId))
                .thenReturn(Optional.of(ride));
        when(securityUtil.getUserIdFromSecurityContext())
                .thenReturn(passengerId);
        doThrow(new ForbiddenException("error"))
                .when(rideValidator).validatePassengerParticipation(ride, passengerId);

        assertThatThrownBy(
                () -> ridePassengerService.updateRide(passengerId, rideId, updatingDto))
                .isInstanceOf(ForbiddenException.class);

        verify(rideRepository).findById(rideId);
        verify(securityUtil).getUserIdFromSecurityContext();
        verify(rideValidator).validatePassengerParticipation(ride, passengerId);
        verifyNoMoreInteractions(rideRepository);
        verifyNoInteractions(rideMapper);
    }

    @Test
    void updateRide_ShouldThrowBadRequestException_WhenRideIsAlreadyRequested() {
        RideUpdatingDto updatingDto = RideTestUtil.buildRideUpdatingDto();
        Ride ride = RideTestUtil.buildDefaultRide().toBuilder()
                .status(RideStatus.REQUESTED)
                .build();
        UUID passengerId = ride.getPassengerId();
        ObjectId rideId = ride.getId();

        when(rideRepository.findById(rideId))
                .thenReturn(Optional.of(ride));
        when(securityUtil.getUserIdFromSecurityContext())
                .thenReturn(passengerId);
        doNothing().when(rideValidator).validatePassengerParticipation(ride, passengerId);

        assertThatThrownBy(
                () -> ridePassengerService.updateRide(passengerId, rideId, updatingDto))
                .isInstanceOf(BadRequestException.class);

        verify(rideRepository).findById(rideId);
        verify(securityUtil).getUserIdFromSecurityContext();
        verify(rideValidator).validatePassengerParticipation(ride, passengerId);
        verifyNoMoreInteractions(rideRepository);
        verifyNoInteractions(rideMapper);
    }

    @Test
    void updateRide_ShouldUpdateRide_WhenRideIsNotRequestedYet() {
        RideUpdatingDto updatingDto = RideTestUtil.buildRideUpdatingDto();
        Ride ride = RideTestUtil.buildDefaultRide().toBuilder()
                .status(RideStatus.PREPARED)
                .build();
        RideDto rideDto = RideTestUtil.buildRideDto();
        UUID passengerId = ride.getPassengerId();
        ObjectId rideId = ride.getId();

        when(rideRepository.findById(rideId))
                .thenReturn(Optional.of(ride));
        when(securityUtil.getUserIdFromSecurityContext())
                .thenReturn(passengerId);
        doNothing().when(rideValidator).validatePassengerParticipation(ride, passengerId);
        doNothing().when(rideMapper).updateEntityFromDto(updatingDto, ride);
        when(rideRepository.save(ride))
                .thenReturn(ride);
        when(rideMapper.entityToDto(ride))
                .thenReturn(rideDto);

        RideDto actual = ridePassengerService.updateRide(passengerId, rideId, updatingDto);

        assertThat(actual).isEqualTo(rideDto);

        verify(rideRepository).findById(rideId);
        verify(securityUtil).getUserIdFromSecurityContext();
        verify(rideValidator).validatePassengerParticipation(ride, passengerId);
        verify(rideMapper).updateEntityFromDto(updatingDto, ride);
        verify(rideRepository).save(ride);
        verify(rideMapper).entityToDto(ride);
    }

    @Test
    void changeRideStatus_ShouldThrowResourceNotFoundException_WhenRideIsNotFound() {
        ObjectId rideId = RideTestUtil.NOT_EXISTING_ID;
        RideStatus status = RideStatus.ARRIVING;
        UUID passengerId = RideTestUtil.PASSENGER_ID;

        when(rideRepository.findById(rideId))
                .thenReturn(Optional.empty());

        assertThatThrownBy(
                () -> ridePassengerService.changeRideStatus(passengerId, rideId, status))
                .isInstanceOf(ResourceNotFoundException.class);

        verify(rideRepository).findById(rideId);
        verifyNoMoreInteractions(rideRepository);
        verifyNoInteractions(userRoleExtractor, rideStatusChangingManager, rideMapper);
    }

    @Test
    void changeRideStatus_ShouldChangeRideStatus_WhenRideIsFound() {
        Ride ride = RideTestUtil.buildDefaultRide();
        UUID passengerId = ride.getPassengerId();
        RideDto rideDto = RideTestUtil.buildRideDto();
        ObjectId rideId = ride.getId();
        RideStatus status = RideStatus.ARRIVING;
        UserRole userRole = UserRole.PASSENGER;

        when(rideRepository.findById(rideId))
                .thenReturn(Optional.of(ride));
        doNothing().when(rideStatusChangingManager).processRideStatusChanging(ride, userRole, status);
        when(rideRepository.save(ride))
                .thenReturn(ride);
        when(rideMapper.entityToDto(ride))
                .thenReturn(rideDto);

        RideDto actual = ridePassengerService.changeRideStatus(passengerId, rideId, status);

        assertThat(actual).isEqualTo(rideDto);

        verify(rideRepository).findById(rideId);
        verify(rideStatusChangingManager).processRideStatusChanging(ride, userRole, status);
        verify(rideRepository).save(ride);
        verify(rideMapper).entityToDto(ride);
    }
}
