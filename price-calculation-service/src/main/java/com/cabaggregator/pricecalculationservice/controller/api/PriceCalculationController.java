package com.cabaggregator.pricecalculationservice.controller.api;

import com.cabaggregator.pricecalculationservice.controller.doc.PriceCalculationControllerDoc;
import com.cabaggregator.pricecalculationservice.core.dto.PriceCalculationRequest;
import com.cabaggregator.pricecalculationservice.core.dto.PriceResponse;
import com.cabaggregator.pricecalculationservice.service.PriceCalculationService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@Validated
@RestController
@RequiredArgsConstructor
@RequestMapping("api/v1/pricing")
public class PriceCalculationController implements PriceCalculationControllerDoc {
    private final PriceCalculationService priceCalculationService;

    @PostMapping
    public ResponseEntity<PriceResponse> calculatePrice(
            @RequestBody @Valid PriceCalculationRequest priceCalculationRequest) {

        log.info("Price calculation request: {}", priceCalculationRequest);

        PriceResponse price = priceCalculationService.calculatePrice(priceCalculationRequest);

        return ResponseEntity.status(HttpStatus.OK).body(price);
    }
}
