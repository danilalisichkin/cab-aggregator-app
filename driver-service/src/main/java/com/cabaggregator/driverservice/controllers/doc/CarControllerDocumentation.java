package com.cabaggregator.driverservice.controllers.doc;

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
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
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

@SecurityRequirement(name = "token")
@Tag(name = "Car API Controller", description = "Provides CRUD operations with cars and their details")
public interface CarControllerDocumentation {

    @Operation(
            summary = "Get page",
            description = "Allows to get page of existing cars")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successful response"),
            @ApiResponse(responseCode = "400", description = "Bad request: invalid parameters or missing required fields")
    })
    ResponseEntity<PageDto<CarDto>> getPageOfCars(
            @RequestParam(defaultValue = "0") @PositiveOrZero Integer offset,
            @RequestParam(defaultValue = "10") @Positive
            @Max(value = 20, message = ValidationErrors.INVALID_NUMBER_MAX_VALUE) Integer limit,
            @RequestParam(defaultValue = "id") CarSortField sortBy,
            @RequestParam(defaultValue = "ASC") Sort.Direction sortOrder);

    @Operation(
            summary = "Get car",
            description = "Allows to get existing car by its ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successful response"),
            @ApiResponse(responseCode = "400", description = "Bad request: invalid parameters or missing required fields"),
            @ApiResponse(responseCode = "404", description = "Not found: car does not exist")
    })
    ResponseEntity<CarDto> getCar(
            @Parameter(
                    description = "Car identifier")
            @PathVariable Long id);

    @Operation(
            summary = "Get full car",
            description = "Allows to get existing car with details by its ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successful response"),
            @ApiResponse(responseCode = "400", description = "Bad request: invalid parameters or missing required fields"),
            @ApiResponse(responseCode = "404", description = "Not found: car does not exist")
    })
    ResponseEntity<CarFullDto> getFullCar(
            @Parameter(
                    description = "Car identifier")
            @PathVariable Long id);

    @Operation(
            summary = "Create car",
            description = "Allows to create a new car")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Successful response"),
            @ApiResponse(responseCode = "400", description = "Bad request: invalid parameters or missing required fields"),
            @ApiResponse(responseCode = "409",
                    description = "Conflict: provided unique data already belongs to other car")
    })
    ResponseEntity<CarDto> createCar(
            @Parameter(
                    name = "Required data",
                    description = "Data that is required to create a new car",
                    required = true)
            @RequestBody @Valid CarAddingDto carAddingDto);

    @Operation(
            summary = "Update car",
            description = "Allows to update existing car by its ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successful response"),
            @ApiResponse(responseCode = "400", description = "Bad request: invalid parameters or missing required fields"),
            @ApiResponse(responseCode = "404", description = "Not found: car does not exist"),
            @ApiResponse(responseCode = "409",
                    description = "Conflict: provided unique data already belongs to other car"),
    })
    ResponseEntity<CarDto> updateCar(
            @Parameter(
                    description = "Car identifier")
            @PathVariable Long id,
            @Parameter(
                    name = "Required data",
                    description = "Data of car that will be updated",
                    required = true)
            @RequestBody @Valid CarUpdatingDto carDto);

    @Operation(
            summary = "Update car details",
            description = "Allows to update details of existing car by its ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successful response"),
            @ApiResponse(responseCode = "400", description = "Bad request: invalid parameters or missing required fields"),
            @ApiResponse(responseCode = "404", description = "Not found: car does not exist")
    })
    ResponseEntity<CarFullDto> updateCarDetails(
            @Parameter(
                    description = "Car identifier")
            @PathVariable Long id,
            @Parameter(
                    name = "Required data",
                    description = "Data of car details that will be connected with car",
                    required = true)
            @RequestBody @Valid CarDetailsSettingDto carDetailsDto);

    @Operation(
            summary = "Delete car",
            description = "Allows to delete existing car with details by its ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Successful response"),
            @ApiResponse(responseCode = "400", description = "Bad request: invalid parameters or missing required fields"),
            @ApiResponse(responseCode = "404", description = "Not found: car does not exist"),
    })
    ResponseEntity<Void> deleteCar(
            @Parameter(
                    description = "Car identifier")
            @PathVariable Long id);
}
