package com.cabaggregator.pricecalculationservice.controller.doc;

import com.cabaggregator.pricecalculationservice.core.dto.PriceCalculationRequest;
import com.cabaggregator.pricecalculationservice.core.dto.PriceResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;

@Tag(name = "Price Calculation API Controller", description = "Calculates price of requested ride")
public interface PriceCalculationControllerDoc {

    @Operation(
            summary = "Calculate price",
            description = "Allows to calculate price of ride")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successful response"),
            @ApiResponse(responseCode = "400", description = "Bad request: invalid parameters or missing required fields"),
    })
    ResponseEntity<PriceResponse> calculatePrice(
            @Parameter(
                    name = "Required data",
                    description = "Data that is required to calculate price",
                    required = true)
            @RequestBody @Valid PriceCalculationRequest priceCalculationRequest);
}
