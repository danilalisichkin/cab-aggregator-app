package com.cabaggregator.pricecalculationservice.core.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "Server response with calculated ride price and additional info about increase coefficients")
public class PriceResponse {
    private Long price;
    private String demand;
    private String weather;
}
