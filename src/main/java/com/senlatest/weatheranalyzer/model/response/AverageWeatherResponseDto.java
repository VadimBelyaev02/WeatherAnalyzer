package com.senlatest.weatheranalyzer.model.response;

import lombok.Getter;
import lombok.Setter;

import java.time.OffsetDateTime;

public record AverageWeatherResponseDto(
        double averageTemperature,
        double averageWindSpeed,
        int averagePressure,
        int averageAirHumidity
) {
}
