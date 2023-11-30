package com.senlatest.weatheranalyzer.model.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;
import java.util.UUID;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
@Entity
@Table(name = "weather")
public class Weather {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;
    private double temperature;

    @Column(name = "wind_speed")
    private double windSpeed;
    private int pressure;

    @Column(name = "air_humidity")
    private int airHumidity;
    private String location;
    private Instant date;

    @Column(name = "weather_description")
    private String weatherDescription;
}
