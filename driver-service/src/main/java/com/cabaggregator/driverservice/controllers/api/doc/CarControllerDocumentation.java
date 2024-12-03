package com.cabaggregator.driverservice.controllers.api.doc;

import com.cabaggregator.driverservice.core.constant.ValidationErrors;
import com.cabaggregator.driverservice.core.dto.car.CarAddingDto;
import com.cabaggregator.driverservice.core.dto.car.CarDto;
import com.cabaggregator.driverservice.core.dto.car.CarFullDto;
import com.cabaggregator.driverservice.core.dto.car.CarUpdatingDto;
import com.cabaggregator.driverservice.core.dto.car.details.CarDetailsSettingDto;
import com.cabaggregator.driverservice.core.dto.page.PageDto;
import com.cabaggregator.driverservice.core.enums.sort.CarSortField;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.PositiveOrZero;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

@Tag(name = "Car API Controller", description = "Provides CRUD operations with cars and their details")
public interface CarControllerDocumentation {

    @Operation(summary = "Get page", description = "Allows to get page of existing cars")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successful response")
    })
    ResponseEntity<PageDto<CarDto>> getPageOfCars(
            @Parameter(name = "offset", description = "The starting point of the page", required = true)
            @RequestParam(defaultValue = "0") @PositiveOrZero Integer offset,

            @Parameter(name = "limit", description = "The number of cars to return", required = true)
            @RequestParam(defaultValue = "10") @Positive
            @Max(value = 20, message = ValidationErrors.INVALID_NUMBER_MAX_VALUE) Integer limit,

            @Parameter(name = "sort field", description = "Sorting criteria for the cars")
            @RequestParam(defaultValue = "id") CarSortField sortBy,

            @Parameter(name = "sort order", description = "Sorting criteria for the cars")
            @RequestParam(defaultValue = "ASC") Sort.Direction sortOrder);

    @Operation(summary = "Get car", description = "Allows to get existing car by its ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successful response"),
            @ApiResponse(responseCode = "400", description = "Bad request: invalid parameters"),
            @ApiResponse(responseCode = "400", description = "Bad request: missing required fields"),
            @ApiResponse(responseCode = "404", description = "Car not found: car with ID does not exist")
    })
    ResponseEntity<CarDto> getCar(
            @Parameter(name = "id", description = "ID of the car", required = true)
            @PathVariable Long id);

    @Operation(summary = "Get full car", description = "Allows to get existing car with details by its ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successful response"),
            @ApiResponse(responseCode = "400", description = "Bad request: invalid parameters"),
            @ApiResponse(responseCode = "400", description = "Bad request: missing required fields"),
            @ApiResponse(responseCode = "404", description = "Car not found: car with ID does not exist")
    })
    ResponseEntity<CarFullDto> getFullCar(
            @Parameter(name = "id", description = "ID of the car", required = true)
            @PathVariable Long id);

    @Operation(summary = "Add/save car", description = "Allows to add/save new car")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Successful response"),
            @ApiResponse(responseCode = "400", description = "Bad request: invalid parameters"),
            @ApiResponse(responseCode = "400", description = "Bad request: missing required fields"),
            @ApiResponse(responseCode = "400", description = "Bad request: invalid car release date provided"),
            @ApiResponse(responseCode = "409", description = "Conflict: license plate already belongs to another car")
    })
    ResponseEntity<CarDto> saveCar(
            @RequestBody @Valid CarAddingDto carAddingDto);

    @Operation(summary = "Update car", description = "Allows to update existing car by its ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successful response"),
            @ApiResponse(responseCode = "400", description = "Bad request: invalid parameters"),
            @ApiResponse(responseCode = "400", description = "Bad request: missing required fields"),
            @ApiResponse(responseCode = "404", description = "Car not found: car with ID does not exist"),
            @ApiResponse(responseCode = "409", description = "Conflict: license plate already belongs to another car")
    })
    ResponseEntity<CarDto> updateCar(
            @Parameter(name = "id", description = "ID of the car to be updated", required = true)
            @PathVariable Long id,
            @RequestBody @Valid CarUpdatingDto carDto);

    @Operation(summary = "Update car details", description = "Allows to update details of existing car by its ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successful response"),
            @ApiResponse(responseCode = "400", description = "Bad request: invalid parameters"),
            @ApiResponse(responseCode = "400", description = "Bad request: missing required fields"),
            @ApiResponse(responseCode = "400", description = "Bad request: invalid car release date provided"),
            @ApiResponse(responseCode = "404", description = "Car not found: car with ID does not exist")
    })
    ResponseEntity<CarFullDto> updateCarDetails(
            @Parameter(name = "id", description = "ID of the car to update details", required = true)
            @PathVariable Long id,
            @RequestBody @Valid CarDetailsSettingDto carDetailsDto);

    @Operation(summary = "Delete car", description = "Allows to delete existing car with details by its ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Successful response"),
            @ApiResponse(responseCode = "400", description = "Bad request: invalid parameters"),
            @ApiResponse(responseCode = "400", description = "Bad request: missing required fields"),
            @ApiResponse(responseCode = "404", description = "Car not found: car with ID does not exist")
    })
    ResponseEntity<Void> deleteCar(
            @Parameter(name = "id", description = "ID of the car to be deleted", required = true)
            @PathVariable Long id);
}
