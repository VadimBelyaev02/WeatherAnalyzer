package com.senlatest.weatheranalyzer.client;

import com.senlatest.weatheranalyzer.model.*;
import com.senlatest.weatheranalyzer.model.entity.Weather;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.util.UriComponentsBuilder;

@RequiredArgsConstructor
@Component
public class WeatherbitClient {

    private final String API_KEY = "294a275ba6394baaa8e5e84639f94a27";
    private final String URI = "https://api.weatherbit.io/v2.0/current";
    private final WebClient webClient;
    private final OpenStreetMapClient openStreetMapClient;

    public Weather getCurrentWeather(String cityName) {
        Coordinates coordinates = openStreetMapClient.getLonAndLatByCityName(cityName);

        String uri = UriComponentsBuilder.fromUriString(URI)
                .queryParam("lat", coordinates.getLatitude())
                .queryParam("lon", coordinates.getLongitude())
                .queryParam("key", API_KEY)
                .build().toUriString();

        WeatherData weatherData = webClient.get()
                .uri(uri)
                .retrieve()
                .bodyToMono(WeatherData.class)
                .block();

        return Weather.builder()
                .airHumidity(weatherData.getData().get(0).getRh())
                .build();
    }

}
