package com.cabaggregator.pricecalculationservice.controller.api;

import com.cabaggregator.pricecalculationservice.controller.doc.PriceCalculationControllerDoc;
import com.cabaggregator.pricecalculationservice.core.dto.PriceCalculationRequest;
import com.cabaggregator.pricecalculationservice.core.dto.PriceResponse;
import com.cabaggregator.pricecalculationservice.service.PriceCalculationService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Validated
@RestController
@RequiredArgsConstructor
@RequestMapping("api/v1/pricing")
public class PriceCalculationController implements PriceCalculationControllerDoc {
    private final PriceCalculationService priceCalculationService;

    @GetMapping
    public ResponseEntity<PriceResponse> calculatePrice(@RequestBody @Valid PriceCalculationRequest priceCalculationRequest) {
        PriceResponse price = priceCalculationService.calculatePrice(priceCalculationRequest);

        return ResponseEntity.status(HttpStatus.OK).body(price);
    }
}
