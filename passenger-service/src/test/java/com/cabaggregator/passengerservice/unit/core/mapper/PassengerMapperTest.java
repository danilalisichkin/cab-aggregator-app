package com.cabaggregator.passengerservice.unit.core.mapper;

import com.cabaggregator.passengerservice.core.dto.passenger.PassengerAddingDto;
import com.cabaggregator.passengerservice.core.dto.passenger.PassengerDto;
import com.cabaggregator.passengerservice.core.dto.passenger.PassengerUpdatingDto;
import com.cabaggregator.passengerservice.core.mapper.PassengerMapper;
import com.cabaggregator.passengerservice.entity.Passenger;
import com.cabaggregator.passengerservice.util.PaginationTestUtil;
import com.cabaggregator.passengerservice.util.PassengerTestUtil;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;
import org.springframework.data.domain.Page;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@Tag("unit")
class PassengerMapperTest {
    private final PassengerMapper mapper = Mappers.getMapper(PassengerMapper.class);

    @Test
    void entityToDto_ShouldConvertEntityToDto_WhenEntityIsNotNull() {
        Passenger passenger = PassengerTestUtil.buildDefaultPassenger();
        PassengerDto passengerDto = PassengerTestUtil.buildPassengerDto();

        PassengerDto actual = mapper.entityToDto(passenger);

        assertThat(actual)
                .isNotNull()
                .isEqualTo(passengerDto);
    }

    @Test
    void entityToDto_ShouldReturnNull_WhenEntityIsNull() {
        assertThat(mapper.entityToDto(null)).isNull();
    }

    @Test
    void updateEntityFromDto_ShouldUpdateEntity_WhenDtoIsNotNull() {
        Passenger actual = PassengerTestUtil.buildDefaultPassenger();
        PassengerUpdatingDto passengerUpdatingDto = PassengerTestUtil.buildPassengerUpdatingDto();

        mapper.updateEntityFromDto(passengerUpdatingDto, actual);

        assertThat(actual).isNotNull();
        assertThat(actual.getId()).isEqualTo(PassengerTestUtil.ID);
        assertThat(actual.getPhoneNumber()).isEqualTo(PassengerTestUtil.UPDATED_PHONE_NUMBER);
        assertThat(actual.getEmail()).isEqualTo(PassengerTestUtil.UPDATED_EMAIL);
        assertThat(actual.getFirstName()).isEqualTo(PassengerTestUtil.UPDATED_FIRST_NAME);
        assertThat(actual.getLastName()).isEqualTo(PassengerTestUtil.UPDATED_LAST_NAME);
    }

    @Test
    void dtoToEntity_ShouldConvertDtoToEntity_WhenDtoIsNotNull() {
        Passenger passenger = PassengerTestUtil.buildDefaultPassenger();
        PassengerAddingDto passengerAddingDto = PassengerTestUtil.buildPassengerAddingDto();

        Passenger actual = mapper.dtoToEntity(passengerAddingDto);

        assertThat(actual)
                .isNotNull()
                .isEqualTo(passenger);
    }

    @Test
    void dtoToEntity_ShouldReturnNull_WhenDtoIsNull() {
        assertThat(mapper.dtoToEntity(null)).isNull();
    }

    @Test
    void entityListToDtoList_ShouldConvertEntityListToDtoList_WhenListIsNotNull() {
        Passenger passenger = PassengerTestUtil.buildDefaultPassenger();
        List<Passenger> passengers = Arrays.asList(passenger, passenger);
        PassengerDto passengerDto = PassengerTestUtil.buildPassengerDto();
        List<PassengerDto> expectedList = Arrays.asList(passengerDto, passengerDto);

        List<PassengerDto> actual = mapper.entityListToDtoList(passengers);

        assertThat(actual)
                .isNotNull()
                .isEqualTo(expectedList);
    }

    @Test
    void entityListToDtoList_ShouldReturnEmptyList_WhenEntityListIsEmpty() {
        List<Passenger> passengers = Collections.emptyList();

        List<PassengerDto> actual = mapper.entityListToDtoList(passengers);

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
        Passenger passenger = PassengerTestUtil.buildDefaultPassenger();
        List<Passenger> entityList = Arrays.asList(passenger, passenger);
        Page<Passenger> entityPage = PaginationTestUtil.buildPageFromList(entityList);
        PassengerDto passengerDto = PassengerTestUtil.buildPassengerDto();
        List<PassengerDto> dtoList = Arrays.asList(passengerDto, passengerDto);
        Page<PassengerDto> expectedDtoPage = PaginationTestUtil.buildPageFromList(dtoList);

        Page<PassengerDto> actual = mapper.entityPageToDtoPage(entityPage);

        assertThat(actual)
                .isNotNull()
                .isEqualTo(expectedDtoPage);
    }
}
