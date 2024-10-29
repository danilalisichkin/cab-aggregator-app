package com.cabaggregator.driverservice.services.impl;

import com.cabaggregator.driverservice.core.constants.MessageKeys;
import com.cabaggregator.driverservice.core.dto.CarDetailsAddingDTO;
import com.cabaggregator.driverservice.core.dto.CarDetailsDTO;
import com.cabaggregator.driverservice.core.mappers.CarDetailsMapper;
import com.cabaggregator.driverservice.dao.repository.CarDetailsRepository;
import com.cabaggregator.driverservice.entities.CarDetails;
import com.cabaggregator.driverservice.exceptions.ResourceNotFoundException;
import com.cabaggregator.driverservice.services.ICarDetailsService;
import com.cabaggregator.driverservice.validator.CarDetailsValidator;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CarDetailsService implements ICarDetailsService {

    private final CarDetailsRepository carDetailsRepository;

    private final CarDetailsMapper carDetailsMapper;

    private final CarDetailsValidator carDetailsValidator;

    @Autowired
    public CarDetailsService(CarDetailsRepository carDetailsRepository, CarDetailsMapper carDetailsMapper, CarDetailsValidator carDetailsValidator) {
        this.carDetailsRepository = carDetailsRepository;
        this.carDetailsMapper = carDetailsMapper;
        this.carDetailsValidator = carDetailsValidator;
    }

    @Override
    public List<CarDetailsDTO> getAllCarDetails() {
        return carDetailsRepository.findAll().stream()
                .map(carDetailsMapper::entityToDto)
                .toList();
    }

    @Override
    public CarDetailsDTO getCarDetailsById(long id) {
        return carDetailsMapper.entityToDto(
                carDetailsRepository.findById(id).orElseThrow(
                        () -> new ResourceNotFoundException(
                                MessageKeys.ERROR_CAR_DETAILS_WITH_ID_NOT_FOUND,
                                id)));
    }

    @Override
    public CarDetailsDTO getCarDetailsByCarId(long carId) {
        return carDetailsMapper.entityToDto(
                carDetailsRepository.findByCarId(carId).orElseThrow(
                        () -> new ResourceNotFoundException(
                                MessageKeys.ERROR_CAR_DETAILS_WITH_CAR_ID_NOT_FOUND,
                                carId)));
    }

    @Override
    @Transactional
    public CarDetailsDTO saveCarDetails(CarDetailsAddingDTO carDetailsDTO) {
        carDetailsValidator.checkExistenceOfCarWithId(carDetailsDTO.getCarId());
        carDetailsValidator.checkUniquenessOfCarDetailsWithCarId(carDetailsDTO.getCarId());

        return carDetailsMapper.entityToDto(
                carDetailsRepository.save(
                        carDetailsMapper.addingDtoToEntity(carDetailsDTO)));
    }

    @Override
    @Transactional
    public CarDetailsDTO updateCarDetails(CarDetailsDTO carDetailsDTO) {
        carDetailsValidator.checkExistenceOfCarDetailsWithCarId(carDetailsDTO.getCarId());

        CarDetails carDetailsToUpdate = carDetailsMapper.dtoToEntity(carDetailsDTO);

        return carDetailsMapper.entityToDto(carDetailsRepository.save(carDetailsToUpdate));
    }
}
