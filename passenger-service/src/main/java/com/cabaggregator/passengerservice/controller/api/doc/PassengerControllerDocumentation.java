package com.cabaggregator.passengerservice.controller.api.doc;

import com.cabaggregator.passengerservice.core.constant.ValidationErrors;
import com.cabaggregator.passengerservice.core.dto.page.PageDto;
import com.cabaggregator.passengerservice.core.dto.passenger.PassengerAddingDto;
import com.cabaggregator.passengerservice.core.dto.passenger.PassengerDto;
import com.cabaggregator.passengerservice.core.dto.passenger.PassengerUpdatingDto;
import com.cabaggregator.passengerservice.core.enums.sort.PassengerSortField;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.PositiveOrZero;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.UUID;

@Tag(name = "Passenger API Controller", description = "Provides CRUD-operations with passengers")
public interface PassengerControllerDocumentation {

    @Operation(summary = "Get page", description = "Allows to get page of existing passengers")
    ResponseEntity<PageDto<PassengerDto>> getPageOfPassengers(
            @RequestParam(defaultValue = "0") @PositiveOrZero Integer offset,
            @RequestParam(defaultValue = "10") @Positive
            @Max(value = 20, message = ValidationErrors.INVALID_NUMBER_MAX_VALUE) Integer limit,
            @RequestParam(defaultValue = "id") PassengerSortField sortBy,
            @RequestParam(defaultValue = "ASC") Sort.Direction sortOrder);

    @Operation(summary = "Get", description = "Allows to get existing passenger by its id")
    ResponseEntity<PassengerDto> getPassenger(@PathVariable @org.hibernate.validator.constraints.UUID UUID id);

    @Operation(summary = "Add/save", description = "Allows to add/save new passenger")
    ResponseEntity<PassengerDto> savePassenger(@RequestBody @Valid PassengerAddingDto passengerAddingDto);

    @Operation(summary = "Update",
            description = "Allows to update existing passenger by its id")
    ResponseEntity<PassengerDto> updatePassenger(
            @PathVariable UUID id,
            @RequestBody @Valid PassengerUpdatingDto passengerDto);

    @Operation(summary = "Update rating",
            description = "Allows to update rating of existing passenger")
    ResponseEntity<PassengerDto> updatePassengerRating(
            @PathVariable UUID id,
            @RequestBody @NotNull
            @Min(value = 0, message = ValidationErrors.INVALID_NUMBER_MIN_VALUE)
            @Max(value = 5, message = ValidationErrors.INVALID_NUMBER_MAX_VALUE)
            Double rating);

    @Operation(summary = "Delete", description = "Allows to delete existing passenger by its id")
    ResponseEntity<Void> deletePassenger(@PathVariable UUID id);
}
