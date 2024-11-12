package com.cabaggregator.driverservice.core.mapper;

import com.cabaggregator.driverservice.CarTestUtil;
import com.cabaggregator.driverservice.core.dto.car.CarAddingDto;
import com.cabaggregator.driverservice.core.dto.car.CarDto;
import com.cabaggregator.driverservice.core.dto.car.CarUpdatingDto;
import com.cabaggregator.driverservice.entity.Car;
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
public class CarMapperTest {
    private final CarMapper mapper = Mappers.getMapper(CarMapper.class);

    private Car car;
    private CarDto carDto;
    private CarAddingDto carAddingDto;
    private CarUpdatingDto carUpdatingDto;

    @BeforeEach
    void setUp() {
        car = CarTestUtil.buildCar();
        carDto = CarTestUtil.buildCarDto();
        carAddingDto = CarTestUtil.buildCarAddingDto();
        carUpdatingDto = CarTestUtil.buildCarUpdatingDto();
    }

    @Test
    void entityToDto_ShouldConvertEntityToDto_WhenEntityIsNotNull() {
        CarDto convertedDto = mapper.entityToDto(car);

        assertThat(convertedDto).isNotNull();
        assertThat(convertedDto).isEqualTo(carDto);
    }

    @Test
    void entityToDto_ShouldReturnNull_WhenEntityIsNull() {
        assertThat(mapper.entityToDto(null)).isNull();
    }

    @Test
    void updateEntityFromDto_ShouldUpdateEntity_WhenDtoIsNotNull() {
        mapper.updateEntityFromDto(carUpdatingDto, car);

        assertThat(car).isNotNull();
        assertThat(car.getId()).isEqualTo(CarTestUtil.CAR_ID);
        assertThat(car.getLicensePlate()).isEqualTo(CarTestUtil.UPDATED_LICENSE_PLATE);
        assertThat(car.getMake()).isEqualTo(CarTestUtil.UPDATED_MAKE);
        assertThat(car.getModel()).isEqualTo(CarTestUtil.UPDATED_MODEL);
        assertThat(car.getColor()).isEqualTo(CarTestUtil.UPDATED_COLOR);
    }

    @Test
    void updateEntityFromDto_ShouldThrowNullPointerException_WhenDtoIsNull() {
        assertThatThrownBy(() -> mapper.updateEntityFromDto(carUpdatingDto, null))
                .isInstanceOf(NullPointerException.class);
    }

    @Test
    void dtoToEntity_ShouldConvertDtoToEntity_WhenDtoIsNotNull() {
        Car convertedEntity = mapper.dtoToEntity(carAddingDto);

        assertThat(convertedEntity).isNotNull();
        assertThat(convertedEntity.getId()).isNull();
        assertThat(convertedEntity.getLicensePlate()).isEqualTo(car.getLicensePlate());
        assertThat(convertedEntity.getMake()).isEqualTo(car.getMake());
        assertThat(convertedEntity.getModel()).isEqualTo(car.getModel());
        assertThat(convertedEntity.getColor()).isEqualTo(car.getColor());
    }

    @Test
    void dtoToEntity_ShouldReturnNull_WhenDtoIsNull() {
        assertThat(mapper.dtoToEntity(null)).isNull();
    }

    @Test
    void entityListToDtoList_ShouldConvertEntityListToDtoList_WhenEntityListIsNotEmpty() {
        List<Car> entityList = Arrays.asList(car, car);
        List<CarDto> expectedDtoList = Arrays.asList(carDto, carDto);

        List<CarDto> result = mapper.entityListToDtoList(entityList);

        assertThat(result).isNotNull();
        assertThat(result).isEqualTo(expectedDtoList);
    }

    @Test
    void entityListToDtoList_ShouldReturnEmptyList_WhenEntityListIsEmpty() {
        List<Car> entityList = Collections.emptyList();

        List<CarDto> result = mapper.entityListToDtoList(entityList);

        assertThat(result).isNotNull();
        assertThat(result).isEmpty();
    }

    @Test
    void entityListToDtoList_ShouldReturnNull_WhenEntityListIsNull() {
        assertThat(mapper.entityListToDtoList(null)).isNull();
    }

    @Test
    void entityPageToDtoPage_ShouldConvertEntityPageToDtoPage_WhenPageIsNotNull() {
        List<Car> entityList = Arrays.asList(car, car);
        Page<Car> entityPage = new PageImpl<>(entityList);

        final int expectedPage = 0;
        final int expectedTotalPages = 1;
        final long expectedTotalItems = 2;
        final int expectedItemsOnPage = 2;
        final int expectedPassengersListSize = 2;

        Page<CarDto> result = mapper.entityPageToDtoPage(entityPage);

        assertThat(result).isNotNull();
        assertThat(result.getNumber()).isEqualTo(expectedPage);
        assertThat(result.getTotalPages()).isEqualTo(expectedTotalPages);
        assertThat(result.getTotalElements()).isEqualTo(expectedTotalItems);
        assertThat(result.getNumberOfElements()).isEqualTo(expectedItemsOnPage);
        assertThat(result.getContent()).hasSize(expectedPassengersListSize);
    }

    @Test
    void entityPageToDtoPage_ShouldReturnNull_WhenPageIsNull() {
        Page<CarDto> result = mapper.entityPageToDtoPage(null);

        assertThat(result).isNull();
    }
}
