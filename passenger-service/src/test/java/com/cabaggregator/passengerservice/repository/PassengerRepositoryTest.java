package com.cabaggregator.passengerservice.repository;

import com.cabaggregator.passengerservice.PassengerTestUtil;
import com.cabaggregator.passengerservice.entity.Passenger;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@Tag("integration")
@DataJpaTest
@Testcontainers
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class PassengerRepositoryTest {
    @Autowired
    private PassengerRepository passengerRepository;

    private Passenger passenger;

    @Container
    public static PostgreSQLContainer<?> postgresqlContainer = new PostgreSQLContainer<>("postgres:14-alpine")
            .withDatabaseName("passenger_database")
            .withUsername("postgres")
            .withPassword("root");

    @DynamicPropertySource
    public static void setProperties(DynamicPropertyRegistry registry) {
        registry.add("spring.datasource.url", postgresqlContainer::getJdbcUrl);
        registry.add("spring.datasource.username", postgresqlContainer::getUsername);
        registry.add("spring.datasource.password", postgresqlContainer::getPassword);
    }

    @BeforeEach
    public void setUp() {
        passenger = PassengerTestUtil.buildPassenger();
        passengerRepository.deleteAll();
    }

    @Test
    void findByPhoneNumber_ShouldReturnPassenger_WhenPassengerExists() {
        Passenger savedPassenger = passengerRepository.save(passenger);
        Optional<Passenger> foundPassenger = passengerRepository.findByPhoneNumber(savedPassenger.getPhoneNumber());

        assertThat(foundPassenger).contains(savedPassenger);
    }

    @Test
    void findByPhoneNumber_ShouldReturnNull_WhenPassengerDoesNotExist() {
        Optional<Passenger> foundPassenger = passengerRepository.findByPhoneNumber(passenger.getPhoneNumber());

        assertThat(foundPassenger).isEmpty();
    }

    @Test
    void findByEmail_ShouldReturnPassenger_WhenPassengerExists() {
        Passenger savedPassenger = passengerRepository.save(passenger);
        Optional<Passenger> foundPassenger = passengerRepository.findByEmail(savedPassenger.getEmail());

        assertThat(foundPassenger).contains(savedPassenger);
    }

    @Test
    void findByEmail_ShouldReturnNull_WhenPassengerDoesNotExist() {
        Optional<Passenger> foundPassenger = passengerRepository.findByEmail(passenger.getEmail());

        assertThat(foundPassenger).isEmpty();
    }

    @Test
    void existsByPhoneNumber_ShouldReturnTrue_WhenPassengerExists() {
        passengerRepository.save(passenger);

        boolean result = passengerRepository.existsByPhoneNumber(passenger.getPhoneNumber());

        assertThat(result).isTrue();
    }

    @Test
    void existsByPhoneNumber_ShouldReturnFalse_WhenPassengerWithPhoneNumberDoesNotExist() {
        boolean result = passengerRepository.existsByPhoneNumber(passenger.getPhoneNumber());

        assertThat(result).isFalse();
    }

    @Test
    void existsByEmail_ShouldReturnTrue_WhenPassengerExists() {
        passengerRepository.save(passenger);

        boolean result = passengerRepository.existsByEmail(passenger.getEmail());

        assertThat(result).isTrue();
    }

    @Test
    void existsByEmail_ShouldReturnFalse_WhenPassengerDoesNotExist() {
        boolean result = passengerRepository.existsByEmail(passenger.getEmail());

        assertThat(result).isFalse();
    }
}
