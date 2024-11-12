package com.cabaggregator.driverservice.controllers.api;

import com.cabaggregator.driverservice.core.constant.MessageKeys;
import com.cabaggregator.driverservice.core.constant.ValidationRegex;
import com.cabaggregator.driverservice.core.dto.car.CarAddingDto;
import com.cabaggregator.driverservice.core.dto.car.CarDto;
import com.cabaggregator.driverservice.core.dto.car.CarUpdatingDto;
import com.cabaggregator.driverservice.core.dto.car.details.CarDetailsDto;
import com.cabaggregator.driverservice.core.dto.car.details.CarDetailsSettingDto;
import com.cabaggregator.driverservice.core.dto.page.PagedDto;
import com.cabaggregator.driverservice.service.ICarDetailsService;
import com.cabaggregator.driverservice.service.ICarService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
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
    private final ICarService carService;
    private final ICarDetailsService carDetailsService;

    @GetMapping
    public ResponseEntity<PagedDto<CarDto>> getPageOfCars(
            @Positive @RequestParam(required = false, name = "page", defaultValue = "1") int pageNumber,
            @Positive @RequestParam(required = false, name = "size", defaultValue = "10") int pageSize,
            @Pattern(regexp = ValidationRegex.SORT_ORDER,
                    message = MessageKeys.ValidationErrors.INVALID_SORT_ORDER)
            @RequestParam(required = false, name = "sort", defaultValue = "id") String sortField,
            @RequestParam(required = false, name = "order", defaultValue = "desc") String sortOrder) {

        log.info("Sending page of cars");

        PagedDto<CarDto> page = carService.getPageOfCars(pageNumber, pageSize, sortField, sortOrder);

        return ResponseEntity.status(HttpStatus.OK).body(page);
    }

    @GetMapping("/details")
    public ResponseEntity<PagedDto<CarDetailsDto>> getPageOfCarDetails(
            @Positive @RequestParam(required = false, name = "page", defaultValue = "1") int pageNumber,
            @Positive @RequestParam(required = false, name = "size", defaultValue = "10") int pageSize,
            @Pattern(regexp = ValidationRegex.SORT_ORDER,
                    message = MessageKeys.ValidationErrors.INVALID_SORT_ORDER)
            @RequestParam(required = false, name = "sort", defaultValue = "id") String sortField,
            @RequestParam(required = false, name = "order", defaultValue = "desc") String sortOrder) {

        log.info("Sending page of car details");

        PagedDto<CarDetailsDto> page =
                carDetailsService.getPageOfCarDetails(pageNumber, pageSize, sortField, sortOrder);

        return ResponseEntity.status(HttpStatus.OK).body(page);
    }

    @GetMapping("/{id}")
    public ResponseEntity<CarDto> getCar(@NotNull @PathVariable Long id) {
        log.info("Getting car with id={}", id);

        CarDto car = carService.getCarById(id);

        return ResponseEntity.status(HttpStatus.OK).body(car);
    }

    @GetMapping("/{id}/details")
    public ResponseEntity<CarDetailsDto> getCarDetails(@NotNull @PathVariable Long id) {
        log.info("Getting car details of car with id={}", id);

        CarDetailsDto carDetails = carDetailsService.getCarDetailsByCarId(id);

        return ResponseEntity.status(HttpStatus.OK).body(carDetails);
    }

    @PostMapping
    public ResponseEntity<CarDto> saveCar(@Valid @RequestBody CarAddingDto carAddingDto) {
        log.info("Saving car with licence plate={}", carAddingDto.licensePlate());

        CarDto car = carService.saveCar(carAddingDto);

        return ResponseEntity.status(HttpStatus.CREATED).body(car);
    }

    @PostMapping("/{id}/details")
    public ResponseEntity<CarDetailsDto> saveCarDetails(
            @NotNull @PathVariable Long id,
            @Valid @RequestBody CarDetailsSettingDto carDetailsDto) {
        log.info("Saving details for car with id={}", id);

        CarDetailsDto carDetails = carDetailsService.saveCarDetails(id, carDetailsDto);

        return ResponseEntity.status(HttpStatus.OK).body(carDetails);
    }

    @PutMapping("/{id}")
    public ResponseEntity<CarDto> updateCar(
            @NotNull @PathVariable Long id,
            @Valid @RequestBody CarUpdatingDto carDto) {
        log.info("Updating car with id={}", carDto.licensePlate());

        CarDto car = carService.updateCar(id, carDto);

        return ResponseEntity.status(HttpStatus.OK).body(car);
    }

    @PutMapping("/{id}/details")
    public ResponseEntity<CarDetailsDto> updateCarDetails(
            @NotNull @PathVariable Long id,
            @Valid @RequestBody CarDetailsSettingDto carDetailsDto) {
        log.info("Updating details for car with id={}", id);

        CarDetailsDto carDetails = carDetailsService.updateCarDetails(id, carDetailsDto);

        return ResponseEntity.status(HttpStatus.OK).body(carDetails);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCar(@NotNull @PathVariable Long id) {
        log.info("Deleting car with id={}", id);

        carService.deleteCarById(id);

        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}
