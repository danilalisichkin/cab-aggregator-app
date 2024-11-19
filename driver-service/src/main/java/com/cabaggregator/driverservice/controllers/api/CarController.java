package com.cabaggregator.driverservice.controllers.api;

import com.cabaggregator.driverservice.core.constant.ValidationErrors;
import com.cabaggregator.driverservice.core.dto.car.CarAddingDto;
import com.cabaggregator.driverservice.core.dto.car.CarDto;
import com.cabaggregator.driverservice.core.dto.car.CarFullDto;
import com.cabaggregator.driverservice.core.dto.car.CarUpdatingDto;
import com.cabaggregator.driverservice.core.dto.car.details.CarDetailsSettingDto;
import com.cabaggregator.driverservice.core.dto.page.PagedDto;
import com.cabaggregator.driverservice.core.enums.sort.CarSort;
import com.cabaggregator.driverservice.service.CarService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
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
public class CarController {
    private final CarService carService;

    @GetMapping
    public ResponseEntity<PagedDto<CarDto>> getPageOfCars(
            @RequestParam(name = "offset")
            @Min(value = 0, message = ValidationErrors.INVALID_NUMBER_MIN_VALUE)
            Integer offset,
            @RequestParam(name = "limit") @Positive Integer limit,
            @RequestParam(name = "sort") CarSort sort) {

        log.info("Sending page of cars");

        PagedDto<CarDto> page = carService.getPageOfCars(offset, limit, sort);

        return ResponseEntity.status(HttpStatus.OK).body(page);
    }

    @GetMapping("/{id}")
    public ResponseEntity<CarDto> getCar(@PathVariable @NotNull Long id) {
        log.info("Getting car with id={}", id);

        CarDto car = carService.getCarById(id);

        return ResponseEntity.status(HttpStatus.OK).body(car);
    }

    @GetMapping("/{id}/full")
    public ResponseEntity<CarFullDto> getFullCar(@PathVariable @NotNull Long id) {
        log.info("Getting car with id={} including its details", id);

        CarFullDto carDetails = carService.getFullCarById(id);

        return ResponseEntity.status(HttpStatus.OK).body(carDetails);
    }

    @PostMapping
    public ResponseEntity<CarDto> saveCar(@RequestBody @Valid CarAddingDto carAddingDto) {
        log.info("Saving car with licence plate={}", carAddingDto.licensePlate());

        CarDto car = carService.saveCar(carAddingDto);

        return ResponseEntity.status(HttpStatus.CREATED).body(car);
    }

    @PutMapping("/{id}")
    public ResponseEntity<CarDto> updateCar(
            @PathVariable @NotNull Long id,
            @RequestBody @Valid CarUpdatingDto carDto) {
        log.info("Updating car with id={}", carDto.licensePlate());

        CarDto car = carService.updateCar(id, carDto);

        return ResponseEntity.status(HttpStatus.OK).body(car);
    }

    @PutMapping("/{id}/details")
    public ResponseEntity<CarFullDto> updateCarDetails(
            @PathVariable @NotNull Long id,
            @RequestBody @Valid CarDetailsSettingDto carDetailsDto) {
        log.info("Updating details of car with id={}", id);

        CarFullDto carDetails = carService.updateCarDetails(id, carDetailsDto);

        return ResponseEntity.status(HttpStatus.OK).body(carDetails);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCar(@NotNull @PathVariable Long id) {
        log.info("Deleting car with id={}", id);

        carService.deleteCarById(id);

        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}
