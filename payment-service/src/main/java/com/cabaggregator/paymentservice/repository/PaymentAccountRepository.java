package com.cabaggregator.paymentservice.repository;

import com.cabaggregator.paymentservice.entity.PaymentAccount;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface PaymentAccountRepository extends JpaRepository<PaymentAccount, UUID> {
    boolean existsByStripeCustomerId(String stripeCustomerId);
}
