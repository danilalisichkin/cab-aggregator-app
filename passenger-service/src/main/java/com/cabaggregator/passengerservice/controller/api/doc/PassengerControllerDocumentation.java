package com.cabaggregator.passengerservice.controller.api.doc;

import com.cabaggregator.passengerservice.core.constant.ValidationErrors;
import com.cabaggregator.passengerservice.core.dto.PagedDto;
import com.cabaggregator.passengerservice.core.dto.PassengerAddingDto;
import com.cabaggregator.passengerservice.core.dto.PassengerDto;
import com.cabaggregator.passengerservice.core.dto.PassengerUpdatingDto;
import com.cabaggregator.passengerservice.core.enums.sort.PassengerSort;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.PositiveOrZero;
import org.hibernate.validator.constraints.UUID;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

@Tag(name = "Passenger API Controller", description = "Provides CRUD-operations with passengers")
public interface PassengerControllerDocumentation {

    @Operation(summary = "Get page", description = "Allows to get page of existing passengers")
    ResponseEntity<PagedDto<PassengerDto>> getPageOfPassengers(
            @RequestParam(name = "offset") @PositiveOrZero Integer offset,
            @RequestParam(name = "limit") @Positive Integer limit,
            @RequestParam(name = "sort", defaultValue = "id") PassengerSort sort);

    @Operation(summary = "Get", description = "Allows to get existing passenger by its id")
    ResponseEntity<PassengerDto> getPassenger(
            @PathVariable @NotEmpty @UUID(message = ValidationErrors.INVALID_UUID_FORMAT) String id);

    @Operation(summary = "Add/save", description = "Allows to add/save new passenger")
    ResponseEntity<PassengerDto> savePassenger(@RequestBody @Valid PassengerAddingDto passengerAddingDto);

    @Operation(summary = "Update",
            description = "Allows to update existing passenger by its id")
    ResponseEntity<PassengerDto> updatePassenger(
            @PathVariable @NotEmpty @UUID(message = ValidationErrors.INVALID_UUID_FORMAT) String id,
            @RequestBody @Valid PassengerUpdatingDto passengerDto);

    @Operation(summary = "Update rating",
            description = "Allows to update rating of existing passenger")
    ResponseEntity<PassengerDto> updatePassengerRating(
            @PathVariable @NotEmpty @UUID(message = ValidationErrors.INVALID_UUID_FORMAT) String id,
            @RequestBody @NotNull
            @Min(value = 0, message = ValidationErrors.INVALID_NUMBER_MIN_VALUE)
            @Max(value = 5, message = ValidationErrors.INVALID_NUMBER_MAX_VALUE)
            Double rating);

    @Operation(summary = "Delete", description = "Allows to delete existing passenger by its id")
    ResponseEntity<Void> deletePassenger(
            @PathVariable @NotEmpty @UUID(message = ValidationErrors.INVALID_UUID_FORMAT) String id);
}
