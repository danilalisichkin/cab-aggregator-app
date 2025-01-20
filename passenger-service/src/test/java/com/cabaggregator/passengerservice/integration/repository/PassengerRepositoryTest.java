package com.cabaggregator.passengerservice.integration.repository;

import com.cabaggregator.passengerservice.config.AbstractPostgresIntegrationTest;
import com.cabaggregator.passengerservice.entity.Passenger;
import com.cabaggregator.passengerservice.repository.PassengerRepository;
import com.cabaggregator.passengerservice.util.PassengerTestUtil;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.jdbc.Sql;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@Tag("integration")
@DataJpaTest
@Sql(scripts = {
        "classpath:/postgresql/import_passengers.sql"},
        executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class PassengerRepositoryTest extends AbstractPostgresIntegrationTest {
    @Autowired
    private PassengerRepository passengerRepository;

    @Test
    void findByPhoneNumber_ShouldReturnPassenger_WhenPassengerWithPhoneNumberExists() {
        Passenger passenger = PassengerTestUtil.buildDefaultPassenger();

        Optional<Passenger> actual = passengerRepository.findByPhoneNumber(passenger.getPhoneNumber());

        assertThat(actual)
                .isPresent()
                .contains(passenger);
    }

    @Test
    void findByPhoneNumber_ShouldReturnNull_WhenPassengerWithPhoneNumberDoesNotExist() {
        Optional<Passenger> actual =
                passengerRepository.findByPhoneNumber(PassengerTestUtil.NOT_EXISTING_PHONE_NUMBER);

        assertThat(actual).isEmpty();
    }

    @Test
    void findByEmail_ShouldReturnPassenger_WhenPassengerWithEmailExists() {
        Passenger passenger = PassengerTestUtil.buildDefaultPassenger();

        Optional<Passenger> actual = passengerRepository.findByEmail(passenger.getEmail());

        assertThat(actual)
                .isPresent()
                .contains(passenger);
    }

    @Test
    void findByEmail_ShouldReturnNull_WhenPassengerWithEmailDoesNotExist() {
        Optional<Passenger> actual =
                passengerRepository.findByEmail(PassengerTestUtil.NOT_EXISTING_EMAIL);

        assertThat(actual).isEmpty();
    }

    @Test
    void existsByPhoneNumber_ShouldReturnTrue_WhenPassengerWithPhoneExists() {
        boolean actual = passengerRepository.existsByPhoneNumber(PassengerTestUtil.PHONE_NUMBER);

        assertThat(actual).isTrue();
    }

    @Test
    void existsByPhoneNumber_ShouldReturnFalse_WhenPassengerWithPhoneNumberDoesNotExist() {
        boolean actual = passengerRepository.existsByPhoneNumber(PassengerTestUtil.NOT_EXISTING_PHONE_NUMBER);

        assertThat(actual).isFalse();
    }

    @Test
    void existsByEmail_ShouldReturnTrue_WhenPassengerWithEmailExists() {
        boolean result = passengerRepository.existsByEmail(PassengerTestUtil.EMAIL);

        assertThat(result).isTrue();
    }

    @Test
    void existsByEmail_ShouldReturnFalse_WhenPassengerWithEmailDoesNotExist() {
        boolean result = passengerRepository.existsByEmail(PassengerTestUtil.NOT_EXISTING_EMAIL);

        assertThat(result).isFalse();
    }
}
