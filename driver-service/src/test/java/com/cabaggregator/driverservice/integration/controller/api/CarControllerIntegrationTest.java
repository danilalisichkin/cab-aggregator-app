package com.cabaggregator.driverservice.integration.controller.api;

import com.cabaggregator.driverservice.config.AbstractPostgresIntegrationTest;
import com.cabaggregator.driverservice.core.dto.car.CarAddingDto;
import com.cabaggregator.driverservice.core.dto.car.CarDto;
import com.cabaggregator.driverservice.core.dto.car.CarFullDto;
import com.cabaggregator.driverservice.core.dto.car.CarUpdatingDto;
import com.cabaggregator.driverservice.core.dto.car.details.CarDetailsSettingDto;
import com.cabaggregator.driverservice.entity.Car;
import com.cabaggregator.driverservice.repository.CarRepository;
import com.cabaggregator.driverservice.util.CarDetailsTestUtil;
import com.cabaggregator.driverservice.util.CarTestUtil;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.SneakyThrows;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.MediaType;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;
import java.util.Optional;

import static com.cabaggregator.driverservice.util.IntegrationTestUtil.CARS_BASE_URL;
import static com.cabaggregator.driverservice.util.IntegrationTestUtil.LOCAL_HOST;
import static org.assertj.core.api.Assertions.assertThat;
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
class CarControllerIntegrationTest extends AbstractPostgresIntegrationTest {
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private CarRepository carRepository;

    @Autowired
    private ObjectMapper objectMapper;

    @LocalServerPort
    private int port;

    private final String baseUrl = "%s:%s/%s".formatted(LOCAL_HOST, port, CARS_BASE_URL);

    @AfterEach
    void clearCars() {
        carRepository.deleteAll();
    }

