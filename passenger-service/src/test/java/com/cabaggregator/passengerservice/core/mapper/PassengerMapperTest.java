package com.cabaggregator.passengerservice.core.mapper;

import com.cabaggregator.passengerservice.PassengerTestUtil;
import com.cabaggregator.passengerservice.core.dto.passenger.PassengerAddingDto;
import com.cabaggregator.passengerservice.core.dto.passenger.PassengerDto;
import com.cabaggregator.passengerservice.core.dto.passenger.PassengerUpdatingDto;
import com.cabaggregator.passengerservice.entity.Passenger;
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
class PassengerMapperTest {
    private final PassengerMapper mapper = Mappers.getMapper(PassengerMapper.class);

    @Test
    void entityToDto_ShouldConvertEntityToDto_WhenEntityIsNotNull() {
        Passenger passenger = PassengerTestUtil.buildPassenger();
        PassengerDto passengerDto = PassengerTestUtil.buildPassengerDto();

        PassengerDto convertedDto = mapper.entityToDto(passenger);

        assertThat(convertedDto)
                .isNotNull()
                .isEqualTo(passengerDto);
    }

    @Test
    void entityToDto_ShouldReturnNull_WhenEntityIsNull() {
        assertThat(mapper.entityToDto(null)).isNull();
    }

    @Test
    void updateEntityFromDto_ShouldUpdateEntity_WhenDtoIsNotNull() {
        Passenger passenger = PassengerTestUtil.buildPassenger();
        PassengerUpdatingDto passengerUpdatingDto = PassengerTestUtil.buildPassengerUpdatingDto();

        mapper.updateEntityFromDto(passengerUpdatingDto, passenger);

        assertThat(passenger).isNotNull();
        assertThat(passenger.getId()).isEqualTo(PassengerTestUtil.ID);
        assertThat(passenger.getPhoneNumber()).isEqualTo(PassengerTestUtil.UPDATED_PHONE_NUMBER);
        assertThat(passenger.getEmail()).isEqualTo(PassengerTestUtil.UPDATED_EMAIL);
        assertThat(passenger.getFirstName()).isEqualTo(PassengerTestUtil.UPDATED_FIRST_NAME);
        assertThat(passenger.getLastName()).isEqualTo(PassengerTestUtil.UPDATED_LAST_NAME);
        assertThat(passenger.getRating()).isEqualTo(PassengerTestUtil.UPDATED_RATING);
    }

    @Test
    void updateEntityFromDto_ShouldThrowNullPointerException_WhenDtoIsNull() {
        PassengerUpdatingDto passengerUpdatingDto = PassengerTestUtil.buildPassengerUpdatingDto();

        assertThatThrownBy(() -> mapper.updateEntityFromDto(passengerUpdatingDto, null))
                .isInstanceOf(NullPointerException.class);
    }

    @Test
    void dtoToEntity_ShouldConvertDtoToEntity_WhenDtoIsNotNull() {
        Passenger passenger = PassengerTestUtil.buildPassenger();
        PassengerAddingDto passengerAddingDto = PassengerTestUtil.buildPassengerAddingDto();

        Passenger convertedEntity = mapper.dtoToEntity(passengerAddingDto);

        assertThat(convertedEntity).isNotNull();
        assertThat(convertedEntity.getId()).isEqualTo(passenger.getId());
        assertThat(convertedEntity.getPhoneNumber()).isEqualTo(passenger.getPhoneNumber());
        assertThat(convertedEntity.getEmail()).isEqualTo(passenger.getEmail());
        assertThat(convertedEntity.getFirstName()).isEqualTo(passenger.getFirstName());
        assertThat(convertedEntity.getLastName()).isEqualTo(passenger.getLastName());
        assertThat(convertedEntity.getRating()).isNull();
    }

    @Test
    void dtoToEntity_ShouldReturnNull_WhenDtoIsNull() {
        assertThat(mapper.dtoToEntity(null)).isNull();
    }

    @Test
    void entityListToDtoList_ShouldConvertEntityListToDtoList_WhenListIsNotNull() {
        Passenger passenger = PassengerTestUtil.buildPassenger();
        PassengerDto passengerDto = PassengerTestUtil.buildPassengerDto();

        List<Passenger> passengers = Arrays.asList(passenger, passenger);
        List<PassengerDto> expectedList = Arrays.asList(passengerDto, passengerDto);

        List<PassengerDto> result = mapper.entityListToDtoList(passengers);

        assertThat(result)
                .isNotNull()
                .isEqualTo(expectedList);
    }

    @Test
    void entityListToDtoList_ShouldReturnEmptyList_WhenEntityListIsEmpty() {
        List<Passenger> passengers = Collections.emptyList();

        List<PassengerDto> result = mapper.entityListToDtoList(passengers);

        assertThat(result)
                .isNotNull()
                .isEmpty();
    }

    @Test
    void entityListToDtoList_ShouldReturnNull_WhenListIsNull() {
        assertThat(mapper.entityListToDtoList(null)).isNull();
    }

    @Test
    void entityPageToDtoPage_ShouldConvertEntityPageToDtoPage_WhenPageIsNotNull() {
        Passenger passenger = PassengerTestUtil.buildPassenger();

        List<Passenger> passengers = Arrays.asList(passenger, passenger);
        Page<Passenger> passengersPage = new PageImpl<>(passengers);

        final int expectedPage = 0;
        final int expectedTotalPages = 1;
        final long expectedTotalItems = 2;
        final int expectedItemsOnPage = 2;
        final int expectedPassengersListSize = 2;

        Page<PassengerDto> result = mapper.entityPageToDtoPage(passengersPage);

        assertThat(result).isNotNull();
        assertThat(result.getNumber()).isEqualTo(expectedPage);
        assertThat(result.getTotalPages()).isEqualTo(expectedTotalPages);
        assertThat(result.getTotalElements()).isEqualTo(expectedTotalItems);
        assertThat(result.getNumberOfElements()).isEqualTo(expectedItemsOnPage);
        assertThat(result.getContent()).hasSize(expectedPassengersListSize);
    }
}
