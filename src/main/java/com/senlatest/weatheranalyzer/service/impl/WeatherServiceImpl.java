package com.senlatest.weatheranalyzer.service.impl;

import com.senlatest.weatheranalyzer.client.WeatherbitClient;
import com.senlatest.weatheranalyzer.exception.NotFoundException;
import com.senlatest.weatheranalyzer.model.entity.Weather;
import com.senlatest.weatheranalyzer.model.mapper.WeatherMapper;
import com.senlatest.weatheranalyzer.model.response.AverageWeatherResponseDto;
import com.senlatest.weatheranalyzer.model.response.CurrentWeatherResponseDto;
import com.senlatest.weatheranalyzer.repository.WeatherRepository;
import com.senlatest.weatheranalyzer.service.WeatherService;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.concurrent.TimeUnit;

@Service
@RequiredArgsConstructor
public class WeatherServiceImpl implements WeatherService {

    private final WeatherbitClient weatherbitClient;
    private final WeatherRepository weatherRepository;
    private final WeatherMapper weatherMapper;

    @Override
    public CurrentWeatherResponseDto getCurrentWeather(String cityName) {
        Weather weather = weatherRepository.findLast().orElseThrow(
                () -> new NotFoundException("There is no a single weather")
        );
        return weatherMapper.toCurrentWeatherResponseDto(weather);
    }

    @Override
    public AverageWeatherResponseDto getAverageWeather(String cityName, Instant from, Instant to) {
        Weather weather = weatherRepository.findAverageWeatherForPeriod(from, to, cityName).orElseThrow(
                () -> new NotFoundException("There is no a single weather that satisfies the condition")
        );

    }

    @Scheduled(fixedRate = 3, timeUnit = TimeUnit.MINUTES)
    private void updateWeatherInfo() {
        String city = "Minsk";
        Weather currentWeather = weatherbitClient.getCurrentWeather(city);
        weatherRepository.save(currentWeather);
    }

}
