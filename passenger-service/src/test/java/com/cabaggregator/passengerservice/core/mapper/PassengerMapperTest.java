package com.cabaggregator.passengerservice.core.mapper;

import com.cabaggregator.passengerservice.PassengerTestUtil;
import com.cabaggregator.passengerservice.core.dto.PassengerAddingDto;
import com.cabaggregator.passengerservice.core.dto.PassengerDto;
import com.cabaggregator.passengerservice.core.dto.PassengerUpdatingDto;
import com.cabaggregator.passengerservice.entity.Passenger;
import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.BeforeEach;
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

@Tag("unit")
@ExtendWith(MockitoExtension.class)
public class PassengerMapperTest {
    private final PassengerMapper mapper = Mappers.getMapper(PassengerMapper.class);

    private Passenger passenger;
    private PassengerDto passengerDto;
    private PassengerUpdatingDto passengerUpdatingDto;
    private PassengerAddingDto passengerAddingDto;

    @BeforeEach
    void setUp() {
        passenger = PassengerTestUtil.buildPassenger();
        passengerDto = PassengerTestUtil.buildPassengerDto();
        passengerUpdatingDto = PassengerTestUtil.buildPassengerUpdatingDto();
        passengerAddingDto = PassengerTestUtil.buildPassengerAddingDto();
    }

    @Test
    void entityToDto_ShouldConvertEntityToDto_WhenEntityIsNotNull() {
        PassengerDto convertedDto = mapper.entityToDto(passenger);

        assertThat(convertedDto).isNotNull();
        assertThat(convertedDto).isEqualTo(passengerDto);
    }

    @Test
    void entityToDto_ShouldReturnNull_WhenEntityIsNull() {
        assertThat(mapper.entityToDto(null)).isNull();
    }

    @Test
    void updatingDtoToEntity_ShouldConvertDtoToEntity_WhenDtoIsNotNull() {
        final long expectedId = 0L;

        Passenger convertedEntity = mapper.updatingDtoToEntity(passengerUpdatingDto);

        assertThat(convertedEntity).isNotNull();
        assertThat(convertedEntity.getId()).isEqualTo(expectedId);
        assertThat(convertedEntity.getPhoneNumber()).isEqualTo(passengerUpdatingDto.phoneNumber());
        assertThat(convertedEntity.getEmail()).isEqualTo(passengerUpdatingDto.email());
        assertThat(convertedEntity.getFirstName()).isEqualTo(passengerUpdatingDto.firstName());
        assertThat(convertedEntity.getLastName()).isEqualTo(passengerUpdatingDto.lastName());
        assertThat(convertedEntity.getRating()).isEqualTo(passengerUpdatingDto.rating());
    }

    @Test
    void updatingDtoToEntity_ShouldReturnNull_WhenDtoIsNull() {
        assertThat(mapper.updatingDtoToEntity(null)).isNull();
    }

    @Test
    void addingDtoToEntity_ShouldConvertDtoToEntity_WhenDtoIsNotNull() {
        final long expectedId = 0L;
        final double expectedRating = 0.0;

        Passenger convertedEntity = mapper.addingDtoToEntity(passengerAddingDto);

        assertThat(convertedEntity).isNotNull();
        assertThat(convertedEntity.getId()).isEqualTo(expectedId);
        assertThat(convertedEntity.getPhoneNumber()).isEqualTo(passenger.getPhoneNumber());
        assertThat(convertedEntity.getEmail()).isEqualTo(passenger.getEmail());
        assertThat(convertedEntity.getFirstName()).isEqualTo(passenger.getFirstName());
        assertThat(convertedEntity.getLastName()).isEqualTo(passenger.getLastName());
        assertThat(convertedEntity.getRating()).isEqualTo(expectedRating);
    }

    @Test
    void addingDtoToEntity_ShouldReturnNull_WhenDtoIsNull() {
        assertThat(mapper.addingDtoToEntity(null)).isNull();
    }

    @Test
    void entityListToDtoList_ShouldConvertEntityListToDtoList_WhenListIsNotNull() {
        List<Passenger> passengers = Arrays.asList(passenger, passenger);
        List<PassengerDto> expectedList = Arrays.asList(passengerDto, passengerDto);

        List<PassengerDto> result = mapper.entityListToDtoList(passengers);

        assertThat(result).isNotNull();
        assertThat(result).isEqualTo(expectedList);
    }

    @Test
    void entityListToDtoList_ShouldReturnEmptyList_WhenEntityListIsEmpty() {
        List<Passenger> passengers = Collections.emptyList();

        List<PassengerDto> result = mapper.entityListToDtoList(passengers);

        assertThat(result).isNotNull();
        assertThat(result).isEmpty();
    }

    @Test
    void entityListToDtoList_ShouldReturnNull_WhenListIsNull() {
        assertThat(mapper.entityListToDtoList(null)).isNull();
    }

    @Test
    void entityPageToDtoPage_ShouldConvertEntityPageToDtoPage_WhenPageIsNotNull() {
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
