package com.cabaggregator.rideservice.unit.core.mapper;

import com.cabaggregator.rideservice.core.RideAddingDto;
import com.cabaggregator.rideservice.core.RideDto;
import com.cabaggregator.rideservice.core.RideUpdatingDto;
import com.cabaggregator.rideservice.entity.Ride;
import com.cabaggregator.rideservice.mapper.RideMapper;
import com.cabaggregator.rideservice.util.PaginationTestUtil;
import com.cabaggregator.rideservice.util.RideTestUtil;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mapstruct.factory.Mappers;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;

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
        Ride ride = RideTestUtil.getRideBuilder().build();
        RideDto rideDto = RideTestUtil.buildRideDto();

        RideDto actual = mapper.entityToDto(ride);

        assertThat(actual)
                .isNotNull()
                .isEqualTo(rideDto);
    }

    @Test
    void entityToDto_ShouldReturnNull_WhenEntityIsNull() {
        assertThat(mapper.entityToDto(null)).isNull();
    }

    @Test
    void dtoToEntity_ShouldConvertDtoToEntity_WhenDtoIsNotNull() {
        Ride ride = RideTestUtil.getRideBuilder().build();
        RideAddingDto rideAddingDto = RideTestUtil.buildRideAddingDto();

        Ride actual = mapper.dtoToEntity(rideAddingDto);

        assertThat(actual).isNotNull();
        assertThat(actual.getId()).isNull();
        assertThat(actual.getFare()).isEqualTo(rideAddingDto.fare());
        assertThat(actual.getPaymentMethod()).isEqualTo(rideAddingDto.paymentMethod());
        assertThat(actual.getPickUpAddress()).isEqualTo(ride.getPickUpAddress());
        assertThat(actual.getDropOffAddress()).isEqualTo(ride.getDropOffAddress());
    }

    @Test
    void dtoToEntity_ShouldReturnNull_WhenDtoIsNull() {
        assertThat(mapper.dtoToEntity(null)).isNull();
    }

    @Test
    void updateEntityFromOrderDto_ShouldUpdateEntity_WhenDtoIsNotNull() {
        Ride ride = RideTestUtil.getRideBuilder().build();
        RideUpdatingDto rideUpdatingDto = RideTestUtil.buildRideUpdatingDto();

        mapper.updateEntityFromOrderDto(rideUpdatingDto, ride);

        assertThat(ride).isNotNull();
        assertThat(ride.getPaymentMethod()).isEqualTo(RideTestUtil.UPDATED_PAYMENT_METHOD);
        assertThat(ride.getPickUpAddress()).isEqualTo(RideTestUtil.buildUpdatedPickUpAddress());
        assertThat(ride.getDropOffAddress()).isEqualTo(RideTestUtil.buildDropOffAddress());
    }

    @Test
    void updateEntityFromOrderDto_ShouldThrowNullPointerException_WhenDtoIsNull() {
        RideUpdatingDto rideUpdatingDto = RideTestUtil.buildRideUpdatingDto();

        assertThatThrownBy(() -> mapper.updateEntityFromOrderDto(rideUpdatingDto, null))
                .isInstanceOf(NullPointerException.class);
    }

    @Test
    void entityListToDtoList_ShouldConvertEntityListToDtoList_WhenListIsNotNull() {
        Ride ride = RideTestUtil.getRideBuilder().build();
        List<Ride> rides = Arrays.asList(ride, ride);
        RideDto rideDto = RideTestUtil.buildRideDto();
        List<RideDto> expectedList = Arrays.asList(rideDto, rideDto);

        List<RideDto> actual = mapper.entityListToDtoList(rides);

        assertThat(actual)
                .isNotNull()
                .isEqualTo(expectedList);
    }

    @Test
    void entityListToDtoList_ShouldReturnEmptyList_WhenEntityListIsEmpty() {
        List<Ride> rides = Collections.emptyList();

        List<RideDto> actual = mapper.entityListToDtoList(rides);

        assertThat(actual)
                .isNotNull()
                .isEmpty();
    }

    @Test
    void entityListToDtoList_ShouldReturnNull_WhenListIsNull() {
        assertThat(mapper.entityListToDtoList(null)).isNull();
    }

    @Test
    void entityPageToDtoPage_ShouldConvertEntityPageToDtoPage_WhenPageIsNotNull() {
        Ride ride = RideTestUtil.getRideBuilder().build();
        List<Ride> entityList = Arrays.asList(ride, ride);
        Page<Ride> entityPage = PaginationTestUtil.buildPageFromList(entityList);
        RideDto rideDto = RideTestUtil.buildRideDto();
        List<RideDto> dtoList = Arrays.asList(rideDto, rideDto);
        Page<RideDto> expectedDtoPage = PaginationTestUtil.buildPageFromList(dtoList);

        Page<RideDto> actual = mapper.entityPageToDtoPage(entityPage);

        assertThat(actual)
                .isNotNull()
                .isEqualTo(expectedDtoPage);
    }
}

