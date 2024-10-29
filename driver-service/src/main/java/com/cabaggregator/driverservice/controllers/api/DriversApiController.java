package com.cabaggregator.driverservice.controllers.api;

import com.cabaggregator.driverservice.core.dto.DriverAddingDTO;
import com.cabaggregator.driverservice.core.dto.DriverDTO;
import com.cabaggregator.driverservice.services.IDriverService;
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
@RequestMapping("/api/v1/drivers")
public class DriversApiController {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    private final IDriverService driverService;

    @Autowired
    public DriversApiController(IDriverService driverService) {
        this.driverService = driverService;
    }

    @GetMapping("/all")
    public ResponseEntity<List<DriverDTO>> getAllDrivers() {
        logger.info("Sending all drivers");

        List<DriverDTO> drivers = driverService.getAllDrivers();

        return ResponseEntity.status(HttpStatus.OK).body(drivers);
    }

    //TODO add get by id, phone, email, name
    @GetMapping("/{id}")
    public ResponseEntity<DriverDTO> getDriverById(@NotNull @PathVariable long id) {
        logger.info("Sending driver with id={}", id);

        DriverDTO driver = driverService.getDriverById(id);

        return ResponseEntity.status(HttpStatus.OK).body(driver);
    }

    @PostMapping
    public ResponseEntity<DriverDTO> saveDriver(@Valid @RequestBody DriverAddingDTO driverAddingDTO) {
        logger.info("Saving driver with phone={}", driverAddingDTO.getPhoneNumber());

        DriverDTO driver = driverService.saveDriver(driverAddingDTO);

        return ResponseEntity.status(HttpStatus.CREATED).body(driver);
    }

    @PutMapping
    public ResponseEntity<DriverDTO> updateDriver(@Valid @RequestBody DriverDTO driverDTO) {
        logger.info("Updating driver with id={}", driverDTO.getId());

        DriverDTO driver = driverService.updateDriver(driverDTO);

        return ResponseEntity.status(HttpStatus.OK).body(driver);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteDriverById(@NotNull @PathVariable long id) {
        logger.info("Deleting driver with id={}", id);

        driverService.deleteDriverById(id);

        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    @DeleteMapping("/all")
    public ResponseEntity<Void> deleteAllPassengers() {
        logger.info("Deleting all drivers");

        driverService.deleteAllDrivers();

        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}
