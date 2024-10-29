package com.cabaggregator.driverservice.controllers.api;

import com.cabaggregator.driverservice.core.dto.CarAddingDTO;
import com.cabaggregator.driverservice.core.dto.CarDTO;
import com.cabaggregator.driverservice.services.ICarService;
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
public class CarsApiController {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    private final ICarService carService;

    @Autowired
    public CarsApiController(ICarService carService) {
        this.carService = carService;
    }

    @GetMapping("/all")
    public ResponseEntity<List<CarDTO>> getAllCars() {
        logger.info("Sending all cars");

        List<CarDTO> cars = carService.getAllCars();

        return ResponseEntity.status(HttpStatus.OK).body(cars);
    }

    //TODO add get car by id or license plate
    @GetMapping("/{id}")
    public ResponseEntity<CarDTO> getCarById(@NotNull @PathVariable long id) {
        logger.info("Getting car with id={}", id);

        CarDTO car = carService.getCarById(id);

        return ResponseEntity.status(HttpStatus.OK).body(car);
    }

    @PostMapping
    public ResponseEntity<CarDTO> saveCar(@Valid @RequestBody CarAddingDTO carAddingDTO) {
        logger.info("Saving car with licence plate={}", carAddingDTO.getLicensePlate());

        CarDTO car = carService.saveCar(carAddingDTO);

        return ResponseEntity.status(HttpStatus.CREATED).body(car);
    }

    @PutMapping
    public ResponseEntity<CarDTO> updateCar(@Valid @RequestBody CarDTO carDTO) {
        logger.info("Updating car with id={}", carDTO.getId());

        CarDTO car = carService.updateCar(carDTO);

        return ResponseEntity.status(HttpStatus.OK).body(car);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCarById(@NotNull @PathVariable long id) {
        logger.info("Deleting car with id={}", id);

        carService.deleteCarById(id);

        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    @DeleteMapping("/all")
    public ResponseEntity<Void> deleteAllCars() {
        logger.info("Deleting all cars");

        carService.deleteAllCars();

        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}
