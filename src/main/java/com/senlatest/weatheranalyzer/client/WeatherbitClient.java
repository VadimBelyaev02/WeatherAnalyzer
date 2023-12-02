package com.senlatest.weatheranalyzer.client;

import com.senlatest.weatheranalyzer.client.model.Coordinates;
import com.senlatest.weatheranalyzer.client.model.CurrentObsGroup;
import com.senlatest.weatheranalyzer.client.model.ForecastDay;
import com.senlatest.weatheranalyzer.model.entity.Weather;
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

    @Value("${weatherbit.api-key:294a275ba6394baaa8e5e84639f94a27}")
    private String API_KEY;
    private final String BASE_URI = "https://api.weatherbit.io/v2.0/";
    private final WebClient webClient;
    private final OpenStreetMapClient openStreetMapClient;


    public List<Weather> getDailyForecast(String city) {
        String uri = getURI(city, "forecast/daily");

        ForecastDay forecastDay = webClient.get()
                .uri(uri)
                .retrieve()
                .bodyToMono(ForecastDay.class)
                .block();

        if (Objects.isNull(forecastDay) || Objects.isNull(forecastDay.getData()) || forecastDay.getData().isEmpty()) {
            throw new RuntimeException();
        }

        for (int i = 0; i < forecastDay.getData().size(); i++) {
            System.out.println(forecastDay.getData().get(i) + "\n\n\n");
            if (forecastDay.getData().get(i).getDatetime().contains(":")) {
                System.out.println("!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!");
            }
        }
        ;
        return forecastDay.getData().stream()
                .peek(System.out::println)
                .map(forecast -> Weather.builder()
                        .date(LocalDate.parse(forecast.getDatetime())
                                .atStartOfDay()
                                .atOffset(ZoneOffset.UTC))//                        .airHumidity(forecast.getRh())
                        .location(city)
                        .pressure(forecast.getPres())
                        .temperature(forecast.getTemp())
                        .weatherDescription(forecast.getWeather().getDescription())
                        .windSpeed(forecast.getWind_spd())
                        .build()
                ).toList();
        //return null;
    }

    public Weather getCurrentWeather(String city) {
        String uri = getURI(city, "current");

        CurrentObsGroup currentObsGroup = webClient.get()
                .uri(uri)
                .retrieve()
                .bodyToMono(CurrentObsGroup.class)
                .block();

        if (Objects.isNull(currentObsGroup) || Objects.isNull(currentObsGroup.getData()) || currentObsGroup.getData().isEmpty()) {
            throw new RuntimeException();
        }
        final CurrentObsGroup.CurrentObs currentObs = currentObsGroup.getData().get(0);

        return Weather.builder()
                .weatherDescription(currentObs.getWeather().getDescription())
                .location(city)
                .date(OffsetDateTime.of(LocalDateTime.parse(currentObs.getDatetime(), DateTimeFormatter.ofPattern("yyyy-MM-dd:H")), ZoneOffset.UTC))

                .windSpeed(currentObs.getWind_speed())
                .airHumidity(currentObs.getRh())
                .temperature(currentObs.getTemp())
                .pressure(currentObs.getPres())
                .build();
    }

    private String getURI(String city, String uriSuffix) {
        Coordinates coordinates = openStreetMapClient.getLonAndLatByCityName(city);

        return UriComponentsBuilder.fromUriString(BASE_URI + uriSuffix)
                .queryParam("lat", coordinates.getLatitude())
                .queryParam("lon", coordinates.getLongitude())
                .queryParam("key", API_KEY)
                .build().toUriString();
    }
}
