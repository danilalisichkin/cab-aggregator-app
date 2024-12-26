package com.cabaggregator.driverservice.unit.core.mapper;

import com.cabaggregator.driverservice.core.dto.car.details.CarDetailsDto;
import com.cabaggregator.driverservice.core.dto.car.details.CarDetailsSettingDto;
import com.cabaggregator.driverservice.core.mapper.CarDetailsMapper;
import com.cabaggregator.driverservice.entity.CarDetails;
import com.cabaggregator.driverservice.util.CarDetailsTestUtil;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mapstruct.factory.Mappers;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@Tag("unit")
@ExtendWith(MockitoExtension.class)
class CarDetailsMapperTest {
    private final CarDetailsMapper mapper = Mappers.getMapper(CarDetailsMapper.class);

    @Test
    void entityToDto_ShouldConvertEntityToDto_WhenEntityIsNotNull() {
        CarDetails carDetails = CarDetailsTestUtil.buildDefaultCarDetails();
        CarDetailsDto carDetailsDto = CarDetailsTestUtil.buildCarDetailsDto();

        CarDetailsDto actual = mapper.entityToDto(carDetails);

        assertThat(actual)
                .isNotNull()
                .isEqualTo(carDetailsDto);
    }

    @Test
    void entityToDto_ShouldReturnNull_WhenEntityIsNull() {
        assertThat(mapper.entityToDto(null)).isNull();
    }

    @Test
    void updateEntityFromDto_ShouldUpdateEntity_WhenDtoIsNotNull() {
        CarDetails actual = CarDetailsTestUtil.buildDefaultCarDetails();
        CarDetailsSettingDto carDetailsSettingDto = CarDetailsTestUtil.buildCarDetailsSettingDto();

        mapper.updateEntityFromDto(carDetailsSettingDto, actual);

        assertThat(actual).isNotNull();
        assertThat(actual.getReleaseDate()).isEqualTo(CarDetailsTestUtil.UPDATED_RELEASE_DATE);
        assertThat(actual.getSeatCapacity()).isEqualTo(CarDetailsTestUtil.UPDATED_SEAT_CAPACITY);
    }

    @Test
    void updateEntityFromDto_ShouldThrowNullPointerException_WhenDtoIsNull() {
        CarDetailsSettingDto carDetailsSettingDto = CarDetailsTestUtil.buildCarDetailsSettingDto();

        assertThatThrownBy(
                () -> mapper.updateEntityFromDto(carDetailsSettingDto, null))
                .isInstanceOf(NullPointerException.class);
    }
}
