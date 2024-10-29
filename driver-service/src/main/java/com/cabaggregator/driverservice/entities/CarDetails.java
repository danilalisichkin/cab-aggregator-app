package com.cabaggregator.driverservice.entities;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "car_details")
public class CarDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "car_details_seq")
    @SequenceGenerator(name = "car_details_seq", sequenceName = "car_details_seq", allocationSize = 1, initialValue = 1)
    private long id;

    @Column(nullable = false)
    private LocalDate releaseDate;

    @Column(nullable = false)
    private int seatCapacity;
}
