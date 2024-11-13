package com.cabaggregator.driverservice.core.mapper;

import com.cabaggregator.driverservice.CarDetailsTestUtil;
import com.cabaggregator.driverservice.core.dto.car.details.CarDetailsDto;
import com.cabaggregator.driverservice.core.dto.car.details.CarDetailsSettingDto;
import com.cabaggregator.driverservice.entity.CarDetails;
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
        CarDetails carDetails = CarDetailsTestUtil.buildCarDetails();
        CarDetailsDto carDetailsDto = CarDetailsTestUtil.buildCarDetailsDto();

        CarDetailsDto convertedDto = mapper.entityToDto(carDetails);

        assertThat(convertedDto)
                .isNotNull()
                .isEqualTo(carDetailsDto);
    }

    @Test
    void entityToDto_ShouldReturnNull_WhenEntityIsNull() {
        assertThat(mapper.entityToDto(null)).isNull();
    }

    @Test
    void updateEntityFromDto_ShouldUpdateEntity_WhenDtoIsNotNull() {
        CarDetails carDetails = CarDetailsTestUtil.buildCarDetails();
        CarDetailsSettingDto carDetailsSettingDto = CarDetailsTestUtil.buildCarDetailsSettingDto();

        mapper.updateEntityFromDto(carDetailsSettingDto, carDetails);

        assertThat(carDetails).isNotNull();
        assertThat(carDetails.getReleaseDate()).isEqualTo(CarDetailsTestUtil.UPDATED_RELEASE_DATE);
        assertThat(carDetails.getSeatCapacity()).isEqualTo(CarDetailsTestUtil.UPDATED_SEAT_CAPACITY);
    }

    @Test
    void updateEntityFromDto_ShouldThrowNullPointerException_WhenDtoIsNull() {
        CarDetailsSettingDto carDetailsSettingDto = CarDetailsTestUtil.buildCarDetailsSettingDto();

        assertThatThrownBy(() -> mapper.updateEntityFromDto(carDetailsSettingDto, null))
                .isInstanceOf(NullPointerException.class);
    }
}
