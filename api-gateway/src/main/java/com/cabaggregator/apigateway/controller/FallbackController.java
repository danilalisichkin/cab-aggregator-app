package com.cabaggregator.apigateway.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static com.cabaggregator.apigateway.constant.FallbackMessages.AUTH_FALLBACK_RESPONSE;
import static com.cabaggregator.apigateway.constant.FallbackMessages.CAR_FALLBACK_RESPONSE;
import static com.cabaggregator.apigateway.constant.FallbackMessages.DRIVER_FALLBACK_RESPONSE;
import static com.cabaggregator.apigateway.constant.FallbackMessages.PASSENGER_FALLBACK_RESPONSE;
import static com.cabaggregator.apigateway.constant.FallbackMessages.PAYMENT_FALLBACK_RESPONSE;
import static com.cabaggregator.apigateway.constant.FallbackMessages.PAYOUT_FALLBACK_RESPONSE;
import static com.cabaggregator.apigateway.constant.FallbackMessages.PRICING_FALLBACK_RESPONSE;
import static com.cabaggregator.apigateway.constant.FallbackMessages.PROMO_CODE_FALLBACK_RESPONSE;
import static com.cabaggregator.apigateway.constant.FallbackMessages.PROMO_STAT_FALLBACK_RESPONSE;
import static com.cabaggregator.apigateway.constant.FallbackMessages.RATING_FALLBACK_RESPONSE;
import static com.cabaggregator.apigateway.constant.FallbackMessages.RIDE_FALLBACK_RESPONSE;
import static com.cabaggregator.apigateway.constant.FallbackMessages.USER_FALLBACK_RESPONSE;
import static org.springframework.http.HttpStatus.SERVICE_UNAVAILABLE;
import static org.springframework.web.bind.annotation.RequestMethod.DELETE;
import static org.springframework.web.bind.annotation.RequestMethod.GET;
import static org.springframework.web.bind.annotation.RequestMethod.PATCH;
import static org.springframework.web.bind.annotation.RequestMethod.POST;
import static org.springframework.web.bind.annotation.RequestMethod.PUT;

@RestController
@RequestMapping("/fallback")
public class FallbackController {
    @RequestMapping(value = "/users", method = {GET, POST, PUT, DELETE, PATCH})
    public ResponseEntity<String> usersFallback() {
        return ResponseEntity.status(SERVICE_UNAVAILABLE).body(USER_FALLBACK_RESPONSE);
    }

    @RequestMapping(value = "/auth", method = {GET, POST, PUT, DELETE, PATCH})
    public ResponseEntity<String> authFallback() {
        return ResponseEntity.status(SERVICE_UNAVAILABLE).body(AUTH_FALLBACK_RESPONSE);
    }

    @RequestMapping(value = "/passengers", method = {GET, POST, PUT, DELETE, PATCH})
    public ResponseEntity<String> passengersFallback() {
        return ResponseEntity.status(SERVICE_UNAVAILABLE).body(PASSENGER_FALLBACK_RESPONSE);
    }

    @RequestMapping(value = "/drivers", method = {GET, POST, PUT, DELETE, PATCH})
    public ResponseEntity<String> driversFallback() {
        return ResponseEntity.status(SERVICE_UNAVAILABLE).body(DRIVER_FALLBACK_RESPONSE);
    }

    @RequestMapping(value = "/cars", method = {GET, POST, PUT, DELETE, PATCH})
    public ResponseEntity<String> carsFallback() {
        return ResponseEntity.status(SERVICE_UNAVAILABLE).body(CAR_FALLBACK_RESPONSE);
    }

    @RequestMapping(value = "/rides", method = {GET, POST, PUT, DELETE, PATCH})
    public ResponseEntity<String> ridesFallback() {
        return ResponseEntity.status(SERVICE_UNAVAILABLE).body(RIDE_FALLBACK_RESPONSE);
    }

    @RequestMapping(value = "/promo-codes", method = {GET, POST, PUT, DELETE, PATCH})
    public ResponseEntity<String> promoCodesFallback() {
        return ResponseEntity.status(SERVICE_UNAVAILABLE).body(PROMO_CODE_FALLBACK_RESPONSE);
    }

    @RequestMapping(value = "/promo-stats", method = {GET, POST, PUT, DELETE, PATCH})
    public ResponseEntity<String> promoStatsFallback() {
        return ResponseEntity.status(SERVICE_UNAVAILABLE).body(PROMO_STAT_FALLBACK_RESPONSE);
    }

    @RequestMapping(value = "/pricing", method = {GET, POST, PUT, DELETE, PATCH})
    public ResponseEntity<String> pricingFallback() {
        return ResponseEntity.status(SERVICE_UNAVAILABLE).body(PRICING_FALLBACK_RESPONSE);
    }

    @RequestMapping(value = "/rates", method = {GET, POST, PUT, DELETE, PATCH})
    public ResponseEntity<String> ratesFallback() {
        return ResponseEntity.status(SERVICE_UNAVAILABLE).body(RATING_FALLBACK_RESPONSE);
    }

    @RequestMapping(value = "/payment", method = {GET, POST, PUT, DELETE, PATCH})
    public ResponseEntity<String> paymentFallback() {
        return ResponseEntity.status(SERVICE_UNAVAILABLE).body(PAYMENT_FALLBACK_RESPONSE);
    }

    @RequestMapping(value = "/payout", method = {GET, POST, PUT, DELETE, PATCH})
    public ResponseEntity<String> payoutFallback() {
        return ResponseEntity.status(SERVICE_UNAVAILABLE).body(PAYOUT_FALLBACK_RESPONSE);
    }
}
