package com.cabaggregator.payoutservice.repository;

import com.cabaggregator.payoutservice.entity.PayoutAccount;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface PayoutAccountRepository extends JpaRepository<PayoutAccount, UUID> {
    boolean existsByStripeAccountId(String stripeAccountId);
}
