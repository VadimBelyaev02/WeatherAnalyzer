package com.senlatest.weatheranalyzer.unit;

import com.senlatest.weatheranalyzer.model.entity.Weather;
import com.senlatest.weatheranalyzer.model.mapper.WeatherMapper;
import com.senlatest.weatheranalyzer.model.response.AverageWeatherResponseDto;
import com.senlatest.weatheranalyzer.model.response.CurrentWeatherResponseDto;
import com.senlatest.weatheranalyzer.repository.WeatherRepository;
import com.senlatest.weatheranalyzer.service.impl.WeatherServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.time.OffsetDateTime;
import java.time.ZoneOffset;
import java.util.Optional;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@DisplayName("Weather service tests for methods")
@ExtendWith(MockitoExtension.class)
public class WeatherServiceUnitTest {

    @Mock
    private WeatherRepository weatherRepository;

    @Mock
    private WeatherMapper weatherMapper;

    @InjectMocks
    private WeatherServiceImpl weatherService;
    private Weather currentWeather;
    private CurrentWeatherResponseDto currentWeatherResponseDto;
    private AverageWeatherResponseDto averageWeatherResponseDto;

    @BeforeEach
    void setup() {
        int pressure = 10;
        int airHumidity = 50;
        float temperature = -5.99f;
        float windSpeed = 55.5f;
        currentWeather = Weather.builder()
                .pressure(pressure)
                .airHumidity(airHumidity)
                .temperature(temperature)
                .windSpeed(windSpeed)
                .weatherDescription("Cloudy")
                .build();

        currentWeatherResponseDto = CurrentWeatherResponseDto.builder()
                .pressure(pressure)
                .airHumidity(airHumidity)
                .temperature(temperature)
                .windSpeed(windSpeed)
                .build();

        averageWeatherResponseDto = AverageWeatherResponseDto.builder()
                .averagePressure(pressure)
                .averageAirHumidity(airHumidity)
                .averageTemperature(temperature)
                .averageWindSpeed(windSpeed)
                .build();
    }

    @Test
    void Given_CityNameAndDate_When_AccessingWeather_Then_WeatherIsReturned() {
        OffsetDateTime date = LocalDate.now().atStartOfDay().atOffset(ZoneOffset.UTC);
        String city = "Minsk";

        currentWeather.setDate(date);

        when(weatherRepository.findByDateAndCity(date, city)).thenReturn(Optional.of(currentWeather));
        when(weatherMapper.toCurrentWeatherResponseDto(currentWeather)).thenReturn(currentWeatherResponseDto);

        assertEquals(currentWeatherResponseDto, weatherService.getCurrentWeather(city));

        verify(weatherRepository, only()).findByDateAndCity(date, city);
        verify(weatherMapper, only()).toCurrentWeatherResponseDto(currentWeather);
    }

    @ParameterizedTest
    @MethodSource("offsetDateTimeAndCityProvider")
    void Given_CityAndPeriod_When_CalculateAverageValues_Then_AverageValuesAreReturned(OffsetDateTime fromDate, OffsetDateTime toDate, String city) {

        when(weatherRepository.findAverageWeatherForPeriod(fromDate, toDate, city)).thenReturn(Optional.of(currentWeather));
        when(weatherMapper.toAverageWeatherResponseDto(currentWeather)).thenReturn(averageWeatherResponseDto);

        assertEquals(averageWeatherResponseDto, weatherService.getAverageWeather(city, fromDate, toDate));

        verify(weatherRepository, only()).findAverageWeatherForPeriod(fromDate, toDate, city);
        verify(weatherMapper, only()).toAverageWeatherResponseDto(currentWeather);
    }

    static Stream<Arguments> offsetDateTimeAndCityProvider() {
        return Stream.of(
                Arguments.of(OffsetDateTime.parse("2023-12-04T12:00:00Z"), OffsetDateTime.parse("2023-12-05T12:00:00Z"), "Minsk"),
                Arguments.of(OffsetDateTime.parse("2023-12-03T08:30:00Z"), OffsetDateTime.parse("2023-12-04T12:00:00Z"), "Gomel"),
                Arguments.of(OffsetDateTime.parse("2023-12-12T01:55:00Z"), OffsetDateTime.parse("2023-12-20T12:00:00Z"), "Vitebsk")
        );
    }
}
