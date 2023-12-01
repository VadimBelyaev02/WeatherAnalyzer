package com.senlatest.weatheranalyzer.client;

import com.senlatest.weatheranalyzer.client.model.Coordinates;
import com.senlatest.weatheranalyzer.client.model.LocationInfo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.Objects;

@Component
@RequiredArgsConstructor
public class OpenStreetMapClient {

    private final String BASE_URI = "https://nominatim.openstreetmap.org/search";
    private final WebClient webClient;

    public Coordinates getLonAndLatByCityName(String cityName) {
        String uri = UriComponentsBuilder.fromUriString(BASE_URI)
                .queryParam("q", cityName)
                .queryParam("format", "json")
                .build()
                .toUriString();

        return Objects.requireNonNull(webClient.get()
                        .uri(uri)
                        .retrieve()
                        .bodyToMono(LocationInfo.class)
                        .block())
                .getLatAndLon();

    }
}
