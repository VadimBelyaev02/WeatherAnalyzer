package com.senlatest.weatheranalyzer.controller;

import com.senlatest.weatheranalyzer.model.response.AverageWeatherResponseDto;
import com.senlatest.weatheranalyzer.model.response.CurrentWeatherResponseDto;
import com.senlatest.weatheranalyzer.service.WeatherService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/weather")
public class WeatherController {

    private final WeatherService weatherService;

    @GetMapping("/current")
    public ResponseEntity<CurrentWeatherResponseDto> getCurrentWeather(@RequestParam(value = "city") String city) {
        CurrentWeatherResponseDto currentWeather = weatherService.getCurrentWeather(city);
        return ResponseEntity.ok(currentWeather);
    }

    @GetMapping("/average")
    public ResponseEntity<AverageWeatherResponseDto> getAverageWeather(
            @RequestParam(value = "city") String city,
            @RequestParam(value = "from") LocalDate from,
            @RequestParam(value = "to") LocalDate to
            ) {
        AverageWeatherResponseDto averageWeather = weatherService.getAverageWeather(city, from, to);
        return ResponseEntity.ok(averageWeather);
    }

}
