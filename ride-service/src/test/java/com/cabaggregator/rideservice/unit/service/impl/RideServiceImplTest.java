package com.cabaggregator.rideservice.unit.service.impl;

import com.cabaggregator.rideservice.core.dto.page.PageDto;
import com.cabaggregator.rideservice.core.dto.ride.RideAddingDto;
import com.cabaggregator.rideservice.core.dto.ride.RideDto;
import com.cabaggregator.rideservice.core.dto.ride.RideUpdatingDto;
import com.cabaggregator.rideservice.core.enums.PaymentMethod;
import com.cabaggregator.rideservice.core.enums.PaymentStatus;
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
import com.cabaggregator.rideservice.service.PriceService;
import com.cabaggregator.rideservice.service.RouteService;
import com.cabaggregator.rideservice.service.impl.RideServiceImpl;
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
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.mockStatic;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoInteractions;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;

@Tag("unit")
@ExtendWith(MockitoExtension.class)
class RideServiceImplTest {
    @InjectMocks
    private RideServiceImpl rideService;

    @Mock
    private RideRepository rideRepository;

    @Mock
    private RideValidator rideValidator;

    @Mock
    private PriceService priceService;

    @Mock
    private RouteService routeService;

    @Mock
    private RideStatusChangingManager rideStatusChangingManager;

    @Mock
    private RideMapper rideMapper;

    @Mock
    private PageMapper pageMapper;

    @Mock
    private SecurityUtil securityUtil;

    @Mock
    private UserRoleExtractor userRoleExtractor;

    @Test
    void getPageOfRides_ShouldReturnPageDto_WhenCalledWithValidParameters() {
        Integer offset = 0;
        Integer limit = 10;
        RideSortField sortBy = RideSortField.ID;
        Sort.Direction sortOrder = Sort.Direction.ASC;

        Ride ride = RideTestUtil.buildDefaultRide();
        RideDto rideDto = RideTestUtil.buildRideDto();

        PageRequest pageRequest = PaginationTestUtil.buildPageRequest(offset, limit, sortBy.getValue(), sortOrder);
        Page<Ride> ridePage = PaginationTestUtil.buildPageFromSingleElement(ride);
        Page<RideDto> rideDtoPage = PaginationTestUtil.buildPageFromSingleElement(rideDto);
        PageDto<RideDto> pageDto = PaginationTestUtil.buildPageDto(rideDtoPage);

        try (MockedStatic<PageRequestBuilder> mockedStatic = mockStatic(PageRequestBuilder.class)) {
            mockedStatic.when(() -> PageRequestBuilder.buildPageRequest(offset, limit, sortBy.getValue(), sortOrder))
                    .thenReturn(pageRequest);
            when(rideRepository.findAll(pageRequest))
                    .thenReturn(ridePage);
            when(rideMapper.entityPageToDtoPage(ridePage))
                    .thenReturn(rideDtoPage);
            when(pageMapper.pageToPageDto(rideDtoPage))
                    .thenReturn(pageDto);

            PageDto<RideDto> actual =
                    rideService.getPageOfRides(offset, limit, sortBy, sortOrder, null);

            assertThat(actual)
                    .isNotNull()
                    .isEqualTo(pageDto);

            mockedStatic.verify(() -> PageRequestBuilder.buildPageRequest(offset, limit, sortBy.getValue(), sortOrder));
            verify(rideRepository).findAll(pageRequest);
            verify(rideRepository, never()).findAllByStatus(any(RideStatus.class), any(Pageable.class));
            verify(rideMapper).entityPageToDtoPage(ridePage);
            verify(pageMapper).pageToPageDto(rideDtoPage);
        }
    }

