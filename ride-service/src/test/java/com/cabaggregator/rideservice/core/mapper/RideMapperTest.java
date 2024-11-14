package com.cabaggregator.rideservice.core.mapper;

import com.cabaggregator.rideservice.RideTestUtil;
import com.cabaggregator.rideservice.core.dto.ride.RideDto;
import com.cabaggregator.rideservice.core.dto.ride.order.RideOrderAddingDto;
import com.cabaggregator.rideservice.core.dto.ride.order.RideOrderUpdatingDto;
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
        assertThat(result.price()).isEqualTo(rideDto.price());
        assertThat(result.startTime()).isEqualTo(rideDto.startTime());
        assertThat(result.endTime()).isEqualTo(rideDto.endTime());
    }

    @Test
    void entityToDto_ShouldReturnNull_WhenEntityIsNull() {
        assertThat(mapper.entityToDto(null)).isNull();
    }

    @Test
    void dtoToEntity_ShouldConvertDtoToEntity_WhenDtoIsNotNull() {
        Ride ride = RideTestUtil.buildRide();
        RideOrderAddingDto rideOrderAddingDto = RideTestUtil.buildOrderAddingDto();

        Ride result = mapper.dtoToEntity(rideOrderAddingDto);

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
    void updateEntityFromOrderDto_ShouldUpdateEntity_WhenDtoIsNotNull() {
        Ride ride = RideTestUtil.buildRide();
        RideOrderUpdatingDto rideOrderUpdatingDto = RideTestUtil.buildOrderUpdatingDto();

        mapper.updateEntityFromOrderDto(rideOrderUpdatingDto, ride);

        assertThat(ride).isNotNull();
        assertThat(ride.getId()).isEqualTo(RideTestUtil.RIDE_ID);
        assertThat(ride.getPaymentMethod()).isEqualTo(RideTestUtil.UPDATED_PAYMENT_METHOD);
        assertThat(ride.getPickupAddress()).isEqualTo(RideTestUtil.UPDATED_PICKUP_ADDRESS);
        assertThat(ride.getDestinationAddress()).isEqualTo(RideTestUtil.UPDATED_DESTINATION_ADDRESS);
    }

    @Test
    void updateEntityFromOrderDto_ShouldThrowNullPointerException_WhenDtoIsNull() {
        RideOrderUpdatingDto rideOrderUpdatingDto = RideTestUtil.buildOrderUpdatingDto();

        assertThatThrownBy(() -> mapper.updateEntityFromOrderDto(rideOrderUpdatingDto, null))
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
            assertThat(convertedDto.price()).isEqualTo(expectedDto.price());
            assertThat(convertedDto.startTime()).isEqualTo(expectedDto.startTime());
            assertThat(convertedDto.endTime()).isEqualTo(expectedDto.endTime());
        }
    }

    @Test
    void entityListToDtoList_ShouldReturnEmptyList_WhenEntityListIsEmpty() {
        List<Ride> entityList = Collections.emptyList();

        List<RideDto> result = mapper.entityListToDtoList(entityList);

        assertThat(result)
                .isNotNull()
                .isEmpty();
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

