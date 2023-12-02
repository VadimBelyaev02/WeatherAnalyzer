package com.senlatest.weatheranalyzer.client;

import com.senlatest.weatheranalyzer.client.model.CurrentObsGroup;
import com.senlatest.weatheranalyzer.client.model.ForecastDay;
import com.senlatest.weatheranalyzer.exception.BadResponseFromExternalService;
import com.senlatest.weatheranalyzer.model.entity.Location;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.Objects;

@RequiredArgsConstructor
@Component
public class WeatherbitClient {

    @Value("${weatherbit.api-key:db1a4abaffe74bba953da44ba8512827}")
    private String apiKey;
    private final WebClient webClient;

    public ForecastDay getDailyForecast(Location location) {
        String uri = getURI(location, "forecast/daily");

        ForecastDay forecastDay = webClient.get()
                .uri(uri)
                .retrieve()
                .bodyToMono(ForecastDay.class)
                .block();

        if (Objects.isNull(forecastDay) || Objects.isNull(forecastDay.getData()) || forecastDay.getData().isEmpty()) {
            throw new BadResponseFromExternalService("No data provided");
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
            throw new BadResponseFromExternalService("No data provided");
        }
        return currentObsGroup;
    }

    private String getURI(Location location, String uriSuffix) {
        return UriComponentsBuilder.fromUriString("https://api.weatherbit.io/v2.0/" + uriSuffix)
                .queryParam("lat", location.getLatitude())
                .queryParam("lon", location.getLongitude())
                .queryParam("key", apiKey)
                .build().toUriString();
    }
}
