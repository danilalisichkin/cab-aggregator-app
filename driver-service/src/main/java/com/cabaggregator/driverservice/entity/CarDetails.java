package com.cabaggregator.driverservice.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

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

    @OneToOne
    @JoinColumn(name = "car_id", nullable = false)
    private Car car;
}
