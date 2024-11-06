package com.cabaggregator.driverservice.core.mapper;

import com.cabaggregator.driverservice.CarDetailsTestUtil;
import com.cabaggregator.driverservice.core.dto.car.details.CarDetailsDto;
import com.cabaggregator.driverservice.core.dto.car.details.CarDetailsSettingDto;
import com.cabaggregator.driverservice.entity.CarDetails;
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
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@Tag("unit")
@ExtendWith(MockitoExtension.class)
public class CarDetailsMapperTest {
    private final CarDetailsMapper mapper = Mappers.getMapper(CarDetailsMapper.class);

    private CarDetails carDetails;
    private CarDetailsDto carDetailsDto;
    private CarDetailsSettingDto carDetailsSettingDto;

    @BeforeEach
    void setUp() {
        carDetails = CarDetailsTestUtil.buildCarDetails();
        carDetailsDto = CarDetailsTestUtil.buildCarDetailsDto();
        carDetailsSettingDto = CarDetailsTestUtil.buildCarDetailsSettingDto();
    }

    @Test
    void entityToDto_ShouldConvertEntityToDto_WhenEntityIsNotNull() {
        CarDetailsDto convertedDto = mapper.entityToDto(carDetails);

        assertThat(convertedDto).isNotNull();
        assertThat(convertedDto).isEqualTo(carDetailsDto);
    }

    @Test
    void entityToDto_ShouldReturnNull_WhenEntityIsNull() {
        assertThat(mapper.entityToDto(null)).isNull();
    }

    @Test
    void updateEntityFromDto_ShouldUpdateEntity_WhenDtoIsNotNull() {
        mapper.updateEntityFromDto(carDetailsSettingDto, carDetails);

        assertThat(carDetails).isNotNull();
        assertThat(carDetails.getReleaseDate()).isEqualTo(CarDetailsTestUtil.UPDATED_RELEASE_DATE);
        assertThat(carDetails.getSeatCapacity()).isEqualTo(CarDetailsTestUtil.UPDATED_SEAT_CAPACITY);
    }

    @Test
    void updateEntityFromDto_ShouldThrowNullPointerException_WhenDtoIsNull() {
        assertThatThrownBy(() -> mapper.updateEntityFromDto(carDetailsSettingDto, null))
                .isInstanceOf(NullPointerException.class);
    }

    @Test
    void dtoToEntity_ShouldConvertDtoToEntity_WhenDtoIsNotNull() {

        CarDetails convertedEntity = mapper.dtoToEntity(carDetailsSettingDto);

        assertThat(convertedEntity).isNotNull();
        assertThat(convertedEntity.getId()).isNull();
        assertThat(convertedEntity.getReleaseDate()).isEqualTo(CarDetailsTestUtil.UPDATED_RELEASE_DATE);
        assertThat(convertedEntity.getSeatCapacity()).isEqualTo(CarDetailsTestUtil.UPDATED_SEAT_CAPACITY);
        assertThat(convertedEntity.getCar()).isNull();
    }

    @Test
    void dtoToEntity_ShouldReturnNull_WhenDtoIsNull() {
        assertThat(mapper.dtoToEntity(null)).isNull();
    }

    @Test
    void entityListToDtoList_ShouldConvertEntityListToDtoList_WhenEntityListIsNotEmpty() {
        List<CarDetails> entityList = Arrays.asList(carDetails, carDetails);
        List<CarDetailsDto> expectedDtoList = Arrays.asList(carDetailsDto, carDetailsDto);

        List<CarDetailsDto> result = mapper.entityListToDtoList(entityList);

        assertThat(result).isNotNull();
        assertThat(result).isEqualTo(expectedDtoList);
    }

    @Test
    void entityListToDtoList_ShouldReturnEmptyList_WhenEntityListIsEmpty() {
        List<CarDetails> entityList = Collections.emptyList();

        List<CarDetailsDto> result = mapper.entityListToDtoList(entityList);

        assertThat(result).isNotNull();
        assertThat(result).isEmpty();
    }

    @Test
    void entityListToDtoList_ShouldReturnNull_WhenEntityListIsNull() {
        assertThat(mapper.entityListToDtoList(null)).isNull();
    }

    @Test
    void entityPageToDtoPage_ShouldConvertEntityPageToDtoPage_WhenPageIsNotNull() {
        List<CarDetails> entityList = Arrays.asList(carDetails, carDetails);
        Page<CarDetails> entityPage = new PageImpl<>(entityList);

        final int expectedPage = 0;
        final int expectedTotalPages = 1;
        final long expectedTotalItems = 2;
        final int expectedItemsOnPage = 2;
        final int expectedPassengersListSize = 2;

        Page<CarDetailsDto> result = mapper.entityPageToDtoPage(entityPage);

        assertThat(result).isNotNull();
        assertThat(result.getNumber()).isEqualTo(expectedPage);
        assertThat(result.getTotalPages()).isEqualTo(expectedTotalPages);
        assertThat(result.getTotalElements()).isEqualTo(expectedTotalItems);
        assertThat(result.getNumberOfElements()).isEqualTo(expectedItemsOnPage);
        assertThat(result.getContent()).hasSize(expectedPassengersListSize);
    }

    @Test
    void entityPageToDtoPage_ShouldReturnNull_WhenPageIsNull() {
        Page<CarDetailsDto> result = mapper.entityPageToDtoPage(null);

        assertThat(result).isNull();
    }
}
