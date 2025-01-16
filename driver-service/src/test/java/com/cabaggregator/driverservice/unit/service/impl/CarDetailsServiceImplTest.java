package com.cabaggregator.driverservice.unit.service.impl;

import com.cabaggregator.driverservice.core.dto.car.details.CarDetailsDto;
import com.cabaggregator.driverservice.core.dto.car.details.CarDetailsSettingDto;
import com.cabaggregator.driverservice.core.mapper.CarDetailsMapper;
import com.cabaggregator.driverservice.entity.CarDetails;
import com.cabaggregator.driverservice.exception.ResourceNotFoundException;
import com.cabaggregator.driverservice.repository.CarDetailsRepository;
import com.cabaggregator.driverservice.service.impl.CarDetailsServiceImpl;
import com.cabaggregator.driverservice.util.CarDetailsTestUtil;
import com.cabaggregator.driverservice.util.CarTestUtil;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoInteractions;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;

@Tag("unit")
@ExtendWith(MockitoExtension.class)
class CarDetailsServiceImplTest {

    @InjectMocks
    private CarDetailsServiceImpl carDetailsService;

    @Mock
    private CarDetailsRepository carDetailsRepository;

    @Mock
    private CarDetailsMapper carDetailsMapper;

    @Test
    void updateCarDetails_ShouldThrowResourceNotFound_WhenCarDetailsNotFound() {
        Long id = CarTestUtil.NOT_EXISTING_ID;
        CarDetailsSettingDto carDetailsSettingDto = CarDetailsTestUtil.buildCarDetailsSettingDto();

        when(carDetailsRepository.findById(id))
                .thenReturn(Optional.empty());

        assertThatThrownBy(
                () -> carDetailsService.updateCarDetails(id, carDetailsSettingDto))
                .isInstanceOf(ResourceNotFoundException.class);

        verify(carDetailsRepository).findById(id);
        verifyNoMoreInteractions(carDetailsRepository);
        verifyNoInteractions(carDetailsMapper);
    }

    @Test
    void updateCarDetails_ShouldUpdateCarDetails_WhenTheyFound() {
        Long id = CarTestUtil.ID;
        CarDetailsSettingDto carDetailsSettingDto = CarDetailsTestUtil.buildCarDetailsSettingDto();
        CarDetails carDetails = CarDetailsTestUtil.buildDefaultCarDetails();
        CarDetailsDto carDetailsDto = CarDetailsTestUtil.buildCarDetailsDto();

        when(carDetailsRepository.findById(id))
                .thenReturn(Optional.of(carDetails));
        doNothing().when(carDetailsMapper).updateEntityFromDto(carDetailsSettingDto, carDetails);
        when(carDetailsRepository.save(any(CarDetails.class)))
                .thenReturn(carDetails);
        when(carDetailsMapper.entityToDto(carDetails))
                .thenReturn(carDetailsDto);

        CarDetailsDto actual = carDetailsService.updateCarDetails(id, carDetailsSettingDto);

        assertThat(actual)
                .isNotNull()
                .isEqualTo(carDetailsDto);

        verify(carDetailsRepository).findById(id);
        verify(carDetailsMapper).updateEntityFromDto(carDetailsSettingDto, carDetails);
        verify(carDetailsRepository).save(any(CarDetails.class));
        verify(carDetailsMapper).entityToDto(carDetails);
    }
}
