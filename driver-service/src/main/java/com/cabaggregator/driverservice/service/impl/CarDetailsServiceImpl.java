package com.cabaggregator.driverservice.service.impl;

import com.cabaggregator.driverservice.core.constant.ApplicationMessages;
import com.cabaggregator.driverservice.core.dto.car.details.CarDetailsDto;
import com.cabaggregator.driverservice.core.dto.car.details.CarDetailsSettingDto;
import com.cabaggregator.driverservice.core.mapper.CarDetailsMapper;
import com.cabaggregator.driverservice.entity.CarDetails;
import com.cabaggregator.driverservice.exception.ResourceNotFoundException;
import com.cabaggregator.driverservice.repository.CarDetailsRepository;
import com.cabaggregator.driverservice.service.CarDetailsService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CarDetailsServiceImpl implements CarDetailsService {
    private final CarDetailsRepository carDetailsRepository;
    private final CarDetailsMapper carDetailsMapper;

    @Override
    @Transactional
    public CarDetailsDto saveCarDetails(Long id, CarDetailsSettingDto carDetailsDto) {
        CarDetails carDetails = getCarDetailsEntityById(id);
        carDetailsMapper.updateEntityFromDto(carDetailsDto, carDetails);

        return carDetailsMapper.entityToDto(
                carDetailsRepository.save(carDetails));
    }

    private CarDetails getCarDetailsEntityById(Long carId) {
        return carDetailsRepository
                .findById(carId)
                .orElseThrow(() -> new ResourceNotFoundException(
                        ApplicationMessages.CAR_DETAILS_WITH_ID_NOT_FOUND,
                        carId));
    }
}
