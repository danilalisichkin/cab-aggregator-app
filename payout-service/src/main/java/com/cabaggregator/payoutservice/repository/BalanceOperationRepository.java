package com.cabaggregator.payoutservice.repository;

import com.cabaggregator.payoutservice.entity.BalanceOperation;
import com.cabaggregator.payoutservice.entity.PayoutAccount;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface BalanceOperationRepository
        extends JpaRepository<BalanceOperation, Long>, JpaSpecificationExecutor<BalanceOperation> {

    @Query("SELECT SUM(op.amount) FROM BalanceOperation op WHERE op.payoutAccount = :payoutAccount")
    Long getBalance(PayoutAccount payoutAccount);
}
