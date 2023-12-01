package com.senlatest.weatheranalyzer.model.response;

public record AverageWeatherResponseDto(
        double averageTemperature,
        double averageWindSpeed,
        int averagePressure,
        int averageAirHumidity
) {
}
