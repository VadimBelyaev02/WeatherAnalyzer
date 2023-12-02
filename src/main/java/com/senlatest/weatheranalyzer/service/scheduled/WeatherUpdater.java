package com.senlatest.weatheranalyzer.service.scheduled;

import com.senlatest.weatheranalyzer.client.WeatherbitClient;
import com.senlatest.weatheranalyzer.model.entity.Weather;
import com.senlatest.weatheranalyzer.repository.WeatherRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Timestamp;
import java.time.OffsetDateTime;
import java.util.List;
import java.util.concurrent.TimeUnit;

@RequiredArgsConstructor
@Component
public class WeatherUpdater {

    private final WeatherRepository weatherRepository;
    private final WeatherbitClient weatherbitClient;

    @Transactional
    @Scheduled(fixedRateString = "${weather.forecast.daily.update-frequency}", timeUnit = TimeUnit.HOURS)
    public void updateWeatherForecast() {
        String city = "Minsk";
//        List<Weather> weatherList = weatherbitClient.getDailyForecast(city);
//
//        OffsetDateTime fromDate = weatherList.get(0).getDate();
//        OffsetDateTime toDate = weatherList.get(weatherList.size() - 1).getDate();
//
//        weatherRepository.deleteAllByDateAfterAndDateBefore(fromDate, toDate);
//
//        weatherRepository.saveAll(weatherList);

    }

    @Transactional
    @Scheduled(fixedRateString = "${weather.current.update-frequency}", timeUnit = TimeUnit.SECONDS)
    public void currentWeather() {
        String city = "Minsk";
        final Weather currentWeather = weatherbitClient.getCurrentWeather(city);
        weatherRepository.updateByDate(currentWeather);
    }
}
