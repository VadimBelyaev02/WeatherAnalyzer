package com.senlatest.weatheranalyzer.controller;

import com.senlatest.weatheranalyzer.model.CurrentWeatherResponse;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/weather")
public class WeatherController {

    @GetMapping("/current")
    public CurrentWeatherResponse getCurrentWeather() {

    }

}
