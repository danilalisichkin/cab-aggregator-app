package com.cabaggregator.paymentservice.controller.api;

import com.cabaggregator.paymentservice.controller.doc.PaymentControllerDoc;
import com.cabaggregator.paymentservice.core.dto.payment.PaymentDto;
import com.cabaggregator.paymentservice.core.dto.payment.PaymentRequest;
import com.cabaggregator.paymentservice.core.dto.payment.PaymentResponse;
import com.cabaggregator.paymentservice.service.PaymentService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.bson.types.ObjectId;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Validated
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/payments")
public class PaymentController implements PaymentControllerDoc {

    private final PaymentService paymentService;

    @PostMapping
    public ResponseEntity<PaymentResponse> createPayment(@RequestBody @Valid PaymentRequest paymentRequest) {
        PaymentResponse paymentResponse = paymentService.createPayment(paymentRequest);

        return ResponseEntity.status(HttpStatus.CREATED).body(paymentResponse);
    }

    @GetMapping("/ride/{rideId}")
    public ResponseEntity<List<PaymentDto>> getRidePayments(@PathVariable ObjectId rideId) {
        List<PaymentDto> ridePayments = paymentService.getRidePayments(rideId);

        return ResponseEntity.status(HttpStatus.OK).body(ridePayments);
    }
}
