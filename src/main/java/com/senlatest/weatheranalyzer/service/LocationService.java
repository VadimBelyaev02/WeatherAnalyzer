package com.senlatest.weatheranalyzer.service;

import com.senlatest.weatheranalyzer.model.request.LocationRequestDto;
import com.senlatest.weatheranalyzer.model.response.LocationResponseDto;

import java.util.List;
import java.util.UUID;

public interface LocationService {

    LocationResponseDto getById(UUID locationId);

    List<LocationResponseDto> getAll();

    LocationResponseDto save(LocationRequestDto locationRequestDto);

    LocationResponseDto update(UUID id, LocationRequestDto locationRequestDto);

    void deleteById(UUID locationId);
}
