package com.cabaggregator.driverservice.integration.controller.api;

import com.cabaggregator.driverservice.config.AbstractPostgresIntegrationTest;
import com.cabaggregator.driverservice.core.dto.driver.DriverAddingDto;
import com.cabaggregator.driverservice.core.dto.driver.DriverDto;
import com.cabaggregator.driverservice.core.dto.driver.DriverUpdatingDto;
import com.cabaggregator.driverservice.entity.Driver;
import com.cabaggregator.driverservice.repository.CarRepository;
import com.cabaggregator.driverservice.repository.DriverRepository;
import com.cabaggregator.driverservice.util.CarTestUtil;
import com.cabaggregator.driverservice.util.DriverTestUtil;
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

import static com.cabaggregator.driverservice.util.IntegrationTestUtil.DRIVERS_BASE_URL;
import static com.cabaggregator.driverservice.util.IntegrationTestUtil.LOCAL_HOST;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.nullValue;
import static org.hamcrest.collection.IsCollectionWithSize.hasSize;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@Tag("integration")
@AutoConfigureMockMvc
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class DriverControllerIntegrationTest extends AbstractPostgresIntegrationTest {
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private DriverRepository driverRepository;

    @Autowired
    private CarRepository carRepository;

    @Autowired
    private ObjectMapper objectMapper;

    @LocalServerPort
    private int port;

    private String baseUrl;

    @PostConstruct
    void initBaseUrl() {
        this.baseUrl = "%s:%s/%s".formatted(LOCAL_HOST, port, DRIVERS_BASE_URL);
    }

    @AfterEach
    void clearDriversAndCars() {
        driverRepository.deleteAll();
        carRepository.deleteAll();
    }

    @Test
    @SneakyThrows
    @Sql(scripts = {
            "classpath:/postgresql/import_cars.sql",
            "classpath:/postgresql/import_car_details.sql",
            "classpath:/postgresql/import_drivers.sql"},
            executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    void getPageOfDrivers_ShouldReturnDrivers_WhenTheyExist() {
        int expectedPage = 0;
        int expectedPageSize = 10;
        int expectedTotalPages = 1;
        int expectedContentSize = 2;

        mockMvc.perform(get(baseUrl)
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
    void getPageOfDrivers_ShouldReturnEmptyPage_WhenNoDriversExist() {
        int expectedPage = 0;
        int expectedPageSize = 10;
        int expectedTotalPages = 0;
        int expectedContentSize = 0;

        mockMvc.perform(get(baseUrl)
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
            "classpath:/postgresql/import_cars.sql",
            "classpath:/postgresql/import_car_details.sql",
            "classpath:/postgresql/import_drivers.sql"},
            executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    void getDriver_ShouldReturnDriver_WhenHeExists() {
        String requestUrl = "%s/%s".formatted(baseUrl, DriverTestUtil.ID.toString());
        DriverDto driverDto = DriverTestUtil.buildDriverDto();
        String expectedJson = objectMapper.writeValueAsString(driverDto);

        mockMvc.perform(get(requestUrl))
                .andExpect(status().isOk())
                .andExpect(content().json(expectedJson));
    }

    @Test
    @SneakyThrows
    void getDriver_ShouldReturnNotFoundStatus_WhenDriverDoesNotExist() {
        String requestUrl = "%s/%s".formatted(baseUrl, DriverTestUtil.NOT_EXISTING_ID.toString());

        mockMvc.perform(get(requestUrl))
                .andExpect(status().isNotFound());
    }

    @Test
    @SneakyThrows
    void createDriver_ShouldCreateDriver_WhenHeUnique() {
        DriverAddingDto addingDto = DriverTestUtil.buildDriverAddingDto();
        String json = objectMapper.writeValueAsString(addingDto);

        mockMvc.perform(post(baseUrl)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(addingDto.id().toString()))
                .andExpect(jsonPath("$.phoneNumber").value(addingDto.phoneNumber()))
                .andExpect(jsonPath("$.email").value(addingDto.email()))
                .andExpect(jsonPath("$.firstName").value(addingDto.firstName()))
                .andExpect(jsonPath("$.lastName").value(addingDto.lastName()))
                .andExpect(jsonPath("$.carId").value(nullValue()));

        List<Driver> drivers = driverRepository.findAll();
        assertThat(drivers).anyMatch(driver -> driver.getId().equals(addingDto.id()));
    }

    @Test
    @SneakyThrows
    @Sql(scripts = {
            "classpath:/postgresql/import_cars.sql",
            "classpath:/postgresql/import_car_details.sql",
            "classpath:/postgresql/import_drivers.sql"},
            executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    void createDriver_ShouldReturnConflictStatus_WhenDriverNotUnique() {
        DriverAddingDto addingDto = DriverTestUtil.buildDriverAddingDto();
        String json = objectMapper.writeValueAsString(addingDto);
        int expectedDriverCount = 2;

        mockMvc.perform(post(baseUrl)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andExpect(status().isConflict());

        List<Driver> drivers = driverRepository.findAll();
        assertThat(drivers).hasSize(expectedDriverCount);
    }

    @Test
    @SneakyThrows
    @Sql(scripts = {
            "classpath:/postgresql/import_cars.sql",
            "classpath:/postgresql/import_car_details.sql",
            "classpath:/postgresql/import_drivers.sql"},
            executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    void updateDriver_ShouldUpdateDriver_WhenHeExists() {
        String requestUrl = "%s/%s".formatted(baseUrl, DriverTestUtil.ID.toString());
        DriverUpdatingDto updatingDto = DriverTestUtil.buildDriverUpdatingDto();
        String json = objectMapper.writeValueAsString(updatingDto);
        DriverDto updatedPassengerDto = DriverTestUtil.buildUpdatedDriverDto();
        String expectedJson = objectMapper.writeValueAsString(updatedPassengerDto);

        mockMvc.perform(put(requestUrl)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andExpect(status().isOk())
                .andExpect(content().json(expectedJson));

        Optional<Driver> driver = driverRepository.findById(DriverTestUtil.ID);
        assertThat(driver).isPresent();
        assertThat(driver.get().getFirstName()).isEqualTo(updatingDto.firstName());
        assertThat(driver.get().getLastName()).isEqualTo(updatingDto.lastName());
        assertThat(driver.get().getPhoneNumber()).isEqualTo(updatingDto.phoneNumber());
    }

    @Test
    @SneakyThrows
    void updateDriver_ShouldReturnNotFoundStatus_WhenDriverDoesNotExist() {
        String requestUrl = "%s/%s".formatted(baseUrl, DriverTestUtil.NOT_EXISTING_ID.toString());
        DriverUpdatingDto updatingDto = DriverTestUtil.buildDriverUpdatingDto();
        String json = objectMapper.writeValueAsString(updatingDto);

        mockMvc.perform(put(requestUrl)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andExpect(status().isNotFound());
    }

    @Test
    @SneakyThrows
    @Sql(scripts = {
            "classpath:/postgresql/import_cars.sql",
            "classpath:/postgresql/import_car_details.sql",
            "classpath:/postgresql/import_drivers.sql"},
            executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    void updateDriver_ShouldReturnConflictStatus_WhenUpdatableDataIsNotUnique() {
        String requestUrl = "%s/%s".formatted(baseUrl, DriverTestUtil.ID.toString());
        DriverUpdatingDto updatingDto = DriverTestUtil.buildConflictDriverUpdatingDto();
        String json = objectMapper.writeValueAsString(updatingDto);

        mockMvc.perform(put(requestUrl)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andExpect(status().isConflict());
    }

    @Test
    @SneakyThrows
    @Sql(scripts = {
            "classpath:/postgresql/import_cars.sql",
            "classpath:/postgresql/import_car_details.sql",
            "classpath:/postgresql/import_drivers.sql"},
            executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    void setDriverCar_ShouldSetDriverCar_WhenDriverAndCarExist() {
        String requestUrl = "%s/%s/car".formatted(baseUrl, DriverTestUtil.ID.toString());
        String json = objectMapper.writeValueAsString(CarTestUtil.FREE_CAR_ID);

        mockMvc.perform(patch(requestUrl)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(DriverTestUtil.ID.toString()))
                .andExpect(jsonPath("$.phoneNumber").value(DriverTestUtil.PHONE_NUMBER))
                .andExpect(jsonPath("$.email").value(DriverTestUtil.EMAIL))
                .andExpect(jsonPath("$.firstName").value(DriverTestUtil.FIRST_NAME))
                .andExpect(jsonPath("$.lastName").value(DriverTestUtil.LAST_NAME))
                .andExpect(jsonPath("$.carId").value(CarTestUtil.FREE_CAR_ID));

        Optional<Driver> driver = driverRepository.findById(DriverTestUtil.ID);
        assertThat(driver).isPresent();
        assertThat(driver.get().getCar().getId()).isEqualTo(CarTestUtil.FREE_CAR_ID);
    }

    @Test
    @SneakyThrows
    void setDriverCar_ShouldReturnNotFoundStatus_WhenDriverDoesNotExist() {
        String requestUrl = "%s/%s/car".formatted(baseUrl, DriverTestUtil.NOT_EXISTING_ID.toString());
        String json = objectMapper.writeValueAsString(CarTestUtil.ID);

        mockMvc.perform(patch(requestUrl)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andExpect(status().isNotFound());
    }

    @Test
    @SneakyThrows
    @Sql(scripts = {
            "classpath:/postgresql/import_cars.sql",
            "classpath:/postgresql/import_car_details.sql",
            "classpath:/postgresql/import_drivers.sql"},
            executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    void setDriverCar_ShouldReturnNotFoundStatus_WhenCarDoesNotExist() {
        String requestUrl = "%s/%s/car".formatted(baseUrl, DriverTestUtil.ID.toString());
        String json = objectMapper.writeValueAsString(CarTestUtil.NOT_EXISTING_ID);

        mockMvc.perform(patch(requestUrl)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andExpect(status().isNotFound());
    }

    @Test
    @SneakyThrows
    @Sql(scripts = {
            "classpath:/postgresql/import_cars.sql",
            "classpath:/postgresql/import_car_details.sql",
            "classpath:/postgresql/import_drivers.sql"},
            executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    void setDriverCar_ShouldReturnConflictStatus_WhenCarRelatedToAnotherDriver() {
        String requestUrl = "%s/%s/car".formatted(baseUrl, DriverTestUtil.ID.toString());
        String json = objectMapper.writeValueAsString(CarTestUtil.OTHER_CAR_ID);

        mockMvc.perform(patch(requestUrl)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andExpect(status().isConflict());
    }

    @Test
    @SneakyThrows
    @Sql(scripts = {
            "classpath:/postgresql/import_cars.sql",
            "classpath:/postgresql/import_car_details.sql",
            "classpath:/postgresql/import_drivers.sql"},
            executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    void deleteDriver_ShouldDeleteDriver_WhenHeExists() {
        String requestUrl = "%s/%s".formatted(baseUrl, DriverTestUtil.ID.toString());
        int expectedDriverCount = 1;

        mockMvc.perform(delete(requestUrl))
                .andExpect(status().isNoContent());

        List<Driver> drivers = driverRepository.findAll();
        assertThat(drivers).hasSize(expectedDriverCount);
        assertThat(drivers.getFirst().getId()).isNotEqualTo(DriverTestUtil.ID);
    }

    @Test
    @SneakyThrows
    void deleteDriver_ShouldReturnNotFoundStatus_WhenDriverDoesNotExist() {
        String requestUrl = "%s/%s".formatted(baseUrl, DriverTestUtil.NOT_EXISTING_ID.toString());

        mockMvc.perform(delete(requestUrl))
                .andExpect(status().isNotFound());
    }
}
