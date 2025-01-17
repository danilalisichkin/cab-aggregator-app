package com.cabaggregator.driverservice.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.MapsId;
import jakarta.persistence.OneToOne;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Entity
@Getter
@Setter
@Builder(toBuilder = true)
@NoArgsConstructor
@AllArgsConstructor
public class CarDetails {
    @Id
    private Long id;

    @OneToOne
    @MapsId
    @JoinColumn(name = "car_id")
    private Car car;

    @Column(nullable = false)
    private LocalDate releaseDate;

    @Column(nullable = false)
    private Integer seatCapacity;
}
