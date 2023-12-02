package com.senlatest.weatheranalyzer.client;

import com.senlatest.weatheranalyzer.client.model.LocationInfo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.List;
import java.util.Objects;

@Component
@RequiredArgsConstructor
public class OpenStreetMapClient {

    private final WebClient webClient;

    public LocationInfo getLocationByCityName(String cityName) {
        String uri = UriComponentsBuilder.fromUriString("https://nominatim.openstreetmap.org/search")
                .queryParam("q", cityName)
                .queryParam("format", "json")
                .build()
                .toUriString();

        List<LocationInfo> locationInfoList = Objects.requireNonNull(webClient.get()
                .uri(uri)
                .retrieve()
                .bodyToFlux(LocationInfo.class)
                .collectList()
                .block());
        return locationInfoList.get(0);
    }
}