    @Test
    @SneakyThrows
    @Sql(scripts = {
            "classpath:/sql.repository/import_cars.sql",
            "classpath:/sql.repository/import_car_details.sql"},
            executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    void getPageOfCars_ShouldReturnCars_WhenTheyExist() {
        int expectedPage = 0;
        int expectedPageSize = 10;
        int expectedTotalPages = 1;
        int expectedContentSize = 3;

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
    void getPageOfCars_ShouldReturnEmptyPage_WhenNoCarsExist() {
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
            "classpath:/sql.repository/import_cars.sql",
            "classpath:/sql.repository/import_car_details.sql"},
            executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    void getCar_ShouldReturnCar_WhenItExists() {
        String requestUrl = "%s/%s".formatted(baseUrl, CarTestUtil.ID.toString());
        CarDto carDto = CarTestUtil.buildCarDto();
        String expectedJson = objectMapper.writeValueAsString(carDto);

        mockMvc.perform(get(requestUrl))
                .andExpect(status().isOk())
                .andExpect(content().json(expectedJson));
    }

    @Test
    @SneakyThrows
    void getCar_ShouldReturnNotFoundStatus_WhenCarDoesNotExist() {
        String requestUrl = "%s/%s".formatted(baseUrl, CarTestUtil.NOT_EXISTING_ID.toString());

        mockMvc.perform(get(requestUrl))
                .andExpect(status().isNotFound());
    }

    @Test
    @SneakyThrows
    @Sql(scripts = {
            "classpath:/sql.repository/import_cars.sql",
            "classpath:/sql.repository/import_car_details.sql"},
            executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    void getFullCar_ShouldReturnFullCar_WhenItExists() {
        String requestUrl = "%s/%s/full".formatted(baseUrl, CarTestUtil.ID.toString());
        CarFullDto carFullDto = CarTestUtil.buildCarFullDto();
        String expectedJson = objectMapper.writeValueAsString(carFullDto);

        mockMvc.perform(get(requestUrl))
                .andExpect(status().isOk())
                .andExpect(content().json(expectedJson));
    }

    @Test
    @SneakyThrows
    void getFullCar_ShouldReturnNotFoundStatus_WhenCarDoesNotExist() {
        String requestUrl = "%s/%s/full".formatted(baseUrl, CarTestUtil.NOT_EXISTING_ID.toString());

        mockMvc.perform(get(requestUrl))
                .andExpect(status().isNotFound());
    }

    @Test
    @SneakyThrows
    void createCar_ShouldCreateCar_WhenItUnique() {
        CarAddingDto addingDto = CarTestUtil.buildCarAddingDto();
        String json = objectMapper.writeValueAsString(addingDto);
        CarDto createdCarDto = CarTestUtil.buildCarDto();
        String expectedJson = objectMapper.writeValueAsString(createdCarDto);
        int expectedCarCount = 1;

        mockMvc.perform(post(baseUrl)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andExpect(status().isCreated())
                .andExpect(content().json(expectedJson));
//                .andExpect(jsonPath("$.licensePlate").value(addingDto.licensePlate()))
//                .andExpect(jsonPath("$.make").value(addingDto.make()))
//                .andExpect(jsonPath("$.model").value(addingDto.model()))
//                .andExpect(jsonPath("$.color").value(addingDto.color()))
//                .andExpect(jsonPath("$.details.releaseDate").value(addingDto.details().releaseDate()))
//                .andExpect(jsonPath("$.details.seatCapacity").value(addingDto.details().seatCapacity()));

        List<Car> cars = carRepository.findAll();
        assertThat(cars).hasSize(expectedCarCount);
        assertThat(cars.getFirst().getLicensePlate()).isEqualTo(addingDto.licensePlate());
    }

    @Test
    @SneakyThrows
    @Sql(scripts = {
            "classpath:/sql.repository/import_cars.sql",
            "classpath:/sql.repository/import_car_details.sql"},
            executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    void createCar_ShouldReturnConflictStatus_WhenCarNotUnique() {
        CarAddingDto addingDto = CarTestUtil.buildCarAddingDto();
        String json = objectMapper.writeValueAsString(addingDto);
        int expectedCarCount = 3;

        mockMvc.perform(post(baseUrl)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andExpect(status().isConflict());

        List<Car> cars = carRepository.findAll();
        assertThat(cars).hasSize(expectedCarCount);
    }

    @Test
    @SneakyThrows
    @Sql(scripts = {
            "classpath:/sql.repository/import_cars.sql",
            "classpath:/sql.repository/import_car_details.sql"},
            executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    void updateCar_ShouldUpdateCar_WhenItExists() {
        String requestUrl = "%s/%s".formatted(baseUrl, CarTestUtil.ID.toString());
        CarUpdatingDto updatingDto = CarTestUtil.buildCarUpdatingDto();
        String json = objectMapper.writeValueAsString(updatingDto);
        CarDto updatedCarDto = CarTestUtil.buildUpdatedCarDto();
        String expectedJson = objectMapper.writeValueAsString(updatedCarDto);

        mockMvc.perform(put(requestUrl)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andExpect(status().isOk())
                .andExpect(content().json(expectedJson));

        Optional<Car> car = carRepository.findById(CarTestUtil.ID);
        assertThat(car).isPresent();
        assertThat(car.get().getLicensePlate()).isEqualTo(updatingDto.licensePlate());
        assertThat(car.get().getMake()).isEqualTo(updatingDto.make());
        assertThat(car.get().getModel()).isEqualTo(updatingDto.model());
        assertThat(car.get().getColor()).isEqualTo(updatingDto.color());
    }

    @Test
    @SneakyThrows
    void updateCar_ShouldReturnNotFoundStatus_WhenCarDoesNotExist() {
        String requestUrl = "%s/%s".formatted(baseUrl, CarTestUtil.NOT_EXISTING_ID.toString());
        CarUpdatingDto updatingDto = CarTestUtil.buildCarUpdatingDto();
        String json = objectMapper.writeValueAsString(updatingDto);

        mockMvc.perform(put(requestUrl)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andExpect(status().isNotFound());
    }

    @Test
    @SneakyThrows
    @Sql(scripts = {
            "classpath:/sql.repository/import_cars.sql",
            "classpath:/sql.repository/import_car_details.sql"},
            executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    void updateCar_ShouldReturnConflictStatus_WhenUpdatableDataIsNotUnique() {
        String requestUrl = "%s/%s".formatted(baseUrl, CarTestUtil.ID.toString());
        CarUpdatingDto updatingDto = CarTestUtil.buildConflictCarUpdatingDto();
        String json = objectMapper.writeValueAsString(updatingDto);

        mockMvc.perform(put(requestUrl)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andExpect(status().isConflict());
    }

    @Test
    @SneakyThrows
    @Sql(scripts = {
            "classpath:/sql.repository/import_cars.sql",
            "classpath:/sql.repository/import_car_details.sql"},
            executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    void updateCarDetails_ShouldUpdateCarDetails_WhenCarExist() {
        String requestUrl = "%s/%s/details".formatted(baseUrl, CarTestUtil.ID.toString());
        CarDetailsSettingDto settingDto = CarDetailsTestUtil.buildCarDetailsSettingDto();
        String json = objectMapper.writeValueAsString(settingDto);

        mockMvc.perform(put(requestUrl)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andExpect(status().isOk());

        Optional<Car> car = carRepository.findById(CarTestUtil.ID);
        assertThat(car).isPresent();
        assertThat(car.get().getCarDetails().getSeatCapacity()).isEqualTo(settingDto.seatCapacity());
        assertThat(car.get().getCarDetails().getReleaseDate()).isEqualTo(settingDto.releaseDate());
    }

    @Test
    @SneakyThrows
    void setCarDetails_ShouldReturnNotFoundStatus_WhenCarDoesNotExist() {
        String requestUrl = "%s/%s/car".formatted(baseUrl, CarTestUtil.NOT_EXISTING_ID.toString());
        CarDetailsSettingDto settingDto = CarDetailsTestUtil.buildCarDetailsSettingDto();
        String json = objectMapper.writeValueAsString(settingDto);

        mockMvc.perform(patch(requestUrl)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andExpect(status().isNotFound());
    }

    @Test
    @SneakyThrows
    @Sql(scripts = {
            "classpath:/sql.repository/import_cars.sql",
            "classpath:/sql.repository/import_car_details.sql"},
            executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    void deleteCar_ShouldDeleteCar_WhenHeExists() {
        String requestUrl = "%s/%s".formatted(baseUrl, CarTestUtil.ID.toString());
        int expectedCarCount = 2;

        mockMvc.perform(delete(requestUrl))
                .andExpect(status().isNoContent());

        List<Car> cars = carRepository.findAll();
        assertThat(cars).hasSize(expectedCarCount);
        assertThat(cars.getFirst().getId()).isNotEqualTo(CarTestUtil.ID);
    }

    @Test
    @SneakyThrows
    void deleteCar_ShouldReturnNotFoundStatus_WhenCarDoesNotExist() {
        String requestUrl = "%s/%s".formatted(baseUrl, CarTestUtil.NOT_EXISTING_ID.toString());

        mockMvc.perform(delete(requestUrl))
                .andExpect(status().isNotFound());
    }
}
