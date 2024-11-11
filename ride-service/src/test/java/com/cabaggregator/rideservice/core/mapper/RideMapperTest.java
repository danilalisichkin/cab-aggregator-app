package com.cabaggregator.rideservice.core.mapper;

import com.cabaggregator.rideservice.PromoCodeTestUtil;
import com.cabaggregator.rideservice.RideTestUtil;
import com.cabaggregator.rideservice.core.dto.ride.RideDto;
import com.cabaggregator.rideservice.core.dto.ride.RideUpdatingDto;
import com.cabaggregator.rideservice.core.dto.ride.booking.RideBookingAddingDto;
import com.cabaggregator.rideservice.core.dto.ride.booking.RideBookingUpdatingDto;
import com.cabaggregator.rideservice.entity.Ride;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mapstruct.factory.Mappers;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@Tag("unit")
@ExtendWith(MockitoExtension.class)
class RideMapperTest {
    private final RideMapper mapper = Mappers.getMapper(RideMapper.class);

    @Test
    void entityToDto_ShouldConvertEntityToDto_WhenEntityIsNotNull() {
        Ride ride = RideTestUtil.buildRide();
        RideDto rideDto = RideTestUtil.buildRideDto();

        RideDto result = mapper.entityToDto(ride);

        assertThat(result).isNotNull();
        assertThat(result.id()).isEqualTo(rideDto.id());
        assertThat(result.passengerId()).isEqualTo(rideDto.passengerId());
        assertThat(result.driverId()).isEqualTo(rideDto.driverId());
        assertThat(result.promoCode()).isNull();
        assertThat(result.serviceCategory()).isEqualTo(rideDto.serviceCategory());
        assertThat(result.status()).isEqualTo(rideDto.status());
        assertThat(result.paymentMethod()).isEqualTo(rideDto.paymentMethod());
        assertThat(result.pickupAddress()).isEqualTo(rideDto.pickupAddress());
        assertThat(result.destinationAddress()).isEqualTo(rideDto.destinationAddress());
        assertThat(result.cost()).isEqualTo(rideDto.cost());
        assertThat(result.startTime()).isEqualTo(rideDto.startTime());
        assertThat(result.endTime()).isEqualTo(rideDto.endTime());
    }

    @Test
    void entityToDto_ShouldReturnNull_WhenEntityIsNull() {
        assertThat(mapper.entityToDto(null)).isNull();
    }

    @Test
    void updateEntityFromDto_ShouldUpdateEntity_WhenDtoIsNotNull() {
        Ride ride = RideTestUtil.buildRide();
        RideUpdatingDto rideUpdatingDto = RideTestUtil.buildRideUpdatingDto();

        mapper.updateEntityFromDto(rideUpdatingDto, ride);

        assertThat(ride).isNotNull();
        assertThat(ride.getId()).isEqualTo(RideTestUtil.RIDE_ID);
        assertThat(ride.getPassengerId()).isEqualTo(RideTestUtil.UPDATED_PASSENGER_ID);
        assertThat(ride.getPromoCodeId()).isEqualTo(PromoCodeTestUtil.PROMO_CODE_ID);
        assertThat(ride.getServiceCategory()).isEqualTo(RideTestUtil.UPDATED_SERVICE_CATEGORY);
        assertThat(ride.getStatus()).isEqualTo(RideTestUtil.UPDATED_STATUS);
        assertThat(ride.getPaymentMethod()).isEqualTo(RideTestUtil.UPDATED_PAYMENT_METHOD);
        assertThat(ride.getPickupAddress()).isEqualTo(RideTestUtil.UPDATED_PICKUP_ADDRESS);
        assertThat(ride.getDestinationAddress()).isEqualTo(RideTestUtil.UPDATED_DESTINATION_ADDRESS);
        assertThat(ride.getCost()).isEqualTo(RideTestUtil.UPDATED_COST);
        assertThat(ride.getStartTime()).isEqualTo(RideTestUtil.UPDATED_START_TIME);
        assertThat(ride.getEndTime()).isEqualTo(RideTestUtil.UPDATED_END_TIME);
    }

    @Test
    void updateEntityFromDto_ShouldThrowNullPointerException_WhenDtoIsNull() {
        RideUpdatingDto rideUpdatingDto = RideTestUtil.buildRideUpdatingDto();

        assertThatThrownBy(() -> mapper.updateEntityFromDto(rideUpdatingDto, null))
                .isInstanceOf(NullPointerException.class);
    }

    @Test
    void dtoToEntity_ShouldConvertDtoToEntity_WhenDtoIsNotNull() {
        Ride ride = RideTestUtil.buildRide();
        RideBookingAddingDto rideBookingAddingDto = RideTestUtil.buildBookingAddingDto();

        Ride result = mapper.dtoToEntity(rideBookingAddingDto);

        assertThat(result).isNotNull();
        assertThat(result.getId()).isNull();
        assertThat(result.getPassengerId()).isEqualTo(ride.getPassengerId());
        assertThat(result.getServiceCategory()).isEqualTo(ride.getServiceCategory());
        assertThat(result.getPaymentMethod()).isEqualTo(ride.getPaymentMethod());
        assertThat(result.getPickupAddress()).isEqualTo(ride.getPickupAddress());
        assertThat(result.getDestinationAddress()).isEqualTo(ride.getDestinationAddress());
    }