    @Test
    void getPageOfAvailableRides_ShouldReturnPageDto_WhenCalledWithValidParameters() {
        Integer offset = 0;
        Integer limit = 10;
        RideSortField sortBy = RideSortField.ID;
        Sort.Direction sortOrder = Sort.Direction.ASC;

        Ride ride = RideTestUtil.buildDefaultRide();
        RideDto rideDto = RideTestUtil.buildRideDto();

        PageRequest pageRequest = PaginationTestUtil.buildPageRequest(offset, limit, sortBy.getValue(), sortOrder);
        Page<Ride> ridePage = PaginationTestUtil.buildPageFromSingleElement(ride);
        Page<RideDto> rideDtoPage = PaginationTestUtil.buildPageFromSingleElement(rideDto);
        PageDto<RideDto> pageDto = PaginationTestUtil.buildPageDto(rideDtoPage);

        try (MockedStatic<PageRequestBuilder> mockedStatic = mockStatic(PageRequestBuilder.class)) {
            mockedStatic.when(() -> PageRequestBuilder.buildPageRequest(offset, limit, sortBy.getValue(), sortOrder))
                    .thenReturn(pageRequest);
            when(rideRepository.findAllByStatus(RideStatus.REQUESTED, pageRequest))
                    .thenReturn(ridePage);
            when(rideMapper.entityPageToDtoPage(ridePage))
                    .thenReturn(rideDtoPage);
            when(pageMapper.pageToPageDto(rideDtoPage))
                    .thenReturn(pageDto);

            PageDto<RideDto> actual =
                    rideService.getPageOfAvailableRides(offset, limit, sortBy, sortOrder);

            assertThat(actual)
                    .isNotNull()
                    .isEqualTo(pageDto);

            mockedStatic.verify(() -> PageRequestBuilder.buildPageRequest(offset, limit, sortBy.getValue(), sortOrder));
            verify(rideRepository).findAllByStatus(RideStatus.REQUESTED, pageRequest);
            verify(rideMapper).entityPageToDtoPage(ridePage);
            verify(pageMapper).pageToPageDto(rideDtoPage);
        }
    }

    @Test
    void getPageOfDriverRides_ShouldReturnPageDto_WhenCalledWithValidParameters() {
        Integer offset = 0;
        Integer limit = 10;
        RideSortField sortBy = RideSortField.ID;
        Sort.Direction sortOrder = Sort.Direction.ASC;

        Ride ride = RideTestUtil.buildDefaultRide();
        UUID driverId = ride.getDriverId();
        RideDto rideDto = RideTestUtil.buildRideDto();

        PageRequest pageRequest = PaginationTestUtil.buildPageRequest(offset, limit, sortBy.getValue(), sortOrder);
        Page<Ride> ridePage = PaginationTestUtil.buildPageFromSingleElement(ride);
        Page<RideDto> rideDtoPage = PaginationTestUtil.buildPageFromSingleElement(rideDto);
        PageDto<RideDto> pageDto = PaginationTestUtil.buildPageDto(rideDtoPage);

        try (MockedStatic<PageRequestBuilder> mockedStatic = mockStatic(PageRequestBuilder.class)) {
            when(userRoleExtractor.extractCurrentUserRole())
                    .thenReturn(UserRole.DRIVER);
            when(securityUtil.getUserIdFromSecurityContext())
                    .thenReturn(driverId);
            mockedStatic.when(() -> PageRequestBuilder.buildPageRequest(offset, limit, sortBy.getValue(), sortOrder))
                    .thenReturn(pageRequest);
            when(rideRepository.findAllByDriverId(driverId, pageRequest))
                    .thenReturn(ridePage);
            when(rideMapper.entityPageToDtoPage(ridePage))
                    .thenReturn(rideDtoPage);
            when(pageMapper.pageToPageDto(rideDtoPage))
                    .thenReturn(pageDto);

            PageDto<RideDto> actual =
                    rideService.getPageOfDriverRides(offset, limit, sortBy, sortOrder, null, driverId);

            assertThat(actual)
                    .isNotNull()
                    .isEqualTo(pageDto);

            verify(userRoleExtractor).extractCurrentUserRole();
            verify(securityUtil).getUserIdFromSecurityContext();
            mockedStatic.verify(() -> PageRequestBuilder.buildPageRequest(offset, limit, sortBy.getValue(), sortOrder));
            verify(rideRepository).findAllByDriverId(driverId, pageRequest);
            verify(rideRepository, never()).findAllByDriverIdAndStatus(driverId, RideStatus.REQUESTED, pageRequest);
            verify(rideMapper).entityPageToDtoPage(ridePage);
            verify(pageMapper).pageToPageDto(rideDtoPage);
        }
    }

