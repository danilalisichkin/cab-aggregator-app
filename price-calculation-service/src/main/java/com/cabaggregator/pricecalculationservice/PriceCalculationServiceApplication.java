package com.cabaggregator.pricecalculationservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableCaching
@EnableFeignClients
@EnableDiscoveryClient
@ConfigurationPropertiesScan
public class PriceCalculationServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(PriceCalculationServiceApplication.class, args);
    }

}