    @Test
    void dtoToEntity_ShouldReturnNull_WhenDtoIsNull() {
        assertThat(mapper.dtoToEntity(null)).isNull();
    }

    @Test
    void updateEntityFromBookingDto_ShouldUpdateEntity_WhenDtoIsNotNull() {
        Ride ride = RideTestUtil.buildRide();
        RideBookingUpdatingDto rideBookingUpdatingDto = RideTestUtil.buildBookingUpdatingDto();

        mapper.updateEntityFromBookingDto(rideBookingUpdatingDto, ride);

        assertThat(ride).isNotNull();
        assertThat(ride.getId()).isEqualTo(RideTestUtil.RIDE_ID);
        assertThat(ride.getPassengerId()).isEqualTo(RideTestUtil.UPDATED_PASSENGER_ID);
        assertThat(ride.getPaymentMethod()).isEqualTo(RideTestUtil.UPDATED_PAYMENT_METHOD);
        assertThat(ride.getPickupAddress()).isEqualTo(RideTestUtil.UPDATED_PICKUP_ADDRESS);
        assertThat(ride.getDestinationAddress()).isEqualTo(RideTestUtil.UPDATED_DESTINATION_ADDRESS);
    }

    @Test
    void updateEntityFromBookingDto_ShouldThrowNullPointerException_WhenDtoIsNull() {
        RideBookingUpdatingDto rideBookingUpdatingDto = RideTestUtil.buildBookingUpdatingDto();

        assertThatThrownBy(() -> mapper.updateEntityFromBookingDto(rideBookingUpdatingDto, null))
                .isInstanceOf(NullPointerException.class);
    }

    @Test
    void entityListToDtoList_ShouldConvertEntityListToDtoList_WhenEntityListIsNotEmpty() {
        Ride ride = RideTestUtil.buildRide();
        RideDto rideDto = RideTestUtil.buildRideDto();

        List<Ride> entityList = Arrays.asList(ride, ride);
        List<RideDto> expectedDtoList = Arrays.asList(rideDto, rideDto);

        List<RideDto> result = mapper.entityListToDtoList(entityList);

        for (int i = 0; i < result.size(); i++) {
            RideDto convertedDto = result.get(i);
            RideDto expectedDto = expectedDtoList.get(i);

            assertThat(convertedDto).isNotNull();
            assertThat(convertedDto.id()).isEqualTo(expectedDto.id());
            assertThat(convertedDto.passengerId()).isEqualTo(expectedDto.passengerId());
            assertThat(convertedDto.driverId()).isEqualTo(expectedDto.driverId());
            assertThat(convertedDto.promoCode()).isNull();
            assertThat(convertedDto.serviceCategory()).isEqualTo(expectedDto.serviceCategory());
            assertThat(convertedDto.status()).isEqualTo(expectedDto.status());
            assertThat(convertedDto.paymentMethod()).isEqualTo(expectedDto.paymentMethod());
            assertThat(convertedDto.pickupAddress()).isEqualTo(expectedDto.pickupAddress());
            assertThat(convertedDto.destinationAddress()).isEqualTo(expectedDto.destinationAddress());
            assertThat(convertedDto.cost()).isEqualTo(expectedDto.cost());
            assertThat(convertedDto.startTime()).isEqualTo(expectedDto.startTime());
            assertThat(convertedDto.endTime()).isEqualTo(expectedDto.endTime());
        }
    }

    @Test
    void entityListToDtoList_ShouldReturnEmptyList_WhenEntityListIsEmpty() {
        List<Ride> entityList = Collections.emptyList();

        List<RideDto> result = mapper.entityListToDtoList(entityList);

        assertThat(result).isNotNull();
        assertThat(result).isEmpty();
    }

    @Test
    void entityListToDtoList_ShouldReturnNull_WhenEntityListIsNull() {
        assertThat(mapper.entityListToDtoList(null)).isNull();
    }

    @Test
    void entityPageToDtoPage_ShouldConvertEntityPageToDtoPage_WhenPageIsNotNull() {
        Ride ride = RideTestUtil.buildRide();

        List<Ride> entityList = Arrays.asList(ride, ride);
        Page<Ride> entityPage = new PageImpl<>(entityList);

        final int expectedPage = 0;
        final int expectedTotalPages = 1;
        final long expectedTotalItems = 2;
        final int expectedItemsOnPage = 2;
        final int expectedPassengersListSize = 2;

        Page<RideDto> result = mapper.entityPageToDtoPage(entityPage);

        assertThat(result).isNotNull();
        assertThat(result.getNumber()).isEqualTo(expectedPage);
        assertThat(result.getTotalPages()).isEqualTo(expectedTotalPages);
        assertThat(result.getTotalElements()).isEqualTo(expectedTotalItems);
        assertThat(result.getNumberOfElements()).isEqualTo(expectedItemsOnPage);
        assertThat(result.getContent()).hasSize(expectedPassengersListSize);
    }

    @Test
    void entityPageToDtoPage_ShouldReturnNull_WhenPageIsNull() {
        Page<RideDto> result = mapper.entityPageToDtoPage(null);

        assertThat(result).isNull();
    }
}

