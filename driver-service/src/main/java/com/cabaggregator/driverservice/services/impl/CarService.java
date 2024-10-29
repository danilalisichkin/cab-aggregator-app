package com.cabaggregator.driverservice.services.impl;

import com.cabaggregator.driverservice.core.constants.MessageKeys;
import com.cabaggregator.driverservice.core.dto.CarAddingDTO;
import com.cabaggregator.driverservice.core.dto.CarDTO;
import com.cabaggregator.driverservice.core.mappers.CarMapper;
import com.cabaggregator.driverservice.dao.repository.CarRepository;
import com.cabaggregator.driverservice.entities.Car;
import com.cabaggregator.driverservice.exceptions.ResourceNotFoundException;
import com.cabaggregator.driverservice.services.ICarService;
import com.cabaggregator.driverservice.validator.CarValidator;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CarService implements ICarService {

    private final CarRepository carRepository;

    private final CarMapper carMapper;

    private final CarValidator carValidator;

    @Autowired
    public CarService(CarRepository carRepository, CarMapper carMapper, CarValidator carValidator) {
        this.carRepository = carRepository;
        this.carMapper = carMapper;
        this.carValidator = carValidator;
    }

    @Override
    public List<CarDTO> getAllCars() {
        return carRepository.findAll().stream()
                .map(carMapper::entityToDto)
                .toList();
    }

    @Override
    public CarDTO getCarById(long id) {
        return carMapper.entityToDto(
                carRepository.findById(id).orElseThrow(
                        () -> new ResourceNotFoundException(
                                MessageKeys.ERROR_CAR_WITH_ID_NOT_FOUND,
                                id)));
    }

    @Override
    public CarDTO getCarByLicensePlate(String licensePlate) {
        return carMapper.entityToDto(
                carRepository.findByLicensePlate(licensePlate).orElseThrow(
                        () -> new ResourceNotFoundException(
                                MessageKeys.ERROR_CAR_WITH_LICENCE_PLATE_NOT_FOUND,
                                licensePlate)));
    }

    @Override
    @Transactional
    public CarDTO saveCar(CarAddingDTO carDTO) {
        carValidator.checkLicencePlateUniqueness(carDTO.getLicensePlate());

        return carMapper.entityToDto(
                carRepository.save(
                        carMapper.addingDtoToEntity(carDTO)));
    }

    @Override
    @Transactional
    public CarDTO updateCar(CarDTO carDTO) {
        Car carToUpdate = carRepository.findById(carDTO.getId()).orElseThrow(
                () -> new ResourceNotFoundException(
                        MessageKeys.ERROR_CAR_WITH_ID_NOT_FOUND,
                        carDTO.getId()));

        carValidator.checkExistenceOfOtherCarWithSameLicencePlate(carToUpdate);

        carToUpdate = carMapper.dtoToEntity(carDTO);

        return carMapper.entityToDto(carRepository.save(carToUpdate));
    }

    @Override
    @Transactional
    public void deleteCarById(long id) {
        carValidator.checkExistenceOfCarWithId(id);
        carRepository.deleteById(id);
    }

    @Override
    @Transactional
    public void deleteAllCars() {
        if (carRepository.count() == 0) {
            throw new ResourceNotFoundException(MessageKeys.ERROR_CARS_NOT_FOUND);
        } else {
            carRepository.deleteAll();
        }
    }
}
