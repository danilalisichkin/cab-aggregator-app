package com.cabaggregator.promocodeservice.integration.controller.api;

import com.cabaggregator.promocodeservice.config.AbstractPostgresIntegrationTest;
import com.cabaggregator.promocodeservice.core.dto.promo.code.PromoCodeAddingDto;
import com.cabaggregator.promocodeservice.core.dto.promo.code.PromoCodeDto;
import com.cabaggregator.promocodeservice.core.dto.promo.code.PromoCodeUpdatingDto;
import com.cabaggregator.promocodeservice.entity.PromoCode;
import com.cabaggregator.promocodeservice.repository.PromoCodeRepository;
import com.cabaggregator.promocodeservice.util.PromoCodeTestUtil;
import com.fasterxml.jackson.databind.ObjectMapper;
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

import static com.cabaggregator.promocodeservice.util.IntegrationTestUtil.LOCAL_HOST;
import static com.cabaggregator.promocodeservice.util.IntegrationTestUtil.PROMO_CODES_BASE_URL;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.collection.IsCollectionWithSize.hasSize;
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
class PromoCodeControllerIntegrationTest extends AbstractPostgresIntegrationTest {
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private PromoCodeRepository promoCodeRepository;

    @Autowired
    private ObjectMapper objectMapper;

    @LocalServerPort
    private int port;

    private final String baseUrl = "%s:%s/%s".formatted(LOCAL_HOST, port, PROMO_CODES_BASE_URL);

    @AfterEach
    void clearPromoCodes() {
        promoCodeRepository.deleteAll();
    }

