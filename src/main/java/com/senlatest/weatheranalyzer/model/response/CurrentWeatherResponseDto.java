package com.senlatest.weatheranalyzer.model.response;

import lombok.Builder;

@Builder
public record CurrentWeatherResponseDto(
        float temperature,
        float windSpeed,
        int pressure,
        int airHumidity,
        String location
) {
}
