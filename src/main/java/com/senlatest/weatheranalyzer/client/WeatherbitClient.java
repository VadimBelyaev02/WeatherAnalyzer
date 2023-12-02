package com.senlatest.weatheranalyzer.client;

import com.senlatest.weatheranalyzer.client.model.Coordinates;
import com.senlatest.weatheranalyzer.client.model.CurrentObsGroup;
import com.senlatest.weatheranalyzer.client.model.ForecastDay;
import com.senlatest.weatheranalyzer.model.entity.Location;
import com.senlatest.weatheranalyzer.model.entity.Weather;
import com.senlatest.weatheranalyzer.repository.LocationsRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.util.UriComponentsBuilder;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.OffsetDateTime;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Objects;

@RequiredArgsConstructor
@Component
public class WeatherbitClient {

    @Value("${weatherbit.api-key:db1a4abaffe74bba953da44ba8512827}")
    private String API_KEY;
    private final WebClient webClient;
    private final OpenStreetMapClient openStreetMapClient;

    public ForecastDay getDailyForecast(Location location) {
        String uri = getURI(location, "forecast/daily");

        ForecastDay forecastDay = webClient.get()
                .uri(uri)
                .retrieve()
                .bodyToMono(ForecastDay.class)
                .block();

        if (Objects.isNull(forecastDay) || Objects.isNull(forecastDay.getData()) || forecastDay.getData().isEmpty()) {
            throw new RuntimeException();
        }
        return forecastDay;
    }

    public CurrentObsGroup getCurrentWeather(Location location) {
        String uri = getURI(location, "current");

        CurrentObsGroup currentObsGroup = webClient.get()
                .uri(uri)
                .retrieve()
                .bodyToMono(CurrentObsGroup.class)
                .block();

        if (Objects.isNull(currentObsGroup) || Objects.isNull(currentObsGroup.getData()) || currentObsGroup.getData().isEmpty()) {
            throw new RuntimeException();
        }
        return currentObsGroup;
    }

    private String getURI(Location location, String uriSuffix) {
    //    Coordinates coordinates = openStreetMapClient.getLonAndLatByCityName(city);

        String BASE_URI = "https://api.weatherbit.io/v2.0/";
        return UriComponentsBuilder.fromUriString(BASE_URI + uriSuffix)
                .queryParam("lat", location.getLatitude())
                .queryParam("lon", location.getLongitude())
                .queryParam("key", API_KEY)
                .build().toUriString();
    }
}
