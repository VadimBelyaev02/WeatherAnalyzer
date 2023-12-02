package com.senlatest.weatheranalyzer.service;

import com.senlatest.weatheranalyzer.model.response.AverageWeatherResponseDto;
import com.senlatest.weatheranalyzer.model.response.CurrentWeatherResponseDto;

import java.sql.Timestamp;
import java.time.Instant;
import java.time.LocalDate;
import java.time.OffsetDateTime;

public interface WeatherService {

    CurrentWeatherResponseDto getCurrentWeather(String cityName);

    AverageWeatherResponseDto getAverageWeather(String cityName, OffsetDateTime from, OffsetDateTime to);
}
