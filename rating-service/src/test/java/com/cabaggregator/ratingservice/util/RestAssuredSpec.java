package com.cabaggregator.ratingservice.util;

import io.restassured.builder.RequestSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class RestAssuredSpec {

    @Value("${auth.driver-token}")
    private String driverToken;

    @Value("${auth.passenger-token}")
    private String passengerToken;

    public RequestSpecification getDriverAuthSpec() {
        return new RequestSpecBuilder()
                .addHeader("Authorization", "Bearer " + driverToken)
                .setContentType(ContentType.JSON)
                .build();
    }

    public RequestSpecification getPassengerAuthSpec() {
        return new RequestSpecBuilder()
                .addHeader("Authorization", "Bearer " + passengerToken)
                .setContentType(ContentType.JSON)
                .build();
    }
}