package com.cabaggregator.paymentservice.repository;

import com.cabaggregator.paymentservice.entity.Payment;
import com.cabaggregator.paymentservice.entity.PaymentContext;
import com.cabaggregator.paymentservice.entity.enums.PaymentContextType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface PaymentContextRepository extends JpaRepository<PaymentContext, Long> {
    Optional<PaymentContext> findByPayment(Payment payment);

    List<PaymentContext> findAllByTypeAndContextId(PaymentContextType contextType, String contextId);
}
