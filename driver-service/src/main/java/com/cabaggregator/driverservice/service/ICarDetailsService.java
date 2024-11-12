package com.cabaggregator.driverservice.service;

import com.cabaggregator.driverservice.core.dto.car.details.CarDetailsDto;
import com.cabaggregator.driverservice.core.dto.car.details.CarDetailsSettingDto;
import com.cabaggregator.driverservice.core.dto.page.PagedDto;

public interface ICarDetailsService {
    PagedDto<CarDetailsDto> getPageOfCarDetails(
            int pageNumber, int pageSize, String sortField, String sortOrder);

    CarDetailsDto getCarDetailsByCarId(Long carId);

    CarDetailsDto saveCarDetails(Long carId, CarDetailsSettingDto carDetailsDto);

    CarDetailsDto updateCarDetails(Long carId, CarDetailsSettingDto carDetailsDto);
}
