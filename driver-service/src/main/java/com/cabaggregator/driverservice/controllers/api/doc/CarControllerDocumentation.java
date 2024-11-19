package com.cabaggregator.driverservice.controllers.api.doc;

import com.cabaggregator.driverservice.core.dto.car.CarAddingDto;
import com.cabaggregator.driverservice.core.dto.car.CarDto;
import com.cabaggregator.driverservice.core.dto.car.CarFullDto;
import com.cabaggregator.driverservice.core.dto.car.CarUpdatingDto;
import com.cabaggregator.driverservice.core.dto.car.details.CarDetailsSettingDto;
import com.cabaggregator.driverservice.core.dto.page.PagedDto;
import com.cabaggregator.driverservice.core.enums.sort.CarSort;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.PositiveOrZero;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

@Tag(name = "Car API Controller", description = "Provides CRUD operations with cars and their details")
public interface CarControllerDocumentation {

    @Operation(summary = "Get page", description = "Allows to get page of existing cars")
    ResponseEntity<PagedDto<CarDto>> getPageOfCars(
            @Parameter(name = "offset", description = "The starting point of the page", required = true)
            @RequestParam(name = "offset") @PositiveOrZero Integer offset,

            @Parameter(name = "limit", description = "The number of cars to return", required = true)
            @RequestParam(name = "limit") @Positive Integer limit,

            @Parameter(name = "sort", description = "Sorting criteria for the cars")
            @RequestParam(name = "sort") CarSort sort);

    @Operation(summary = "Get car", description = "Allows to get existing car by its id")
    ResponseEntity<CarDto> getCar(
            @Parameter(name = "id", description = "ID of the car", required = true)
            @PathVariable @NotNull Long id);

    @Operation(summary = "Get full car", description = "Allows to get existing car with details by its id")
    ResponseEntity<CarFullDto> getFullCar(
            @Parameter(name = "id", description = "ID of the car", required = true)
            @PathVariable @NotNull Long id);

    @Operation(summary = "Add/save", description = "Allows to add/save new car")
    ResponseEntity<CarDto> saveCar(
            @RequestBody @Valid CarAddingDto carAddingDto);

    @Operation(summary = "Update", description = "Allows to update existing car by its id")
    ResponseEntity<CarDto> updateCar(
            @Parameter(name = "id", description = "ID of the car to be updated", required = true)
            @PathVariable @NotNull Long id,
            @RequestBody @Valid CarUpdatingDto carDto);

    @Operation(summary = "Update", description = "Allows to update details of existing car by its id")
    ResponseEntity<CarFullDto> updateCarDetails(
            @Parameter(name = "id", description = "ID of the car to update details", required = true)
            @PathVariable @NotNull Long id,
            @RequestBody @Valid CarDetailsSettingDto carDetailsDto);

    @Operation(summary = "Delete", description = "Allows to delete existing car with details by its id")
    ResponseEntity<Void> deleteCar(
            @Parameter(name = "id", description = "ID of the car to be deleted", required = true)
            @NotNull @PathVariable Long id);
}
