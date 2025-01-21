package com.cabaggregator.promocodeservice.integration.controller.api;

import com.cabaggregator.promocodeservice.config.AbstractPostgresIntegrationTest;
import com.cabaggregator.promocodeservice.core.dto.promo.stat.PromoStatAddingDto;
import com.cabaggregator.promocodeservice.core.dto.promo.stat.PromoStatDto;
import com.cabaggregator.promocodeservice.entity.PromoCode;
import com.cabaggregator.promocodeservice.entity.PromoStat;
import com.cabaggregator.promocodeservice.repository.PromoCodeRepository;
import com.cabaggregator.promocodeservice.repository.PromoStatRepository;
import com.cabaggregator.promocodeservice.util.PromoCodeTestUtil;
import com.cabaggregator.promocodeservice.util.PromoStatTestUtil;
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

import static com.cabaggregator.promocodeservice.util.IntegrationTestUtil.LOCAL_HOST;
import static com.cabaggregator.promocodeservice.util.IntegrationTestUtil.PROMO_STATS_BASE_URL;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.collection.IsCollectionWithSize.hasSize;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@Tag("integration")
@AutoConfigureMockMvc
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class PromoStatControllerIntegrationTest extends AbstractPostgresIntegrationTest {
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private PromoStatRepository promoStatRepository;

    @Autowired
    private PromoCodeRepository promoCodeRepository;

    @Autowired
    private ObjectMapper objectMapper;

    @LocalServerPort
    private int port;

    private String baseUrl;

    @PostConstruct
    void initBaseUrl() {
        this.baseUrl = "%s:%s/%s".formatted(LOCAL_HOST, port, PROMO_STATS_BASE_URL);
    }

    @AfterEach
    void clearPromoCodesAndStats() {
        promoStatRepository.deleteAll();
        promoCodeRepository.deleteAll();
    }

    @Test
    @SneakyThrows
    @Sql(scripts = {
            "classpath:/postgresql/import_promo_codes.sql",
            "classpath:/postgresql/import_promo_stats.sql"},
            executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    void getPageOfPromoStats_ShouldReturnPromoStats_WhenTheyExist() {
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
    void getPageOfPromoStats_ShouldReturnEmptyPage_WhenNoPromoStatsExist() {
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
            "classpath:/postgresql/import_promo_codes.sql",
            "classpath:/postgresql/import_promo_stats.sql"},
            executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    void getPromoStat_ShouldReturnPromoStat_WhenItExists() {
        String requestUrl = "%s/%s".formatted(baseUrl, PromoStatTestUtil.ID);
        PromoStatDto promoStatDto = PromoStatTestUtil.buildPromoStatDto();
        String expectedJson = objectMapper.writeValueAsString(promoStatDto);

        mockMvc.perform(get(requestUrl))
                .andExpect(status().isOk())
                .andExpect(content().json(expectedJson));
    }

    @Test
    @SneakyThrows
    void getPromoStat_ShouldReturnNotFoundStatus_WhenPromoStatDoesNotExist() {
        String requestUrl = "%s/%s".formatted(baseUrl, PromoStatTestUtil.NOT_EXISTING_ID);

        mockMvc.perform(get(requestUrl))
                .andExpect(status().isNotFound());
    }

    @Test
    @SneakyThrows
    @Sql(scripts = {
            "classpath:/postgresql/import_promo_codes.sql"},
            executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    void createPromoStat_ShouldCreatePromoStat_WhenPromoCodeNotAppliedYet() {
        PromoStatAddingDto addingDto = PromoStatTestUtil.buildPromoStatAddingDto();
        String json = objectMapper.writeValueAsString(addingDto);
        PromoStatDto promoStatDto = PromoStatTestUtil.buildPromoStatDto();
        String expectedJson = objectMapper.writeValueAsString(promoStatDto);
        int expectedPromoStatCount = 1;
        long expectedNewPromoCodeLimit = PromoCodeTestUtil.LIMIT - 1;

        mockMvc.perform(post(baseUrl)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andExpect(status().isCreated())
                .andExpect(content().json(expectedJson));

        List<PromoStat> promoStats = promoStatRepository.findAll();
        assertThat(promoStats).hasSize(expectedPromoStatCount);
        assertThat(promoStats.getFirst().getPromoCode().getValue()).isEqualTo(addingDto.promoCode());
        assertThat(promoStats.getFirst().getUserId()).isEqualTo(addingDto.userId());

        Optional<PromoCode> promoCode = promoCodeRepository.findById(addingDto.promoCode());
        assertThat(promoCode).isPresent();
        assertThat(promoCode.get().getLimit()).isEqualTo(expectedNewPromoCodeLimit);
    }

    @Test
    @SneakyThrows
    @Sql(scripts = {
            "classpath:/postgresql/import_promo_codes.sql",
            "classpath:/postgresql/import_promo_stats.sql"},
            executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    void createPromoStat_ShouldReturnBadRequestStatus_WhenPromoCodeAlreadyApplied() {
        PromoStatAddingDto addingDto = PromoStatTestUtil.buildPromoStatAddingDto();
        String json = objectMapper.writeValueAsString(addingDto);
        int expectedPromoStatCount = 2;

        mockMvc.perform(post(baseUrl)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andExpect(status().isBadRequest());

        List<PromoStat> promoStats = promoStatRepository.findAll();
        assertThat(promoStats).hasSize(expectedPromoStatCount);
    }

    @Test
    @SneakyThrows
    @Sql(scripts = {
            "classpath:/postgresql/import_promo_codes.sql"},
            executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    void createPromoStat_ShouldReturnNotFoundStatus_WhenPromoCodeDoesNotExist() {
        PromoStatAddingDto addingDto = PromoStatTestUtil.buildPromoStatAddingDto(PromoCodeTestUtil.NOT_EXISTING_CODE);
        String json = objectMapper.writeValueAsString(addingDto);

        mockMvc.perform(post(baseUrl)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andExpect(status().isNotFound());

        List<PromoStat> promoStats = promoStatRepository.findAll();
        assertThat(promoStats).isEmpty();
    }

    @Test
    @SneakyThrows
    @Sql(scripts = {
            "classpath:/postgresql/import_expired_promo_codes.sql"},
            executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    void createPromoStat_ShouldReturnBadRequestStatus_WhenPromoCodeExpired() {
        PromoStatAddingDto addingDto = PromoStatTestUtil.buildPromoStatAddingDto(PromoCodeTestUtil.EXPIRED_CODE);
        String json = objectMapper.writeValueAsString(addingDto);

        mockMvc.perform(post(baseUrl)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andExpect(status().isBadRequest());

        List<PromoStat> promoStats = promoStatRepository.findAll();
        assertThat(promoStats).isEmpty();
    }

    @Test
    @SneakyThrows
    @Sql(scripts = {
            "classpath:/postgresql/import_reached_limit_promo_codes.sql"},
            executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    void createPromoStat_ShouldReturnBadRequestStatus_WhenPromoCodeReachedLimit() {
        PromoStatAddingDto addingDto = PromoStatTestUtil.buildPromoStatAddingDto(PromoCodeTestUtil.REACHED_LIMIT_CODE);
        String json = objectMapper.writeValueAsString(addingDto);

        mockMvc.perform(post(baseUrl)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andExpect(status().isBadRequest());

        List<PromoStat> promoStats = promoStatRepository.findAll();
        assertThat(promoStats).isEmpty();
    }
}
