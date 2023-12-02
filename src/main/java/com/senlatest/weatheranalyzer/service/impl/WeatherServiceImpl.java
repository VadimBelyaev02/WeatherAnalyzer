package com.senlatest.weatheranalyzer.service.impl;

import com.senlatest.weatheranalyzer.exception.NotFoundException;
import com.senlatest.weatheranalyzer.model.entity.Weather;
import com.senlatest.weatheranalyzer.model.mapper.WeatherMapper;
import com.senlatest.weatheranalyzer.model.response.AverageWeatherResponseDto;
import com.senlatest.weatheranalyzer.model.response.CurrentWeatherResponseDto;
import com.senlatest.weatheranalyzer.repository.WeatherRepository;
import com.senlatest.weatheranalyzer.service.WeatherService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.time.Instant;
import java.time.LocalDate;
import java.time.OffsetDateTime;

@Service
@RequiredArgsConstructor
public class WeatherServiceImpl implements WeatherService {

    private final WeatherRepository weatherRepository;
    private final WeatherMapper weatherMapper;

    @Override
    public CurrentWeatherResponseDto getCurrentWeather(String cityName) {
        Weather weather = weatherRepository.findByDate(OffsetDateTime.now()).orElseThrow(
                () -> new NotFoundException("There is no a single weather")
        );
        return weatherMapper.toCurrentWeatherResponseDto(weather);
    }

    @Override
    public AverageWeatherResponseDto getAverageWeather(String cityName, OffsetDateTime from, OffsetDateTime to) {
        Weather weather = weatherRepository.findAverageWeatherForPeriod(from, to, cityName).orElseThrow(
                () -> new NotFoundException("There is no a single weather that satisfies the condition")
        );
        return weatherMapper.toAverageWeatherResponseDto(weather);
    }

}
