package com.cabaggregator.driverservice.controllers.api;

import com.cabaggregator.driverservice.controllers.doc.CarControllerDocumentation;
import com.cabaggregator.driverservice.core.constant.ValidationErrors;
import com.cabaggregator.driverservice.core.dto.car.CarAddingDto;
import com.cabaggregator.driverservice.core.dto.car.CarDto;
import com.cabaggregator.driverservice.core.dto.car.CarFullDto;
import com.cabaggregator.driverservice.core.dto.car.CarUpdatingDto;
import com.cabaggregator.driverservice.core.dto.car.details.CarDetailsSettingDto;
import com.cabaggregator.driverservice.core.dto.page.PageDto;
import com.cabaggregator.driverservice.core.enums.sort.CarSortField;
import com.cabaggregator.driverservice.service.CarService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@Validated
@RestController
@RequestMapping("/api/v1/cars")
@RequiredArgsConstructor
public class CarController implements CarControllerDocumentation {
    private final CarService carService;

    @Override
    @GetMapping
    public ResponseEntity<PageDto<CarDto>> getPageOfCars(
            @RequestParam(defaultValue = "0") @PositiveOrZero Integer offset,
            @RequestParam(defaultValue = "10") @Positive
            @Max(value = 20, message = ValidationErrors.INVALID_NUMBER_MAX_VALUE) Integer limit,
            @RequestParam(defaultValue = "id") CarSortField sortBy,
            @RequestParam(defaultValue = "ASC") Sort.Direction sortOrder) {

        log.info("Sending page of cars");

        PageDto<CarDto> page = carService.getPageOfCars(offset, limit, sortBy, sortOrder);

        return ResponseEntity.status(HttpStatus.OK).body(page);
    }

    @Override
    @GetMapping("/{id}")
    public ResponseEntity<CarDto> getCar(@PathVariable Long id) {
        log.info("Getting car with id={}", id);

        CarDto car = carService.getCarById(id);

        return ResponseEntity.status(HttpStatus.OK).body(car);
    }

    @Override
    @GetMapping("/{id}/full")
    public ResponseEntity<CarFullDto> getFullCar(@PathVariable Long id) {
        log.info("Getting car with id={} including its details", id);

        CarFullDto carDetails = carService.getFullCarById(id);

        return ResponseEntity.status(HttpStatus.OK).body(carDetails);
    }

    @Override
    @PostMapping
    public ResponseEntity<CarDto> saveCar(@RequestBody @Valid CarAddingDto carAddingDto) {
        log.info("Saving car with licence plate={}", carAddingDto.licensePlate());

        CarDto car = carService.saveCar(carAddingDto);

        return ResponseEntity.status(HttpStatus.CREATED).body(car);
    }

    @Override
    @PutMapping("/{id}")
    public ResponseEntity<CarDto> updateCar(
            @PathVariable Long id,
            @RequestBody @Valid CarUpdatingDto carDto) {
        log.info("Updating car with id={}", carDto.licensePlate());

        CarDto car = carService.updateCar(id, carDto);

        return ResponseEntity.status(HttpStatus.OK).body(car);
    }

    @Override
    @PutMapping("/{id}/details")
    public ResponseEntity<CarFullDto> updateCarDetails(
            @PathVariable Long id,
            @RequestBody @Valid CarDetailsSettingDto carDetailsDto) {
        log.info("Updating details of car with id={}", id);

        CarFullDto carDetails = carService.updateCarDetails(id, carDetailsDto);

        return ResponseEntity.status(HttpStatus.OK).body(carDetails);
    }

    @Override
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCar(@PathVariable Long id) {
        log.info("Deleting car with id={}", id);

        carService.deleteCarById(id);

        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}
