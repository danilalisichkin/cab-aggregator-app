package com.cabaggregator.driverservice.controllers.api;

import com.cabaggregator.driverservice.core.dto.CarAddingDTO;
import com.cabaggregator.driverservice.core.dto.CarDTO;
import com.cabaggregator.driverservice.services.ICarService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/cars")
@Tag(name = "Car API Controller", description = "Provides CRUD-operations with cars")
public class CarApiController {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    private final ICarService carService;

    @Autowired
    public CarApiController(ICarService carService) {
        this.carService = carService;
    }

    @GetMapping("/all")
    @Operation(summary = "Get all", description = "Allows to get all existing cars")
    public ResponseEntity<List<CarDTO>> getAllCars() {
        logger.info("Sending all cars");

        List<CarDTO> cars = carService.getAllCars();

        return ResponseEntity.status(HttpStatus.OK).body(cars);
    }

    //TODO add get car by id or license plate
    @GetMapping("/{id}")
    @Operation(summary = "Get by id", description = "Allows to get existing car by id")
    public ResponseEntity<CarDTO> getCarById(@NotNull @PathVariable long id) {
        logger.info("Getting car with id={}", id);

        CarDTO car = carService.getCarById(id);

        return ResponseEntity.status(HttpStatus.OK).body(car);
    }

    @PostMapping
    @Operation(summary = "Add/save", description = "Allows to add/save new car")
    public ResponseEntity<CarDTO> saveCar(@Valid @RequestBody CarAddingDTO carAddingDTO) {
        logger.info("Saving car with licence plate={}", carAddingDTO.getLicensePlate());

        CarDTO car = carService.saveCar(carAddingDTO);

        return ResponseEntity.status(HttpStatus.CREATED).body(car);
    }

    @PutMapping
    @Operation(summary = "Update", description = "Allows to update existing car")
    public ResponseEntity<CarDTO> updateCar(@Valid @RequestBody CarDTO carDTO) {
        logger.info("Updating car with id={}", carDTO.getId());

        CarDTO car = carService.updateCar(carDTO);

        return ResponseEntity.status(HttpStatus.OK).body(car);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Delete", description = "Allows to delete existing car by id")
    public ResponseEntity<Void> deleteCarById(@NotNull @PathVariable long id) {
        logger.info("Deleting car with id={}", id);

        carService.deleteCarById(id);

        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    @DeleteMapping("/all")
    @Operation(summary = "Delete all", description = "Allows to delete all existing cars")
    public ResponseEntity<Void> deleteAllCars() {
        logger.info("Deleting all cars");

        carService.deleteAllCars();

        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}
