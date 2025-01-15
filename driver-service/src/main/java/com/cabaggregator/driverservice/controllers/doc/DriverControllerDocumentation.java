package com.cabaggregator.driverservice.controllers.doc;

import com.cabaggregator.driverservice.core.constant.ValidationErrors;
import com.cabaggregator.driverservice.core.dto.driver.DriverAddingDto;
import com.cabaggregator.driverservice.core.dto.driver.DriverDto;
import com.cabaggregator.driverservice.core.dto.driver.DriverUpdatingDto;
import com.cabaggregator.driverservice.core.dto.page.PageDto;
import com.cabaggregator.driverservice.core.enums.sort.DriverSortField;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.PositiveOrZero;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.UUID;

@Tag(name = "Driver API Controller", description = "Provides CRUD operations with drivers")
public interface DriverControllerDocumentation {

    @Operation(
            summary = "Get page",
            description = "Allows to get a page of existing drivers")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successful response"),
            @ApiResponse(responseCode = "400", description = "Bad request: invalid parameters or missing required fields")
    })
    ResponseEntity<PageDto<DriverDto>> getPageOfDrivers(
            @RequestParam(defaultValue = "0") @PositiveOrZero Integer offset,
            @RequestParam(defaultValue = "10") @Positive
            @Max(value = 20, message = ValidationErrors.INVALID_NUMBER_MAX_VALUE) Integer limit,
            @RequestParam(defaultValue = "id") DriverSortField sortBy,
            @RequestParam(defaultValue = "ASC") Sort.Direction sortOrder);

    @Operation(
            summary = "Get driver",
            description = "Allows to get existing driver by its ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successful response"),
            @ApiResponse(responseCode = "400", description = "Bad request: invalid parameters or missing required fields"),
            @ApiResponse(responseCode = "404", description = "Not found: driver does not exist")
    })
    ResponseEntity<DriverDto> getDriver(
            @Parameter(
                    description = "Driver identifier")
            @PathVariable UUID id);

    @Operation(
            summary = "Create driver",
            description = "Allows to create a new driver")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Successful response"),
            @ApiResponse(responseCode = "400", description = "Bad request: invalid parameters or missing required fields"),
            @ApiResponse(responseCode = "409",
                    description = "Conflict: provided unique data already belongs to other driver")
    })
    ResponseEntity<DriverDto> createDriver(
            @Parameter(
                    name = "Required data",
                    description = "Data that is required to create new driver",
                    required = true)
            @RequestBody @Valid DriverAddingDto driverAddingDTO);

    @Operation(
            summary = "Update driver",
            description = "Allows to update existing driver by its ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successful response"),
            @ApiResponse(responseCode = "400", description = "Bad request: invalid parameters or missing required fields"),
            @ApiResponse(responseCode = "404", description = "Not found: driver does not exist"),
            @ApiResponse(responseCode = "409",
                    description = "Conflict: provided unique data already belongs to other driver"),
    })
    ResponseEntity<DriverDto> updateDriver(
            @Parameter(
                    description = "Driver identifier")
            @PathVariable UUID id,
            @Parameter(
                    name = "Required data",
                    description = "Data of driver that will be updated",
                    required = true)
            @RequestBody @Valid DriverUpdatingDto driverDto);

    @Operation(
            summary = "Set car ID",
            description = "Allows to set the car ID for existing driver")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successful response"),
            @ApiResponse(responseCode = "400", description = "Bad request: invalid parameters or missing required fields"),
            @ApiResponse(responseCode = "404", description = "Not found: driver does not exist")
    })
    ResponseEntity<DriverDto> setDriverCar(
            @Parameter(
                    description = "Driver identifier")
            @PathVariable UUID id,
            @Parameter(
                    description = "Car identifier")
            @RequestBody @NotNull Long carId);

    @Operation(
            summary = "Delete driver",
            description = "Allows to delete existing driver by its ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Successful response"),
            @ApiResponse(responseCode = "400", description = "Bad request: invalid parameters or missing required fields"),
            @ApiResponse(responseCode = "404", description = "Not found: driver does not exist"),
    })
    ResponseEntity<Void> deleteDriver(
            @Parameter(
                    description = "Driver identifier")
            @PathVariable UUID id);
}
