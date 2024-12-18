package com.cabaggregator.paymentservice.client;

import org.bson.types.ObjectId;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;

@FeignClient(value = "ride-service", path = "/api/v1/rides")
public interface RideServiceApiClient {

    @PutMapping("/{id}/payment-status")
    ResponseEntity<Void> setRidePaymentStatus(@PathVariable ObjectId id, boolean status);
}
