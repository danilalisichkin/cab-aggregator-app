package com.cabaggregator.pricecalculationservice.controller.api;

import com.cabaggregator.pricecalculationservice.core.dto.PriceCalculationRequest;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Validated
@RestController
@RequestMapping("api/v1/pricing")
public class PriceCalculationController {
    @GetMapping
    public ResponseEntity<Long> calculatePrice(@RequestBody @Valid PriceCalculationRequest priceCalculationRequest) {

        return ResponseEntity.status(HttpStatus.OK).build();
    }
}
