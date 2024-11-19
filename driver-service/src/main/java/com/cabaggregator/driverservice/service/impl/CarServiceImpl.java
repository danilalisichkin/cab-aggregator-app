package com.cabaggregator.driverservice.service.impl;

import com.cabaggregator.driverservice.core.constant.ApplicationMessages;
import com.cabaggregator.driverservice.core.dto.car.CarAddingDto;
import com.cabaggregator.driverservice.core.dto.car.CarDto;
import com.cabaggregator.driverservice.core.dto.car.CarFullDto;
import com.cabaggregator.driverservice.core.dto.car.CarUpdatingDto;
import com.cabaggregator.driverservice.core.dto.car.details.CarDetailsDto;
import com.cabaggregator.driverservice.core.dto.car.details.CarDetailsSettingDto;
import com.cabaggregator.driverservice.core.dto.page.PagedDto;
import com.cabaggregator.driverservice.core.enums.sort.CarSort;
import com.cabaggregator.driverservice.core.mapper.CarDetailsMapper;
import com.cabaggregator.driverservice.core.mapper.CarMapper;
import com.cabaggregator.driverservice.core.mapper.PageMapper;
import com.cabaggregator.driverservice.entity.Car;
import com.cabaggregator.driverservice.entity.CarDetails;
import com.cabaggregator.driverservice.exception.ResourceNotFoundException;
import com.cabaggregator.driverservice.repository.CarDetailsRepository;
import com.cabaggregator.driverservice.repository.CarRepository;
import com.cabaggregator.driverservice.service.CarDetailsService;
import com.cabaggregator.driverservice.service.CarService;
import com.cabaggregator.driverservice.util.PageRequestBuilder;
import com.cabaggregator.driverservice.validator.CarDetailsValidator;
import com.cabaggregator.driverservice.validator.CarValidator;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CarServiceImpl implements CarService {
    private final CarRepository carRepository;
    private final CarDetailsRepository carDetailsRepository;

    private final CarDetailsService carDetailsService;

    private final CarMapper carMapper;
    private final CarDetailsMapper carDetailsMapper;
    private final PageMapper pageMapper;

    private final CarValidator carValidator;
    private final CarDetailsValidator carDetailsValidator;

    @Override
    public PagedDto<CarDto> getPageOfCars(Integer offset, Integer limit, CarSort sort) {
        PageRequest request = PageRequestBuilder.buildPageRequest(offset, limit, sort.getSortValue());

        return pageMapper.pageToPagedDto(
                carMapper.entityPageToDtoPage(
                        carRepository.findAll(request)));
    }

    @Override
    public CarDto getCarById(Long id) {
        return carMapper.entityToDto(
                getCarEntityById(id));
    }

    @Override
    public CarFullDto getFullCarById(Long id) {
        return carMapper.entityToFullDto(
                getCarEntityById(id));
    }

    @Override
    @Transactional
    public CarDto saveCar(CarAddingDto carDto) {
        carDetailsValidator.validateReleaseDate(carDto.details().releaseDate());
        carValidator.validateLicencePlateUniqueness(carDto.licensePlate());

        Car car = carMapper.dtoToEntity(carDto);
        CarDetails details = new CarDetails();
        carDetailsMapper.updateEntityFromDto(carDto.details(), details);

        details.setCar(car);
        car.setCarDetails(details);

        return carMapper.entityToDto(
                carRepository.save(car));
    }

    @Override
    @Transactional
    public CarDto updateCar(Long id, CarUpdatingDto carDto) {
        Car carToUpdate = getCarEntityById(id);
        if (!carToUpdate.getLicensePlate().equals(carDto.licensePlate())) {
            carValidator.validateLicencePlateUniqueness(carDto.licensePlate());
        }

        carMapper.updateEntityFromDto(carDto, carToUpdate);

        return carMapper.entityToDto(
                carRepository.save(carToUpdate));
    }

    @Override
    @Transactional
    public CarFullDto updateCarDetails(Long carId, CarDetailsSettingDto carDetailsDto) {
        carDetailsValidator.validateReleaseDate(carDetailsDto.releaseDate());

        CarDto carDto = carMapper.entityToDto(getCarEntityById(carId));
        CarDetailsDto detailsDto = carDetailsService.saveCarDetails(carId, carDetailsDto);

        return new CarFullDto(carDto, detailsDto);
    }

    @Override
    @Transactional
    public void deleteCarById(Long id) {
        carValidator.validateExistenceOfCarWithId(id);

        carRepository.deleteById(id);
    }

    private Car getCarEntityById(Long id) {
        return carRepository
                .findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(
                        ApplicationMessages.CAR_WITH_ID_NOT_FOUND,
                        id));
    }
}
