package com.senlatest.weatheranalyzer.model.response;

public record CurrentWeatherResponseDto(
    double temperature,
    double windSpeed,
    int pressure,
    int airHumidity,
    String location
) {}
