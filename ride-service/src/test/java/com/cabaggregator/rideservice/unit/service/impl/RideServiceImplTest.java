package com.cabaggregator.rideservice.unit.service.impl;

import com.cabaggregator.rideservice.client.dto.PriceCalculationRequest;
import com.cabaggregator.rideservice.core.dto.page.PageDto;
import com.cabaggregator.rideservice.core.dto.price.PriceRecalculationDto;
import com.cabaggregator.rideservice.core.dto.ride.RideAddingDto;
import com.cabaggregator.rideservice.core.dto.ride.RideDto;
import com.cabaggregator.rideservice.core.dto.route.RouteSummary;
import com.cabaggregator.rideservice.core.enums.RideStatus;
import com.cabaggregator.rideservice.core.enums.sort.RideSortField;
import com.cabaggregator.rideservice.entity.Ride;
import com.cabaggregator.rideservice.exception.BadRequestException;
import com.cabaggregator.rideservice.exception.ForbiddenException;
import com.cabaggregator.rideservice.exception.ResourceNotFoundException;
import com.cabaggregator.rideservice.mapper.PageMapper;
import com.cabaggregator.rideservice.mapper.RideMapper;
import com.cabaggregator.rideservice.repository.RideRepository;
import com.cabaggregator.rideservice.security.util.SecurityUtil;
import com.cabaggregator.rideservice.service.PriceService;
import com.cabaggregator.rideservice.service.RouteService;
import com.cabaggregator.rideservice.service.impl.RideServiceImpl;
import com.cabaggregator.rideservice.strategy.manager.RideStatusChangingManager;
import com.cabaggregator.rideservice.util.PageRequestBuilder;
import com.cabaggregator.rideservice.util.PaginationTestUtil;
import com.cabaggregator.rideservice.util.PriceTestUtil;
import com.cabaggregator.rideservice.util.RideTestUtil;
import com.cabaggregator.rideservice.util.RouteTestUtil;
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
import static org.mockito.ArgumentMatchers.anyList;
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
    void getPageOfRequestedRides_ShouldReturnPageDto_WhenCalledWithValidParameters() {
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
                    rideService.getPageOfRequestedRides(offset, limit, sortBy, sortOrder);

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
    void getRide_ShouldReturnRide_WhenRideExists() {
        Ride ride = RideTestUtil.buildDefaultRide();
        RideDto rideDto = RideTestUtil.buildRideDto();
        ObjectId rideId = ride.getId();

        when(rideRepository.findById(rideId))
                .thenReturn(Optional.of(ride));
        when(rideMapper.entityToDto(ride))
                .thenReturn(rideDto);

        RideDto actual = rideService.getRide(rideId);

        assertThat(actual).isEqualTo(rideDto);

        verify(rideRepository).findById(rideId);
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
        RouteSummary routeSummary = RouteTestUtil.buildRouteSummary();
        PriceCalculationRequest priceCalculationRequest = PriceTestUtil.buildPriceCalculationRequest();
        PriceRecalculationDto priceRecalculationDto = PriceTestUtil.buildPriceRecalculationDto();

        doNothing().when(rideValidator).validateAddresses(addingDto.pickUpAddress(), addingDto.dropOffAddress());
        when(securityUtil.getUserIdFromSecurityContext())
                .thenReturn(userId);
        doNothing().when(rideValidator).validatePassengerFreedom(userId);
        when(rideMapper.dtoToEntity(addingDto))
                .thenReturn(ride);
        when(routeService.getRouteSummary(anyList()))
                .thenReturn(routeSummary);
        when(rideRepository.save(ride))
                .thenReturn(ride);
        when(rideMapper.entityToPriceCalculationRequest(ride))
                .thenReturn(priceCalculationRequest);
        when(priceService.calculateBasePrice(priceCalculationRequest))
                .thenReturn(PriceTestUtil.PRICE);
        when(rideMapper.entityToPriceRecalculationDto(ride))
                .thenReturn(priceRecalculationDto);
        when(priceService.recalculatePriceWithDiscount(priceRecalculationDto))
                .thenReturn(PriceTestUtil.PRICE);
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
        verify(routeService).getRouteSummary(anyList());
        verify(priceService).calculateBasePrice(priceCalculationRequest);
        verify(priceService).recalculatePriceWithDiscount(priceRecalculationDto);
        verify(rideRepository, times(2)).save(ride);
        verify(rideMapper).entityToDto(ride);
    }
}
