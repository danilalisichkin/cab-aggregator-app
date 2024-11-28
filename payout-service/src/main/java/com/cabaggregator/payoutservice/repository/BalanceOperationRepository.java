package com.cabaggregator.payoutservice.repository;

import com.cabaggregator.payoutservice.entity.BalanceOperation;
import com.cabaggregator.payoutservice.entity.PayoutAccount;
import com.cabaggregator.payoutservice.entity.enums.OperationType;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BalanceOperationRepository extends JpaRepository<BalanceOperation, Long> {
    Page<BalanceOperation> findAllByPayoutAccount(PayoutAccount payoutAccount, Pageable pageable);

    Page<BalanceOperation> findAllByPayoutAccountAndType(PayoutAccount payoutAccount, Pageable pageable, OperationType type);
}
