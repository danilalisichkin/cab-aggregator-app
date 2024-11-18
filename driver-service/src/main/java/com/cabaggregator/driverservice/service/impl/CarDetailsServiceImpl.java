package com.cabaggregator.driverservice.service.impl;

import com.cabaggregator.driverservice.core.constant.ApplicationMessages;
import com.cabaggregator.driverservice.core.dto.car.details.CarDetailsSettingDto;
import com.cabaggregator.driverservice.core.mapper.CarDetailsMapper;
import com.cabaggregator.driverservice.entity.CarDetails;
import com.cabaggregator.driverservice.exception.ResourceNotFoundException;
import com.cabaggregator.driverservice.repository.CarDetailsRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CarDetailsServiceImpl implements com.cabaggregator.driverservice.service.CarDetailsService {
    private final CarDetailsRepository carDetailsRepository;
    private final CarDetailsMapper carDetailsMapper;

    @Override
    @Transactional
    public CarDetails saveCarDetails(Long id, CarDetailsSettingDto carDetailsDto) {
        CarDetails carDetails = getCarDetailsEntityById(id);
        carDetailsMapper.updateEntityFromDto(carDetailsDto, carDetails);

        return carDetailsRepository.save(carDetails);
    }

    private CarDetails getCarDetailsEntityById(Long carId) {
        return carDetailsRepository
                .findById(carId)
                .orElseThrow(() -> new ResourceNotFoundException(
                        ApplicationMessages.CAR_DETAILS_WITH_ID_NOT_FOUND,
                        carId));
    }
}
