package com.cabaggregator.driverservice.controllers.api;

import com.cabaggregator.driverservice.core.dto.CarDetailsAddingDTO;
import com.cabaggregator.driverservice.core.dto.CarDetailsDTO;
import com.cabaggregator.driverservice.services.ICarDetailsService;
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
public class CarDetailsController {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    private final ICarDetailsService carDetailsService;

    @Autowired
    public CarDetailsController(ICarDetailsService carDetailsService) {
        this.carDetailsService = carDetailsService;
    }

    @GetMapping("/all")
    public ResponseEntity<List<CarDetailsDTO>> getAllCarDetails() {
        logger.info("Sending all car details");

        List<CarDetailsDTO> carDetails = carDetailsService.getAllCarDetails();

        return ResponseEntity.status(HttpStatus.OK).body(carDetails);
    }

    //TODO add get car details by car_id
    @GetMapping("/{id}")
    public ResponseEntity<CarDetailsDTO> getCarDetailsById(@NotNull @PathVariable long id) {
        logger.info("Getting car details with id={}", id);

        CarDetailsDTO carDetails = carDetailsService.getCarDetailsById(id);

        return ResponseEntity.status(HttpStatus.OK).body(carDetails);
    }

    @PostMapping
    public ResponseEntity<CarDetailsDTO> saveCarDetails(@Valid @RequestBody CarDetailsAddingDTO carDetailsAddingDTO) {
        logger.info("Saving car details for car with id={}", carDetailsAddingDTO.getCarId());

        CarDetailsDTO carDetails = carDetailsService.saveCarDetails(carDetailsAddingDTO);

        return ResponseEntity.status(HttpStatus.CREATED).body(carDetails);
    }

    @PutMapping
    public ResponseEntity<CarDetailsDTO> updateCarDetails(@Valid @RequestBody CarDetailsDTO carDetailsDTO) {
        logger.info("Updating car details for car with id={}", carDetailsDTO.getCarId());

        CarDetailsDTO carDetails = carDetailsService.updateCarDetails(carDetailsDTO);

        return ResponseEntity.status(HttpStatus.OK).body(carDetails);
    }
}
