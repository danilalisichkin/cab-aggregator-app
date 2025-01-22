package com.cabaggregator.passengerservice.integration.controller.api;

import com.cabaggregator.passengerservice.config.AbstractPostgresIntegrationTest;
import com.cabaggregator.passengerservice.core.dto.passenger.PassengerAddingDto;
import com.cabaggregator.passengerservice.core.dto.passenger.PassengerDto;
import com.cabaggregator.passengerservice.core.dto.passenger.PassengerUpdatingDto;
import com.cabaggregator.passengerservice.entity.Passenger;
import com.cabaggregator.passengerservice.repository.PassengerRepository;
import com.cabaggregator.passengerservice.util.AuthTestUtil;
import com.cabaggregator.passengerservice.util.PassengerTestUtil;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.annotation.PostConstruct;
import lombok.SneakyThrows;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.MediaType;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static com.cabaggregator.passengerservice.util.IntegrationTestUtil.LOCAL_HOST;
import static com.cabaggregator.passengerservice.util.IntegrationTestUtil.PASSENGERS_BASE_URL;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.collection.IsCollectionWithSize.hasSize;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@Tag("integration")
@AutoConfigureMockMvc
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class PassengerControllerIntegrationTest extends AbstractPostgresIntegrationTest {
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private PassengerRepository passengerRepository;

    @Autowired
    private AuthTestUtil authTestUtil;

    @LocalServerPort
    private int port;

    private String baseUrl;

    @PostConstruct
    void initBaseUrl() {
        this.baseUrl = "%s:%s/%s".formatted(LOCAL_HOST, port, PASSENGERS_BASE_URL);
    }

    @AfterEach
    void clearPassengers() {
        passengerRepository.deleteAll();
    }

    @Test
    @SneakyThrows
    @Sql(scripts = {
            "classpath:/postgresql/import_passengers.sql"},
            executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    void getPageOfPassengers_ShouldReturnPassengers_WhenTheyExist() {
        int expectedPage = 0;
        int expectedPageSize = 10;
        int expectedTotalPages = 1;
        int expectedContentSize = 2;

        mockMvc.perform(get(baseUrl)
                        .header("Authorization", authTestUtil.getAdminBearerToken())
                        .param("offset", "0")
                        .param("limit", "10")
                        .param("sortBy", "ID")
                        .param("sortOrder", "ASC"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.page").value(expectedPage))
                .andExpect(jsonPath("$.pageSize").value(expectedPageSize))
                .andExpect(jsonPath("$.totalPages").value(expectedTotalPages))
                .andExpect(jsonPath("$.content", hasSize(expectedContentSize)));
    }

    @Test
    @SneakyThrows
    void getPageOfPassengers_ShouldReturnEmptyPage_WhenNoPassengersExist() {
        int expectedPage = 0;
        int expectedPageSize = 10;
        int expectedTotalPages = 0;
        int expectedContentSize = 0;

        mockMvc.perform(get(baseUrl)
                        .header("Authorization", authTestUtil.getAdminBearerToken())
                        .param("offset", "0")
                        .param("limit", "10")
                        .param("sortBy", "ID")
                        .param("sortOrder", "ASC"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.page").value(expectedPage))
                .andExpect(jsonPath("$.pageSize").value(expectedPageSize))
                .andExpect(jsonPath("$.totalPages").value(expectedTotalPages))
                .andExpect(jsonPath("$.content", hasSize(expectedContentSize)));
    }

    @Test
    @SneakyThrows
    @Sql(scripts = {
            "classpath:/postgresql/import_passengers.sql"},
            executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    void getPassenger_ShouldReturnPassenger_WhenHeExists() {
        String requestUrl = "%s/%s".formatted(baseUrl, PassengerTestUtil.ID.toString());
        PassengerDto passengerDto = PassengerTestUtil.buildPassengerDto();
        String expectedJson = new ObjectMapper().writeValueAsString(passengerDto);

        mockMvc.perform(get(requestUrl)
                        .header("Authorization", authTestUtil.getPassengerBearerToken()))
                .andExpect(status().isOk())
                .andExpect(content().json(expectedJson));
    }

    @Test
    @SneakyThrows
    @Sql(scripts = {
            "classpath:/postgresql/import_passengers.sql"},
            executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    void getPassenger_ShouldReturnForbiddenStatus_WhenUserIsNotRequestedPassenger() {
        String requestUrl = "%s/%s".formatted(baseUrl, PassengerTestUtil.OTHER_ID.toString());

        mockMvc.perform(get(requestUrl)
                        .header("Authorization", authTestUtil.getPassengerBearerToken()))
                .andExpect(status().isForbidden());
    }

    @Test
    @SneakyThrows
    void getPassenger_ShouldReturnNotFoundStatus_WhenUserIsAdminAndPassengerDoesNotExist() {
        String requestUrl = "%s/%s".formatted(baseUrl, PassengerTestUtil.NOT_EXISTING_ID.toString());

        mockMvc.perform(get(requestUrl)
                        .header("Authorization", authTestUtil.getAdminBearerToken()))
                .andExpect(status().isNotFound());
    }

    @Test
    @SneakyThrows
    void createPassenger_ShouldCreatePassenger_WhenHeUnique() {
        PassengerAddingDto addingDto = PassengerTestUtil.buildPassengerAddingDto();
        String json = new ObjectMapper().writeValueAsString(addingDto);
        PassengerDto passengerDto = PassengerTestUtil.buildPassengerDto();
        String expectedJson = new ObjectMapper().writeValueAsString(passengerDto);
        int expectedPassengerCount = 1;

        mockMvc.perform(post(baseUrl)
                        .header("Authorization", authTestUtil.getAdminBearerToken())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andExpect(status().isCreated())
                .andExpect(content().json(expectedJson));

        List<Passenger> passengers = passengerRepository.findAll();
        assertThat(passengers).hasSize(expectedPassengerCount);
        assertThat(passengers.getFirst().getId()).isEqualTo(addingDto.id());
    }

    @Test
    @SneakyThrows
    @Sql(scripts = {
            "classpath:/postgresql/import_passengers.sql"},
            executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    void createPassenger_ShouldReturnConflictStatus_WhenPassengerNotUnique() {
        PassengerAddingDto addingDto = PassengerTestUtil.buildPassengerAddingDto();
        String json = new ObjectMapper().writeValueAsString(addingDto);
        int expectedPassengerCount = 2;

        mockMvc.perform(post(baseUrl)
                        .header("Authorization", authTestUtil.getAdminBearerToken())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andExpect(status().isConflict());

        List<Passenger> passengers = passengerRepository.findAll();
        assertThat(passengers).hasSize(expectedPassengerCount);
    }

    @Test
    @SneakyThrows
    @Sql(scripts = {
            "classpath:/postgresql/import_passengers.sql"},
            executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    void updatePassenger_ShouldUpdatePassenger_WhenHeExists() {
        String requestUrl = "%s/%s".formatted(baseUrl, PassengerTestUtil.ID.toString());
        PassengerUpdatingDto updatingDto = PassengerTestUtil.buildPassengerUpdatingDto();
        String json = new ObjectMapper().writeValueAsString(updatingDto);
        PassengerDto updatedPassengerDto = PassengerTestUtil.buildUpdatedPassengerDto();
        String expectedJson = new ObjectMapper().writeValueAsString(updatedPassengerDto);

        mockMvc.perform(put(requestUrl)
                        .header("Authorization", authTestUtil.getPassengerBearerToken())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andExpect(status().isOk())
                .andExpect(content().json(expectedJson));

        Optional<Passenger> passenger = passengerRepository.findById(PassengerTestUtil.ID);
        assertThat(passenger).isPresent();
        assertThat(passenger.get().getFirstName()).isEqualTo(updatingDto.firstName());
        assertThat(passenger.get().getLastName()).isEqualTo(updatingDto.lastName());
        assertThat(passenger.get().getPhoneNumber()).isEqualTo(updatingDto.phoneNumber());
    }

    @Test
    @SneakyThrows
    @Sql(scripts = {
            "classpath:/postgresql/import_passengers.sql"},
            executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    void updatePassenger_ShouldReturnForbiddenStatus_WhenUserIsNotRequestedPassenger() {
        UUID passengerId = PassengerTestUtil.OTHER_ID;
        String requestUrl = "%s/%s".formatted(baseUrl, passengerId.toString());
        PassengerUpdatingDto updatingDto = PassengerTestUtil.buildPassengerUpdatingDto();
        String json = new ObjectMapper().writeValueAsString(updatingDto);
        Optional<Passenger> oldPassenger = passengerRepository.findById(passengerId);

        mockMvc.perform(put(requestUrl)
                        .header("Authorization", authTestUtil.getPassengerBearerToken())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andExpect(status().isForbidden());

        Optional<Passenger> newPassenger = passengerRepository.findById(passengerId);
        assertThat(newPassenger)
                .isPresent()
                .isEqualTo(oldPassenger);
    }

    @Test
    @SneakyThrows
    void updatePassenger_ShouldReturnNotfoundStatus_WhenUserIsAdminAndPassengerDoesNotExist() {
        String requestUrl = "%s/%s".formatted(baseUrl, PassengerTestUtil.NOT_EXISTING_ID.toString());
        PassengerUpdatingDto updatingDto = PassengerTestUtil.buildPassengerUpdatingDto();
        String json = new ObjectMapper().writeValueAsString(updatingDto);

        mockMvc.perform(put(requestUrl)
                        .header("Authorization", authTestUtil.getAdminBearerToken())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andExpect(status().isNotFound());
    }

    @Test
    @SneakyThrows
    @Sql(scripts = {
            "classpath:/postgresql/import_passengers.sql"},
            executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    void updatePassenger_ShouldReturnConflictStatus_WhenUpdatableDataIsNotUnique() {
        String requestUrl = "%s/%s".formatted(baseUrl, PassengerTestUtil.ID.toString());
        PassengerUpdatingDto updatingDto = PassengerTestUtil.buildConflictPassengerUpdatingDto();
        String json = new ObjectMapper().writeValueAsString(updatingDto);

        mockMvc.perform(put(requestUrl)
                        .header("Authorization", authTestUtil.getPassengerBearerToken())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andExpect(status().isConflict());
    }

    @Test
    @SneakyThrows
    @Sql(scripts = {
            "classpath:/postgresql/import_passengers.sql"},
            executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    void deletePassenger_ShouldDeletePassenger_WhenHeExists() {
        String requestUrl = "%s/%s".formatted(baseUrl, PassengerTestUtil.ID.toString());
        int expectedPassengerCount = 1;

        mockMvc.perform(delete(requestUrl)
                        .header("Authorization", authTestUtil.getAdminBearerToken()))
                .andExpect(status().isNoContent());

        List<Passenger> passengers = passengerRepository.findAll();
        assertThat(passengers).hasSize(expectedPassengerCount);
        assertThat(passengers.getFirst().getId()).isNotEqualTo(PassengerTestUtil.ID);
    }

    @Test
    @SneakyThrows
    void deletePassenger_ShouldReturnNotFoundStatus_WhenPassengerDoesNotExist() {
        String requestUrl = "%s/%s".formatted(baseUrl, PassengerTestUtil.NOT_EXISTING_ID.toString());

        mockMvc.perform(delete(requestUrl)
                        .header("Authorization", authTestUtil.getAdminBearerToken()))
                .andExpect(status().isNotFound());
    }
}
