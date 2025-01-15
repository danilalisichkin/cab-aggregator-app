package com.cabaggregator.payoutservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication
@EnableDiscoveryClient
@ConfigurationPropertiesScan
public class PayoutServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(PayoutServiceApplication.class, args);
    }

}
