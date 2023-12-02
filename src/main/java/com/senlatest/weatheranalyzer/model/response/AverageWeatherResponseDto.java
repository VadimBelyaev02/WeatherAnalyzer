package com.senlatest.weatheranalyzer.model.response;

import lombok.Builder;

@Builder
public record AverageWeatherResponseDto(
        double averageTemperature,
        double averageWindSpeed,
        int averagePressure,
        int averageAirHumidity
) {
}
