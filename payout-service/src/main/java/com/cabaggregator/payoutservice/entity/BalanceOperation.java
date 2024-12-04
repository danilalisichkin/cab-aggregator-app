package com.cabaggregator.payoutservice.entity;

import com.cabaggregator.payoutservice.entity.enums.OperationType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Index;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.Immutable;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
@Immutable
@Table(name = "balance_operations", indexes = {
        @Index(name = "idx_payout_account", columnList = "payout_account_user_id"),
        @Index(name = "idx_created_at", columnList = "created_at"),
        @Index(name = "idx_payout_account_created_at", columnList = "payout_account_user_id, created_at"),
        @Index(name = "idx_payout_account_type_created_at", columnList = "payout_account_user_id, type, created_at")
})
public class BalanceOperation {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "payout_account_id", nullable = false)
    private PayoutAccount payoutAccount;

    @Column(nullable = false)
    private Long amount;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private OperationType type;

    @Column(nullable = false)
    private String transcript;

    @CreationTimestamp
    @Column(nullable = false)
    private LocalDateTime createdAt;
}
