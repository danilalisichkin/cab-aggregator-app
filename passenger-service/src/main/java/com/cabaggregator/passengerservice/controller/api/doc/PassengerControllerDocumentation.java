package com.cabaggregator.passengerservice.controller.api.doc;

import com.cabaggregator.passengerservice.core.dto.PagedDto;
import com.cabaggregator.passengerservice.core.dto.PassengerAddingDto;
import com.cabaggregator.passengerservice.core.dto.PassengerDto;
import com.cabaggregator.passengerservice.core.dto.PassengerUpdatingDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

@Tag(name = "Passenger API Controller", description = "Provides CRUD-operations with passengers")
public interface PassengerControllerDocumentation {

    @Operation(summary = "Get page", description = "Allows to get page of existing passengers")
    ResponseEntity<PagedDto<PassengerDto>> getPageOfPassengers(
            @Positive @RequestParam(required = false, name = "page", defaultValue = "1") int pageNumber,
            @Positive @RequestParam(required = false, name = "size", defaultValue = "10") int pageSize,
            @RequestParam(required = false, name = "sort", defaultValue = "id") String sortField,
            @RequestParam(required = false, name = "order", defaultValue = "desc") String sortOrder);

    @Operation(summary = "Get", description = "Allows to get existing passenger by its id")
    ResponseEntity<PassengerDto> getPassenger(@NotNull @PathVariable long id);

    @Operation(summary = "Add/save", description = "Allows to add/save new passenger")
    ResponseEntity<PassengerDto> savePassenger(@Valid @RequestBody PassengerAddingDto passengerAddingDto);

    @Operation(summary = "Update",
            description = "Allows to update existing passenger by its id")
    ResponseEntity<PassengerDto> updatePassenger(
            @NotNull @PathVariable long id,
            @Valid @RequestBody PassengerUpdatingDto passengerDto);

    @Operation(summary = "Delete", description = "Allows to delete existing passenger by its id")
    ResponseEntity<Void> deletePassengerById(@NotNull @PathVariable long id);
}
