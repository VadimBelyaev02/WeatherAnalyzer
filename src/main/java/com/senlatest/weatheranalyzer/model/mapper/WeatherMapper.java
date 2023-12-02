package com.senlatest.weatheranalyzer.model.mapper;

import com.senlatest.weatheranalyzer.model.entity.Weather;
import com.senlatest.weatheranalyzer.model.response.AverageWeatherResponseDto;
import com.senlatest.weatheranalyzer.model.response.CurrentWeatherResponseDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.time.OffsetDateTime;

@Mapper
public interface WeatherMapper {

    @Mapping(target = "location", expression = "java(weather.getLocation().getCity())")
    CurrentWeatherResponseDto toCurrentWeatherResponseDto(Weather weather);

    @Mapping(target = "averageWindSpeed", source = "windSpeed")
    @Mapping(target = "averageTemperature", source = "temperature")
    @Mapping(target = "averagePressure", source = "pressure")
    @Mapping(target = "averageAirHumidity", source = "airHumidity")
    AverageWeatherResponseDto toAverageWeatherResponseDto(Weather weather);
}
