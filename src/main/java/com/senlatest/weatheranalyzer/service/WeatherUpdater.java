package com.senlatest.weatheranalyzer.service;

import com.senlatest.weatheranalyzer.client.WeatherbitClient;
import com.senlatest.weatheranalyzer.model.entity.Weather;
import com.senlatest.weatheranalyzer.repository.WeatherRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;

import java.time.Instant;
import java.util.List;
import java.util.concurrent.TimeUnit;

@RequiredArgsConstructor
public class WeatherUpdater {

    private final WeatherRepository weatherRepository;
    private final WeatherbitClient weatherbitClient;

    @Scheduled(fixedRateString = "${weather.forecast.daily.update-frequency}", timeUnit = TimeUnit.MINUTES)
    private void updateWeatherForecast() {
        String city = "Minsk";
        List<Weather> weatherList = weatherbitClient.getDailyForecast(city);

        Instant fromDate = weatherList.get(0).getDate();
        Instant toDate = weatherList.get(weatherList.size() - 1).getDate();

        weatherRepository.deleteAllByDateAfterAndDateBefore(fromDate, toDate);

        weatherRepository.saveAll(weatherList);
//        LocalDate now = LocalDate.now();


        //      LocalDate localDate = currentWeather.get(0).getDate().atZone(ZoneId.systemDefault()).toLocalDate();

        // delete all where date between those dates
        // insert all the date back
    }

    @Scheduled(fixedRateString = "${weather.current.update-frequency}", timeUnit = TimeUnit.MINUTES)
    private void currentWeather() {
        String city = "Minsk";
        final Weather currentWeather = weatherbitClient.getCurrentWeather(city);
        weatherRepository.updateByDate(currentWeather.getDate());
    }
}
