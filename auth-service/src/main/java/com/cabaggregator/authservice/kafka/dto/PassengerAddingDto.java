package com.cabaggregator.authservice.kafka.dto;

import lombok.Data;

@Data
public class PassengerAddingDto {
    private String id;
    private String phoneNumber;
    private String email;
    private String firstName;
    private String lastName;
}
