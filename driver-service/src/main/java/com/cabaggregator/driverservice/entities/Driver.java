package com.cabaggregator.driverservice.entities;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "drivers")
public class Driver {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "drivers_seq")
    @SequenceGenerator(name = "drivers_seq", sequenceName = "drivers_seq", allocationSize = 1, initialValue = 1)
    private long id;

    @Column(unique = true, nullable = false)
    private String phoneNumber;

    @Column(unique = true, nullable = false)
    private String email;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false, precision = 3, scale = 2)
    private BigDecimal rating;

    @OneToOne
    @JoinColumn(name = "car_id")
    private Car car;
}
