package com.senlatest.weatheranalyzer.service.impl;

import com.senlatest.weatheranalyzer.exception.NotFoundException;
import com.senlatest.weatheranalyzer.logging.annotation.Log;
import com.senlatest.weatheranalyzer.model.entity.Weather;
import com.senlatest.weatheranalyzer.model.mapper.WeatherMapper;
import com.senlatest.weatheranalyzer.model.response.AverageWeatherResponseDto;
import com.senlatest.weatheranalyzer.model.response.CurrentWeatherResponseDto;
import com.senlatest.weatheranalyzer.repository.WeatherRepository;
import com.senlatest.weatheranalyzer.service.WeatherService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.OffsetDateTime;
import java.time.ZoneOffset;

@Log
@Service
@RequiredArgsConstructor
public class WeatherServiceImpl implements WeatherService {

    private final WeatherRepository weatherRepository;
    private final WeatherMapper weatherMapper;

    @Override
    public CurrentWeatherResponseDto getCurrentWeather(String city) {
        Weather weather = weatherRepository.findByDateAndCity(LocalDate.now().atStartOfDay().atOffset(ZoneOffset.UTC), city).orElseThrow(
                () -> new NotFoundException("There is no any information about weather in " + city)
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