    @Test
    void getPageOfDriverRides_ShouldThrowForbiddenException_WhenUserIsNotResourceOwner() {
        Integer offset = 0;
        Integer limit = 10;
        RideSortField sortBy = RideSortField.ID;
        Sort.Direction sortOrder = Sort.Direction.ASC;

        UUID driverId = RideTestUtil.DRIVER_ID;
        UUID userId = RideTestUtil.NOT_EXISTING_DRIVER_ID;

        when(userRoleExtractor.extractCurrentUserRole())
                .thenReturn(UserRole.DRIVER);
        when(securityUtil.getUserIdFromSecurityContext())
                .thenReturn(userId);

        assertThatThrownBy(
                () -> rideService.getPageOfDriverRides(offset, limit, sortBy, sortOrder, null, driverId))
                .isInstanceOf(ForbiddenException.class);

        verify(userRoleExtractor).extractCurrentUserRole();
        verify(securityUtil).getUserIdFromSecurityContext();
        verifyNoInteractions(rideRepository, rideMapper, pageMapper);
    }

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
            when(userRoleExtractor.extractCurrentUserRole())
                    .thenReturn(UserRole.DRIVER);
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
                    rideService.getPageOfPassengerRides(offset, limit, sortBy, sortOrder, null, passengerId);

            assertThat(actual)
                    .isNotNull()
                    .isEqualTo(pageDto);

            verify(userRoleExtractor).extractCurrentUserRole();
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

        when(userRoleExtractor.extractCurrentUserRole())
                .thenReturn(UserRole.PASSENGER);
        when(securityUtil.getUserIdFromSecurityContext())
                .thenReturn(userId);

        assertThatThrownBy(
                () -> rideService.getPageOfPassengerRides(offset, limit, sortBy, sortOrder, null, passengerId))
                .isInstanceOf(ForbiddenException.class);

