package com.cabaggregator.paymentservice.service;

public interface StripeWebhookService {
    void processWebhookEvent(String payload, String sigHeader);
}
