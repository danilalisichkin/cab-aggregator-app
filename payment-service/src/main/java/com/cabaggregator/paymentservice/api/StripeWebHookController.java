package com.cabaggregator.paymentservice.api;

import com.cabaggregator.paymentservice.stripe.StripeHttpHeaders;
import jakarta.validation.constraints.NotEmpty;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Validated
@RestController
@RequestMapping("api/v1/stripe/webhooks")
public class StripeWebHookController {

    @PostMapping
    public ResponseEntity<String> handleStripeWebhook(
            @RequestBody @NotEmpty String payload,
            @RequestHeader(StripeHttpHeaders.STRIPE_SIGNATURE) String sigHeader) {

        return ResponseEntity.status(HttpStatus.OK).build();
    }
}