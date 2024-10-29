package com.cabaggregator.driverservice.controllers.api;

import com.cabaggregator.driverservice.core.dto.CarDetailsAddingDTO;
import com.cabaggregator.driverservice.core.dto.CarDetailsDTO;
import com.cabaggregator.driverservice.services.ICarDetailsService;
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
@RequestMapping("/api/v1/cars/details")
@Tag(name = "Car Details API Controller", description = "Provides CRUD-operations with car details")
public class CarDetailsController {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    private final ICarDetailsService carDetailsService;

    @Autowired
    public CarDetailsController(ICarDetailsService carDetailsService) {
        this.carDetailsService = carDetailsService;
    }

    @GetMapping("/all")
    @Operation(summary = "Get all", description = "Allows to get all existing car details")
    public ResponseEntity<List<CarDetailsDTO>> getAllCarDetails() {
        logger.info("Sending all car details");

        List<CarDetailsDTO> carDetails = carDetailsService.getAllCarDetails();

        return ResponseEntity.status(HttpStatus.OK).body(carDetails);
    }

    //TODO add get car details by car_id
    @GetMapping("/{id}")
    @Operation(summary = "Get by id", description = "Allows to get existing car details by id")
    public ResponseEntity<CarDetailsDTO> getCarDetailsById(@NotNull @PathVariable long id) {
        logger.info("Getting car details with id={}", id);

        CarDetailsDTO carDetails = carDetailsService.getCarDetailsById(id);

        return ResponseEntity.status(HttpStatus.OK).body(carDetails);
    }

    @PostMapping
    @Operation(summary = "Add/save", description = "Allows to add/save new car details")
    public ResponseEntity<CarDetailsDTO> saveCarDetails(@Valid @RequestBody CarDetailsAddingDTO carDetailsAddingDTO) {
        logger.info("Saving car details for car with id={}", carDetailsAddingDTO.getCarId());

        CarDetailsDTO carDetails = carDetailsService.saveCarDetails(carDetailsAddingDTO);

        return ResponseEntity.status(HttpStatus.CREATED).body(carDetails);
    }

    @PutMapping
    @Operation(summary = "Update", description = "Allows to update existing car details")
    public ResponseEntity<CarDetailsDTO> updateCarDetails(@Valid @RequestBody CarDetailsDTO carDetailsDTO) {
        logger.info("Updating car details for car with id={}", carDetailsDTO.getCarId());

        CarDetailsDTO carDetails = carDetailsService.updateCarDetails(carDetailsDTO);

        return ResponseEntity.status(HttpStatus.OK).body(carDetails);
    }
}
