package com.senlatest.weatheranalyzer.service.scheduled;

import com.senlatest.weatheranalyzer.client.WeatherbitClient;
import com.senlatest.weatheranalyzer.client.model.CurrentObsGroup;
import com.senlatest.weatheranalyzer.client.model.ForecastDay;
import com.senlatest.weatheranalyzer.model.entity.Location;
import com.senlatest.weatheranalyzer.model.entity.Weather;
import com.senlatest.weatheranalyzer.repository.LocationsRepository;
import com.senlatest.weatheranalyzer.repository.WeatherRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.OffsetDateTime;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.concurrent.TimeUnit;

@RequiredArgsConstructor
@Component
public class WeatherUpdater {

    private final WeatherRepository weatherRepository;
    private final WeatherbitClient weatherbitClient;
    private final LocationsRepository locationsRepository;

    @Transactional
    @Scheduled(fixedRateString = "${weather.forecast.daily.update-frequency}", timeUnit = TimeUnit.SECONDS)
    public void updateWeatherForecast() {
        List<Location> locations = locationsRepository.findAll();

        for (Location location : locations) {
            ForecastDay forecastDay = weatherbitClient.getDailyForecast(location);

            List<Weather> weatherList = forecastDay.getData().stream()
                    .map(forecast -> Weather.builder()
                            .date(LocalDate.parse(forecast.getDatetime())
                                    .atStartOfDay()
                                    .atOffset(ZoneOffset.UTC))//
                            .airHumidity(forecast.getRh())
                            .location(locationsRepository.findByCity(forecastDay.getCity_name()).orElseThrow())
                            .pressure(forecast.getPres())
                            .temperature(forecast.getTemp())
                            .weatherDescription(forecast.getWeather().getDescription())
                            .windSpeed(forecast.getWind_spd())
                            .build()
                    ).toList();

            OffsetDateTime fromDate = weatherList.get(0).getDate();
            OffsetDateTime toDate = weatherList.get(weatherList.size() - 1).getDate();

            weatherRepository.deleteAllByDateAfterAndDateBefore(fromDate, toDate);

            weatherRepository.saveAll(weatherList);
        }

    }

    @Transactional
    @Scheduled(fixedRateString = "${weather.current.update-frequency}", timeUnit = TimeUnit.SECONDS)
    public void currentWeather() {
        List<Location> locations = locationsRepository.findAll();

        for (Location location : locations) {
            CurrentObsGroup currentObsGroup = weatherbitClient.getCurrentWeather(location);
            CurrentObsGroup.CurrentObs currentObs = currentObsGroup.getData().get(0);
            Weather currentWeather = Weather.builder()
                    .weatherDescription(currentObs.getWeather().getDescription())
                    .location(location)
                    .date(OffsetDateTime.of(LocalDateTime.parse(currentObs.getDatetime(), DateTimeFormatter.ofPattern("yyyy-MM-dd:H")), ZoneOffset.UTC))

                    .windSpeed(currentObs.getWind_speed())
                    .airHumidity(currentObs.getRh())
                    .temperature(currentObs.getTemp())
                    .pressure(currentObs.getPres())
                    .build();
            weatherRepository.updateByDate(currentWeather);
        }
    }
}
