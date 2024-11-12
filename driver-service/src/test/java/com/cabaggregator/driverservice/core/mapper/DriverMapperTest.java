package com.cabaggregator.driverservice.core.mapper;

import com.cabaggregator.driverservice.DriverTestUtil;
import com.cabaggregator.driverservice.core.dto.driver.DriverAddingDto;
import com.cabaggregator.driverservice.core.dto.driver.DriverDto;
import com.cabaggregator.driverservice.core.dto.driver.DriverUpdatingDto;
import com.cabaggregator.driverservice.entity.Driver;
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
class DriverMapperTest {
    private final DriverMapper mapper = Mappers.getMapper(DriverMapper.class);

    private Driver driver;
    private DriverDto driverDto;
    private DriverAddingDto driverAddingDto;
    private DriverUpdatingDto driverUpdatingDto;

    @BeforeEach
    void setUp() {
        driver = DriverTestUtil.buildDriver();
        driverDto = DriverTestUtil.buildDriverDto();
        driverAddingDto = DriverTestUtil.buildDriverAddingDto();
        driverUpdatingDto = DriverTestUtil.buildDriverUpdatingDto();
    }

    @Test
    void entityToDto_ShouldConvertEntityToDto_WhenEntityIsNotNull() {
        DriverDto convertedDto = mapper.entityToDto(driver);

        assertThat(convertedDto).isNotNull();
        assertThat(convertedDto.id()).isEqualTo(driverDto.id());
        assertThat(convertedDto.phoneNumber()).isEqualTo(driverDto.phoneNumber());
        assertThat(convertedDto.email()).isEqualTo(driverDto.email());
        assertThat(convertedDto.firstName()).isEqualTo(driverDto.firstName());
        assertThat(convertedDto.lastName()).isEqualTo(driverDto.lastName());
        assertThat(convertedDto.rating()).isEqualTo(driverDto.rating());
        assertThat(convertedDto.carId()).isNull();
    }

    @Test
    void entityToDto_ShouldReturnNull_WhenEntityIsNull() {
        assertThat(mapper.entityToDto(null)).isNull();
    }

    @Test
    void updateEntityFromDto_ShouldUpdateEntity_WhenDtoIsNotNull() {
        mapper.updateEntityFromDto(driverUpdatingDto, driver);

        assertThat(driver).isNotNull();
        assertThat(driver.getId()).isEqualTo(DriverTestUtil.DRIVER_ID);
        assertThat(driver.getPhoneNumber()).isEqualTo(DriverTestUtil.UPDATED_PHONE_NUMBER);
        assertThat(driver.getEmail()).isEqualTo(DriverTestUtil.UPDATED_EMAIL);
        assertThat(driver.getFirstName()).isEqualTo(DriverTestUtil.UPDATED_FIRST_NAME);
        assertThat(driver.getLastName()).isEqualTo(DriverTestUtil.UPDATED_LAST_NAME);
        assertThat(driver.getRating()).isEqualTo(DriverTestUtil.UPDATED_RATING);
    }

    @Test
    void updateEntityFromDto_ShouldThrowNullPointerException_WhenDtoIsNull() {
        assertThatThrownBy(() -> mapper.updateEntityFromDto(driverUpdatingDto, null))
                .isInstanceOf(NullPointerException.class);
    }

    @Test
    void dtoToEntity_ShouldConvertDtoToEntity_WhenDtoIsNotNull() {
        Driver convertedEntity = mapper.dtoToEntity(driverAddingDto);

        assertThat(convertedEntity).isNotNull();
        assertThat(convertedEntity.getId()).isNull();
        assertThat(convertedEntity.getPhoneNumber()).isEqualTo(driver.getPhoneNumber());
        assertThat(convertedEntity.getEmail()).isEqualTo(driver.getEmail());
        assertThat(convertedEntity.getFirstName()).isEqualTo(driver.getFirstName());
        assertThat(convertedEntity.getLastName()).isEqualTo(driver.getLastName());
        assertThat(convertedEntity.getRating()).isNull();
        assertThat(convertedEntity.getCar()).isNull();
    }

    @Test
    void dtoToEntity_ShouldReturnNull_WhenDtoIsNull() {
        assertThat(mapper.dtoToEntity(null)).isNull();
    }

    @Test
    void entityListToDtoList_ShouldConvertEntityListToDtoList_WhenEntityListIsNotEmpty() {
        List<Driver> entityList = Arrays.asList(driver, driver);
        List<DriverDto> expectedDtoList = Arrays.asList(driverDto, driverDto);

        List<DriverDto> result = mapper.entityListToDtoList(entityList);
        for (int i = 0; i < result.size(); i++) {
            DriverDto convertedDto = result.get(i);
            DriverDto expectedDto = expectedDtoList.get(i);

            assertThat(convertedDto).isNotNull();
            assertThat(convertedDto.id()).isEqualTo(expectedDto.id());
            assertThat(convertedDto.phoneNumber()).isEqualTo(expectedDto.phoneNumber());
            assertThat(convertedDto.email()).isEqualTo(expectedDto.email());
            assertThat(convertedDto.firstName()).isEqualTo(expectedDto.firstName());
            assertThat(convertedDto.lastName()).isEqualTo(expectedDto.lastName());
            assertThat(convertedDto.rating()).isEqualTo(expectedDto.rating());
            assertThat(convertedDto.carId()).isNull();
        }
    }

    @Test
    void entityListToDtoList_ShouldReturnEmptyList_WhenEntityListIsEmpty() {
        List<Driver> entityList = Collections.emptyList();

        List<DriverDto> result = mapper.entityListToDtoList(entityList);

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
        List<Driver> entityList = Arrays.asList(driver, driver);
        Page<Driver> entityPage = new PageImpl<>(entityList);

        final int expectedPage = 0;
        final int expectedTotalPages = 1;
        final long expectedTotalItems = 2;
        final int expectedItemsOnPage = 2;
        final int expectedPassengersListSize = 2;

        Page<DriverDto> result = mapper.entityPageToDtoPage(entityPage);

        assertThat(result).isNotNull();
        assertThat(result.getNumber()).isEqualTo(expectedPage);
        assertThat(result.getTotalPages()).isEqualTo(expectedTotalPages);
        assertThat(result.getTotalElements()).isEqualTo(expectedTotalItems);
        assertThat(result.getNumberOfElements()).isEqualTo(expectedItemsOnPage);
        assertThat(result.getContent()).hasSize(expectedPassengersListSize);
    }

    @Test
    void entityPageToDtoPage_ShouldReturnNull_WhenPageIsNull() {
        Page<DriverDto> result = mapper.entityPageToDtoPage(null);

        assertThat(result).isNull();
    }
}
