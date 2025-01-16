package com.cabaggregator.paymentservice.client;

import com.cabaggregator.paymentservice.client.enums.RidePaymentStatus;
import org.bson.types.ObjectId;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(value = "ride-service", path = "/api/v1/rides")
public interface RideServiceApiClient {

    @PatchMapping("/{id}/payment-status")
    ResponseEntity<Void> setRidePaymentStatus(@PathVariable ObjectId id, RidePaymentStatus paymentStatus);
}
