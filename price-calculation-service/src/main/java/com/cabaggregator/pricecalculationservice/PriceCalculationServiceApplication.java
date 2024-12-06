package com.cabaggregator.pricecalculationservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;

@SpringBootApplication
@ConfigurationPropertiesScan
public class PriceCalculationServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(PriceCalculationServiceApplication.class, args);
    }

}