    @Test
    @SneakyThrows
    @Sql(scripts = {
            "classpath:/sql.repository/import_promo_codes.sql"},
            executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    void getPageOfPromoCodes_ShouldReturnPromoCodes_WhenTheyExist() {
        int expectedPage = 0;
        int expectedPageSize = 10;
        int expectedTotalPages = 1;
        int expectedContentSize = 2;

        mockMvc.perform(get(baseUrl)
                        .param("offset", "0")
                        .param("limit", "10")
                        .param("sortBy", "VALUE")
                        .param("sortOrder", "ASC"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.page").value(expectedPage))
                .andExpect(jsonPath("$.pageSize").value(expectedPageSize))
                .andExpect(jsonPath("$.totalPages").value(expectedTotalPages))
                .andExpect(jsonPath("$.content", hasSize(expectedContentSize)));
    }

    @Test
    @SneakyThrows
    void getPageOfPromoCodes_ShouldReturnEmptyPage_WhenNoPromoCodesExist() {
        int expectedPage = 0;
        int expectedPageSize = 10;
        int expectedTotalPages = 0;
        int expectedContentSize = 0;

        mockMvc.perform(get(baseUrl)
                        .param("offset", "0")
                        .param("limit", "10")
                        .param("sortBy", "VALUE")
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
            "classpath:/sql.repository/import_promo_codes.sql"},
            executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    void getPromoCode_ShouldReturnPromoCode_WhenItExists() {
        String requestUrl = "%s/%s".formatted(baseUrl, PromoCodeTestUtil.VALUE);
        PromoCodeDto promoCodeDto = PromoCodeTestUtil.buildPromoCodeDto();
        String expectedJson = objectMapper.writeValueAsString(promoCodeDto);

        mockMvc.perform(get(requestUrl))
                .andExpect(status().isOk())
                .andExpect(content().json(expectedJson));
    }

    @Test
    @SneakyThrows
    void getPromoCode_ShouldReturnNotFoundStatus_WhenPromoCodeDoesNotExist() {
        String requestUrl = "%s/%s".formatted(baseUrl, PromoCodeTestUtil.NOT_EXISTING_CODE);

        mockMvc.perform(get(requestUrl))
                .andExpect(status().isNotFound());
    }

    @Test
    @SneakyThrows
    void createPromoCode_ShouldCreatePromoCode_WhenItUnique() {
        PromoCodeAddingDto addingDto = PromoCodeTestUtil.buildPromoCodeAddingDto();
        String json = objectMapper.writeValueAsString(addingDto);
        PromoCodeDto promoCodeDto = PromoCodeTestUtil.buildPromoCodeDto();
        String expectedJson = objectMapper.writeValueAsString(promoCodeDto);
        int expectedPromoCodeCount = 1;

        mockMvc.perform(post(baseUrl)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andExpect(status().isCreated())
                .andExpect(content().json(expectedJson));

        List<PromoCode> promoCodes = promoCodeRepository.findAll();
        assertThat(promoCodes).hasSize(expectedPromoCodeCount);
        assertThat(promoCodes.getFirst().getValue()).isEqualTo(addingDto.value());
    }

    @Test
    @SneakyThrows
    @Sql(scripts = {
            "classpath:/sql.repository/import_promo_codes.sql"},
            executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    void createPromoCode_ShouldReturnConflictStatus_WhenPromoCodeNotUnique() {
        PromoCodeAddingDto addingDto = PromoCodeTestUtil.buildPromoCodeAddingDto();
        String json = objectMapper.writeValueAsString(addingDto);
        int expectedPromoCodeCount = 2;

        mockMvc.perform(post(baseUrl)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andExpect(status().isConflict());

        List<PromoCode> promoCodes = promoCodeRepository.findAll();
        assertThat(promoCodes).hasSize(expectedPromoCodeCount);
    }

    @Test
    @SneakyThrows
    void createPromoCode_ShouldReturnBadRequestStatus_WhenPromoCodeEndDateInPast() {
        PromoCodeAddingDto addingDto = PromoCodeTestUtil.buildPromoCodeAddingDtoWithIncorrectEndDate();
        String json = objectMapper.writeValueAsString(addingDto);

        mockMvc.perform(post(baseUrl)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andExpect(status().isBadRequest());

        List<PromoCode> promoCodes = promoCodeRepository.findAll();
        assertThat(promoCodes).isEmpty();
    }

    @Test
    @SneakyThrows
    @Sql(scripts = {
            "classpath:/sql.repository/import_promo_codes.sql"},
            executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    void updatePromoCode_ShouldUpdatePromoCode_WhenItExists() {
        String requestUrl = "%s/%s".formatted(baseUrl, PromoCodeTestUtil.VALUE);
        PromoCodeUpdatingDto updatingDto = PromoCodeTestUtil.buildPromoCodeUpdatingDto();
        String json = objectMapper.writeValueAsString(updatingDto);
        PromoCodeDto updatedPromoCodeDto = PromoCodeTestUtil.buildUpdatedPromoCodeDto();
        String expectedJson = objectMapper.writeValueAsString(updatedPromoCodeDto);

        mockMvc.perform(put(requestUrl)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andExpect(status().isOk())
                .andExpect(content().json(expectedJson));

        Optional<PromoCode> promoCode = promoCodeRepository.findById(PromoCodeTestUtil.VALUE);
        assertThat(promoCode).isPresent();
        assertThat(promoCode.get().getEndDate()).isEqualTo(updatingDto.endDate());
        assertThat(promoCode.get().getDiscountPercentage()).isEqualTo(updatingDto.discountPercentage());
        assertThat(promoCode.get().getLimit()).isEqualTo(updatingDto.limit());
    }

    @Test
    @SneakyThrows
    void updatePromoCode_ShouldReturnBadRequestStatus_WhenPromoCodeEndDateInPast() {
        PromoCodeUpdatingDto updatingDto = PromoCodeTestUtil.buildPromoCodeUpdatingDtoWithIncorrectEndDate();
        String json = objectMapper.writeValueAsString(updatingDto);

        mockMvc.perform(post(baseUrl)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andExpect(status().isBadRequest());

        List<PromoCode> promoCodes = promoCodeRepository.findAll();
        assertThat(promoCodes).isEmpty();
    }
}
