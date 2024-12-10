package com.cabaggregator.pricecalculationservice.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Map;

@FeignClient(
        name = "weatherApiClient",
        url = "https://api.weatherapi.com/v1",
        configuration = WeatherApiClientInterceptor.class)
public interface WeatherApiClient {

    @GetMapping("/current.json")
    Map<String, Object> getCurrentWeather(@RequestParam("q") String coordinates);
}
