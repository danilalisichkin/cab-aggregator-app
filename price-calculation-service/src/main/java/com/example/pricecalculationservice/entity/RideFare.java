package com.example.pricecalculationservice.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "ride_fares")
public class RideFare {
    @Id
    private String fareName;

    @Column(nullable = false)
    private Long basePricePerKilometer;

    @Column(nullable = false)
    private Long basePricePerMinute;
}
