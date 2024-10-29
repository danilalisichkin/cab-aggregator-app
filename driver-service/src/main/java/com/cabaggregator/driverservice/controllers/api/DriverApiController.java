package com.cabaggregator.driverservice.controllers.api;

import com.cabaggregator.driverservice.core.dto.DriverAddingDTO;
import com.cabaggregator.driverservice.core.dto.DriverDTO;
import com.cabaggregator.driverservice.services.IDriverService;
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
@RequestMapping("/api/v1/drivers")
@Tag(name = "Driver API Controller", description = "Provides CRUD-operations with drivers")
public class DriverApiController {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    private final IDriverService driverService;

    @Autowired
    public DriverApiController(IDriverService driverService) {
        this.driverService = driverService;
    }

    @GetMapping("/all")
    @Operation(summary = "Get all", description = "Allows to get all existing drivers")
    public ResponseEntity<List<DriverDTO>> getAllDrivers() {
        logger.info("Sending all drivers");

        List<DriverDTO> drivers = driverService.getAllDrivers();

        return ResponseEntity.status(HttpStatus.OK).body(drivers);
    }

    //TODO add get by id, phone, email, name
    @GetMapping("/{id}")
    @Operation(summary = "Get by id", description = "Allows to get existing driver by id")
    public ResponseEntity<DriverDTO> getDriverById(@NotNull @PathVariable long id) {
        logger.info("Sending driver with id={}", id);

        DriverDTO driver = driverService.getDriverById(id);

        return ResponseEntity.status(HttpStatus.OK).body(driver);
    }

    @PostMapping
    @Operation(summary = "Add/save", description = "Allows to add/save new driver")
    public ResponseEntity<DriverDTO> saveDriver(@Valid @RequestBody DriverAddingDTO driverAddingDTO) {
        logger.info("Saving driver with phone={}", driverAddingDTO.getPhoneNumber());

        DriverDTO driver = driverService.saveDriver(driverAddingDTO);

        return ResponseEntity.status(HttpStatus.CREATED).body(driver);
    }

    @PutMapping
    @Operation(summary = "Update", description = "Allows to update existing driver")
    public ResponseEntity<DriverDTO> updateDriver(@Valid @RequestBody DriverDTO driverDTO) {
        logger.info("Updating driver with id={}", driverDTO.getId());

        DriverDTO driver = driverService.updateDriver(driverDTO);

        return ResponseEntity.status(HttpStatus.OK).body(driver);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Delete", description = "Allows to delete existing driver by id")
    public ResponseEntity<Void> deleteDriverById(@NotNull @PathVariable long id) {
        logger.info("Deleting driver with id={}", id);

        driverService.deleteDriverById(id);

        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    @DeleteMapping("/all")
    @Operation(summary = "Delete all", description = "Allows to delete all existing drivers")
    public ResponseEntity<Void> deleteAllPassengers() {
        logger.info("Deleting all drivers");

        driverService.deleteAllDrivers();

        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}
