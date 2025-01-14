package com.cabaggregator.rideservice.client.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PriceResponse {
    private Long price;
    private String demand;
    private String weather;
}
