package com.senlatest.weatheranalyzer.service;

import com.senlatest.weatheranalyzer.model.response.AverageWeatherResponseDto;
import com.senlatest.weatheranalyzer.model.response.CurrentWeatherResponseDto;

import java.time.Instant;
import java.time.LocalDate;

public interface WeatherService {

    CurrentWeatherResponseDto getCurrentWeather(String cityName);

    AverageWeatherResponseDto getAverageWeather(String cityName, Instant from, Instant to);
}
