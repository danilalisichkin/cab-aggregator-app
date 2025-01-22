package com.cabaggregator.rideservice.unit.service.impl;

import com.cabaggregator.rideservice.core.dto.page.PageDto;
import com.cabaggregator.rideservice.core.dto.ride.RideDto;
import com.cabaggregator.rideservice.core.enums.RideStatus;
import com.cabaggregator.rideservice.core.enums.sort.RideSortField;
import com.cabaggregator.rideservice.core.mapper.PageMapper;
import com.cabaggregator.rideservice.core.mapper.RideMapper;
import com.cabaggregator.rideservice.entity.Ride;
import com.cabaggregator.rideservice.exception.ResourceNotFoundException;
import com.cabaggregator.rideservice.repository.RideRepository;
import com.cabaggregator.rideservice.security.enums.UserRole;
import com.cabaggregator.rideservice.service.impl.RideDriverServiceImpl;
import com.cabaggregator.rideservice.strategy.manager.RideStatusChangingManager;
import com.cabaggregator.rideservice.util.PageRequestBuilder;
import com.cabaggregator.rideservice.util.PaginationTestUtil;
import com.cabaggregator.rideservice.util.RideTestUtil;
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
import static org.mockito.Mockito.mockStatic;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoInteractions;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;

@Tag("unit")
@ExtendWith(MockitoExtension.class)
class RideDriverServiceImplTest {
    @InjectMocks
    private RideDriverServiceImpl rideDriverService;

    @Mock
    private RideStatusChangingManager rideStatusChangingManager;

    @Mock
    private RideRepository rideRepository;

    @Mock
    private RideMapper rideMapper;

    @Mock
    private PageMapper pageMapper;

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
            mockedStatic.when(() -> PageRequestBuilder.buildPageRequest(offset, limit, sortBy.getValue(), sortOrder))
                    .thenReturn(pageRequest);
            when(rideRepository.findAllByDriverId(driverId, pageRequest))
                    .thenReturn(ridePage);
            when(rideMapper.entityPageToDtoPage(ridePage))
                    .thenReturn(rideDtoPage);
            when(pageMapper.pageToPageDto(rideDtoPage))
                    .thenReturn(pageDto);

            PageDto<RideDto> actual =
                    rideDriverService.getPageOfDriverRides(driverId, offset, limit, sortBy, sortOrder, null);

            assertThat(actual)
                    .isNotNull()
                    .isEqualTo(pageDto);

            mockedStatic.verify(() -> PageRequestBuilder.buildPageRequest(offset, limit, sortBy.getValue(), sortOrder));
            verify(rideRepository).findAllByDriverId(driverId, pageRequest);
            verify(rideRepository, never()).findAllByDriverIdAndStatus(driverId, RideStatus.REQUESTED, pageRequest);
            verify(rideMapper).entityPageToDtoPage(ridePage);
            verify(pageMapper).pageToPageDto(rideDtoPage);
        }
    }

    @Test
    void getRide_ShouldThrowResourceNotFoundException_WhenRideIsNotFound() {
        UUID driverId = RideTestUtil.DRIVER_ID;
        ObjectId rideId = RideTestUtil.NOT_EXISTING_ID;

        when(rideRepository.findByIdAndDriverId(rideId, driverId))
                .thenReturn(Optional.empty());

        assertThatThrownBy(
                () -> rideDriverService.getRide(driverId, rideId))
                .isInstanceOf(ResourceNotFoundException.class);

        verify(rideRepository).findByIdAndDriverId(rideId, driverId);
        verifyNoMoreInteractions(rideRepository);
        verifyNoInteractions(rideMapper);
    }

    @Test
    void getRide_ShouldReturnRide_WhenUserIsRideParticipant() {
        Ride ride = RideTestUtil.buildDefaultRide();
        UUID driverId = ride.getDriverId();
        RideDto rideDto = RideTestUtil.buildRideDto();
        ObjectId rideId = ride.getId();

        when(rideRepository.findByIdAndDriverId(rideId, driverId))
                .thenReturn(Optional.of(ride));
        when(rideMapper.entityToDto(ride))
                .thenReturn(rideDto);

        RideDto actual = rideDriverService.getRide(driverId, rideId);

        assertThat(actual).isEqualTo(rideDto);

        verify(rideRepository).findByIdAndDriverId(rideId, driverId);
        verify(rideMapper).entityToDto(ride);
    }

    @Test
    void changeRideStatus_ShouldThrowResourceNotFoundException_WhenRideIsNotFound() {
        ObjectId rideId = RideTestUtil.NOT_EXISTING_ID;
        RideStatus status = RideStatus.ARRIVING;
        UUID driverId = RideTestUtil.DRIVER_ID;

        when(rideRepository.findByIdAndDriverId(rideId, driverId))
                .thenReturn(Optional.empty());

        assertThatThrownBy(
                () -> rideDriverService.changeRideStatus(driverId, rideId, status))
                .isInstanceOf(ResourceNotFoundException.class);

        verify(rideRepository).findByIdAndDriverId(rideId, driverId);
        verifyNoMoreInteractions(rideRepository);
        verifyNoInteractions(rideStatusChangingManager, rideMapper);
    }

    @Test
    void changeRideStatus_ShouldChangeRideStatus_WhenRideIsFound() {
        Ride ride = RideTestUtil.buildDefaultRide();
        UUID driverId = ride.getDriverId();
        RideDto rideDto = RideTestUtil.buildRideDto();
        ObjectId rideId = ride.getId();
        RideStatus status = RideStatus.ARRIVING;
        UserRole userRole = UserRole.DRIVER;

        when(rideRepository.findByIdAndDriverId(rideId, driverId))
                .thenReturn(Optional.of(ride));
        doNothing().when(rideStatusChangingManager).processRideStatusChanging(ride, userRole, status);
        when(rideRepository.save(ride))
                .thenReturn(ride);
        when(rideMapper.entityToDto(ride))
                .thenReturn(rideDto);

        RideDto actual = rideDriverService.changeRideStatus(driverId, rideId, status);

        assertThat(actual).isEqualTo(rideDto);

        verify(rideRepository).findByIdAndDriverId(rideId, driverId);
        verify(rideStatusChangingManager).processRideStatusChanging(ride, userRole, status);
        verify(rideRepository).save(ride);
        verify(rideMapper).entityToDto(ride);
    }
}