        verify(userRoleExtractor).extractCurrentUserRole();
        verify(securityUtil).getUserIdFromSecurityContext();
        verifyNoInteractions(rideRepository, rideMapper, pageMapper);
    }

    @Test
    void getRide_ShouldThrowResourceNotFoundException_WhenRideIsNotFound() {
        ObjectId rideId = RideTestUtil.NOT_EXISTING_ID;

        when(rideRepository.findById(rideId))
                .thenReturn(Optional.empty());

        assertThatThrownBy(
                () -> rideService.getRide(rideId))
                .isInstanceOf(ResourceNotFoundException.class);

        verify(rideRepository).findById(rideId);
        verifyNoMoreInteractions(rideRepository);
        verifyNoInteractions(userRoleExtractor, securityUtil, rideValidator, rideMapper);
    }

    @Test
    void getRide_ShouldThrowForbiddenException_WhenUserIsNotRideParticipant() {
        Ride ride = RideTestUtil.buildDefaultRide();
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
                () -> rideService.getRide(rideId))
                .isInstanceOf(ForbiddenException.class);

        verify(rideRepository).findById(rideId);
        verify(userRoleExtractor).extractCurrentUserRole();
        verify(securityUtil).getUserIdFromSecurityContext();
        verify(rideValidator).validatePassengerParticipation(ride, userId);
        verifyNoMoreInteractions(rideRepository, rideValidator);
        verifyNoInteractions(rideMapper);
    }

    @Test
    void getRide_ShouldReturnRide_WhenUserIsRideParticipant() {
        Ride ride = RideTestUtil.buildDefaultRide();
        RideDto rideDto = RideTestUtil.buildRideDto();
        ObjectId rideId = ride.getId();
        UUID userId = ride.getDriverId();

        when(rideRepository.findById(rideId))
                .thenReturn(Optional.of(ride));
        when(userRoleExtractor.extractCurrentUserRole())
                .thenReturn(UserRole.DRIVER);
        when(securityUtil.getUserIdFromSecurityContext())
                .thenReturn(userId);
        doNothing().when(rideValidator).validateDriverParticipation(ride, userId);
        when(rideMapper.entityToDto(ride))
                .thenReturn(rideDto);

        RideDto actual = rideService.getRide(rideId);

        assertThat(actual).isEqualTo(rideDto);

        verify(rideRepository).findById(rideId);
        verify(userRoleExtractor).extractCurrentUserRole();
        verify(securityUtil).getUserIdFromSecurityContext();
        verify(rideValidator).validateDriverParticipation(ride, userId);
        verify(rideMapper).entityToDto(ride);
        verifyNoMoreInteractions(rideValidator);
    }

    @Test
    void createRide_ShouldThrowBadRequestException_WhenAddressesAreNotValid() {
        RideAddingDto addingDto = RideTestUtil.buildRideAddingDto();

        doThrow(new BadRequestException("error"))
                .when(rideValidator).validateAddresses(addingDto.pickUpAddress(), addingDto.dropOffAddress());

        assertThatThrownBy(
                () -> rideService.createRide(addingDto))
                .isInstanceOf(BadRequestException.class);

        verify(rideValidator).validateAddresses(addingDto.pickUpAddress(), addingDto.dropOffAddress());
        verifyNoMoreInteractions(rideValidator);
        verifyNoInteractions(securityUtil, routeService, priceService, rideRepository, rideMapper);
    }

    @Test
    void createRide_ShouldThrowForbiddenException_WhenPassengerIsParticipatingInRideNow() {
        RideAddingDto addingDto = RideTestUtil.buildRideAddingDto();
        UUID userId = RideTestUtil.PASSENGER_ID;

        doNothing().when(rideValidator).validateAddresses(addingDto.pickUpAddress(), addingDto.dropOffAddress());
        when(securityUtil.getUserIdFromSecurityContext())
                .thenReturn(userId);
        doThrow(new ForbiddenException("error"))
                .when(rideValidator).validatePassengerFreedom(userId);

        assertThatThrownBy(
                () -> rideService.createRide(addingDto))
                .isInstanceOf(ForbiddenException.class);

        verify(rideValidator).validateAddresses(addingDto.pickUpAddress(), addingDto.dropOffAddress());
        verify(securityUtil).getUserIdFromSecurityContext();
        verify(rideValidator).validatePassengerFreedom(userId);
        verifyNoInteractions(routeService, priceService, rideRepository, rideMapper);
    }

    @Test
    void createRide_ShouldCreateRide_WhenCalledWithValidParameters() {
        RideAddingDto addingDto = RideTestUtil.buildRideAddingDto();
        UUID userId = RideTestUtil.PASSENGER_ID;
        Ride ride = RideTestUtil.buildDefaultRide();
        RideDto rideDto = RideTestUtil.buildRideDto();


        doNothing().when(rideValidator).validateAddresses(addingDto.pickUpAddress(), addingDto.dropOffAddress());
        when(securityUtil.getUserIdFromSecurityContext())
                .thenReturn(userId);
        doNothing().when(rideValidator).validatePassengerFreedom(userId);
        when(rideMapper.dtoToEntity(addingDto))
                .thenReturn(ride);
        doNothing().when(routeService).setRouteSummary(ride, addingDto);
        when(rideRepository.save(ride))
                .thenReturn(ride);
        doNothing().when(priceService).calculateBasePrice(ride, addingDto);
        doNothing().when(priceService).recalculatePriceWithDiscount(ride, addingDto);
        when(rideRepository.save(ride))
                .thenReturn(ride);
        when(rideMapper.entityToDto(ride))
                .thenReturn(rideDto);

        RideDto actual = rideService.createRide(addingDto);

        assertThat(actual).isEqualTo(rideDto);

        verify(rideValidator).validateAddresses(addingDto.pickUpAddress(), addingDto.dropOffAddress());
        verify(securityUtil).getUserIdFromSecurityContext();
        verify(rideValidator).validatePassengerFreedom(userId);
        verify(rideMapper).dtoToEntity(addingDto);
        verify(routeService).setRouteSummary(ride, addingDto);
        verify(priceService).calculateBasePrice(ride, addingDto);
        verify(priceService).recalculatePriceWithDiscount(ride, addingDto);
        verify(rideRepository, times(2)).save(ride);
        verify(rideMapper).entityToDto(ride);
    }

    @Test
    void updateRide_ShouldThrowResourceNotFoundException_WhenRideIsNotFound() {
        RideUpdatingDto updatingDto = RideTestUtil.buildRideUpdatingDto();
        ObjectId rideId = RideTestUtil.NOT_EXISTING_ID;

        when(rideRepository.findById(rideId))
                .thenReturn(Optional.empty());

        assertThatThrownBy(
                () -> rideService.updateRide(rideId, updatingDto))
                .isInstanceOf(ResourceNotFoundException.class);

        verify(rideRepository).findById(rideId);
        verifyNoMoreInteractions(rideRepository);
        verifyNoInteractions(rideMapper);
    }

    @Test
    void updateRide_ShouldThrowBadRequestException_WhenRideIsAlreadyRequested() {
        RideUpdatingDto updatingDto = RideTestUtil.buildRideUpdatingDto();
        Ride ride = RideTestUtil.buildDefaultRide().toBuilder()
                .status(RideStatus.REQUESTED)
                .build();
        ObjectId rideId = ride.getId();

        when(rideRepository.findById(rideId))
                .thenReturn(Optional.of(ride));

        assertThatThrownBy(
                () -> rideService.updateRide(rideId, updatingDto))
                .isInstanceOf(BadRequestException.class);

        verify(rideRepository).findById(rideId);
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
        ObjectId rideId = ride.getId();

        when(rideRepository.findById(rideId))
                .thenReturn(Optional.of(ride));
        doNothing().when(rideMapper).updateEntityFromOrderDto(updatingDto, ride);
        when(rideRepository.save(ride))
                .thenReturn(ride);
        when(rideMapper.entityToDto(ride))
                .thenReturn(rideDto);

        RideDto actual = rideService.updateRide(rideId, updatingDto);

        assertThat(actual).isEqualTo(rideDto);

        verify(rideRepository).findById(rideId);
        verify(rideMapper).updateEntityFromOrderDto(updatingDto, ride);
        verify(rideRepository).save(ride);
        verify(rideMapper).entityToDto(ride);
    }

    @Test
    void changeRideStatus_ShouldThrowResourceNotFoundException_WhenRideIsNotFound() {
        ObjectId rideId = RideTestUtil.NOT_EXISTING_ID;
        RideStatus status = RideStatus.ARRIVING;

        when(rideRepository.findById(rideId))
                .thenReturn(Optional.empty());

        assertThatThrownBy(
                () -> rideService.changeRideStatus(rideId, status))
                .isInstanceOf(ResourceNotFoundException.class);

        verify(rideRepository).findById(rideId);
        verifyNoMoreInteractions(rideRepository);
        verifyNoInteractions(userRoleExtractor, rideStatusChangingManager, rideMapper);
    }

    @Test
    void changeRideStatus_ShouldChangeRideStatus_WhenRideIsFound() {
        Ride ride = RideTestUtil.buildDefaultRide();
        RideDto rideDto = RideTestUtil.buildRideDto();
        ObjectId rideId = ride.getId();
        RideStatus status = RideStatus.ARRIVING;
        UserRole userRole = UserRole.DRIVER;

        when(rideRepository.findById(rideId))
                .thenReturn(Optional.of(ride));
        when(userRoleExtractor.extractCurrentUserRole())
                .thenReturn(userRole);
        doNothing().when(rideStatusChangingManager).processRideStatusChanging(ride, userRole, status);
        when(rideRepository.save(ride))
                .thenReturn(ride);
        when(rideMapper.entityToDto(ride))
                .thenReturn(rideDto);

        RideDto actual = rideService.changeRideStatus(rideId, status);

        assertThat(actual).isEqualTo(rideDto);

        verify(rideRepository).findById(rideId);
        verify(userRoleExtractor).extractCurrentUserRole();
        verify(rideStatusChangingManager).processRideStatusChanging(ride, userRole, status);
        verify(rideRepository).save(ride);
        verify(rideMapper).entityToDto(ride);
    }

    @Test
    void changeRidePaymentStatus_ShouldThrowResourceNotFoundException_WhenRideIsNotFound() {
        ObjectId rideId = RideTestUtil.NOT_EXISTING_ID;
        PaymentStatus paymentStatus = PaymentStatus.PAID;

        when(rideRepository.findById(rideId))
                .thenReturn(Optional.empty());

        assertThatThrownBy(
                () -> rideService.changeRidePaymentStatus(rideId, paymentStatus))
                .isInstanceOf(ResourceNotFoundException.class);

        verify(rideRepository).findById(rideId);
        verifyNoMoreInteractions(rideRepository);
        verifyNoInteractions(securityUtil, rideValidator, rideMapper);
    }

    @Test
    void changeRidePaymentStatus_ShouldThrowForbiddenException_WhenDriverIsNotRideParticipant() {
        Ride ride = RideTestUtil.buildDefaultRide();
        ObjectId rideId = ride.getId();
        PaymentStatus paymentStatus = PaymentStatus.PAID;
        UUID userId = RideTestUtil.NOT_EXISTING_DRIVER_ID;

        when(rideRepository.findById(rideId))
                .thenReturn(Optional.of(ride));
        when(securityUtil.getUserIdFromSecurityContext())
                .thenReturn(userId);
        doThrow(new ForbiddenException("error")).when(rideValidator).validateDriverParticipation(ride, userId);

        assertThatThrownBy(
                () -> rideService.changeRidePaymentStatus(rideId, paymentStatus))
                .isInstanceOf(ForbiddenException.class);

        verify(rideRepository).findById(rideId);
        verify(securityUtil).getUserIdFromSecurityContext();
        verify(rideValidator).validateDriverParticipation(ride, userId);
        verifyNoMoreInteractions(rideRepository);
        verifyNoInteractions(rideMapper);
    }

    @Test
    void changeRidePaymentStatus_ShouldThrowForbiddenException_WhenDriverTriesToChangePaymentStatusForCardManually() {
        Ride ride = RideTestUtil.buildDefaultRide().toBuilder().build();
        ObjectId rideId = ride.getId();
        PaymentStatus paymentStatus = PaymentStatus.DECLINED;
        UUID userId = ride.getDriverId();

        when(rideRepository.findById(rideId))
                .thenReturn(Optional.of(ride));
        when(securityUtil.getUserIdFromSecurityContext())
                .thenReturn(userId);
        doNothing().when(rideValidator).validateDriverParticipation(ride, userId);

        assertThatThrownBy(
                () -> rideService.changeRidePaymentStatus(rideId, paymentStatus))
                .isInstanceOf(ForbiddenException.class);

        verify(rideRepository).findById(rideId);
        verify(securityUtil).getUserIdFromSecurityContext();
        verify(rideValidator).validateDriverParticipation(ride, userId);
        verifyNoMoreInteractions(rideRepository);
        verifyNoInteractions(rideMapper);
    }

    @Test
    void changeRidePaymentStatus_ShouldChangeRidePaymentStatus_WhenCalledWithValidParameters() {
        Ride ride = RideTestUtil.buildDefaultRide().toBuilder()
                .paymentMethod(PaymentMethod.CASH)
                .build();
        RideDto rideDto = RideTestUtil.buildRideDto();
        ObjectId rideId = ride.getId();
        PaymentStatus paymentStatus = PaymentStatus.PAID_IN_CASH;
        UUID userId = ride.getDriverId();

        when(rideRepository.findById(rideId))
                .thenReturn(Optional.of(ride));
        when(securityUtil.getUserIdFromSecurityContext())
                .thenReturn(userId);
        doNothing().when(rideValidator).validateDriverParticipation(ride, userId);
        when(rideRepository.save(ride))
                .thenReturn(ride);
        when(rideMapper.entityToDto(ride))
                .thenReturn(rideDto);

        RideDto actual = rideService.changeRidePaymentStatus(rideId, paymentStatus);

        assertThat(actual).isEqualTo(rideDto);

        verify(rideRepository).findById(rideId);
        verify(securityUtil).getUserIdFromSecurityContext();
        verify(rideValidator).validateDriverParticipation(ride, userId);
        verify(rideRepository).save(ride);
        verify(rideMapper).entityToDto(ride);
    }
}
