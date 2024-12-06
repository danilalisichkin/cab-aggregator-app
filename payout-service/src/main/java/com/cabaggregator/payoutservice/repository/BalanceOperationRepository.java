package com.cabaggregator.payoutservice.repository;

import com.cabaggregator.payoutservice.core.enums.OperationType;
import com.cabaggregator.payoutservice.entity.BalanceOperation;
import com.cabaggregator.payoutservice.entity.PayoutAccount;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;

@Repository
public interface BalanceOperationRepository extends JpaRepository<BalanceOperation, Long> {

    @Query("SELECT SUM(op.amount) FROM BalanceOperation op WHERE op.payoutAccount = :payoutAccount")
    Long getBalance(PayoutAccount payoutAccount);

    Page<BalanceOperation> findAllByPayoutAccountAndCreatedAtBetween(
            PayoutAccount payoutAccount, Pageable pageable, LocalDateTime start, LocalDateTime end);

    Page<BalanceOperation> findAllByPayoutAccountAndTypeAndCreatedAtBetween(
            PayoutAccount payoutAccount, Pageable pageable, OperationType type, LocalDateTime start, LocalDateTime end);
}
