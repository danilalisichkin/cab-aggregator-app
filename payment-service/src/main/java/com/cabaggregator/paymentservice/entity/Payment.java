package com.cabaggregator.paymentservice.entity;

import com.cabaggregator.paymentservice.entity.enums.PaymentStatus;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "payments")
public class Payment {
    @Id
    private String paymentIntentId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "payment_account_user_id", nullable = false)
    private PaymentAccount paymentAccount;

    @Column(nullable = false)
    @Enumerated(value = EnumType.STRING)
    private PaymentStatus status;

    @CreationTimestamp
    @Column(nullable = false)
    private LocalDateTime createdAt;
}
