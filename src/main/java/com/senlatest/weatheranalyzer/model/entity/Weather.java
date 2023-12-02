package com.senlatest.weatheranalyzer.model.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;
import java.time.Instant;
import java.time.OffsetDateTime;
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
    private float temperature;

    @Column(name = "wind_speed")
    private float windSpeed;
    private int pressure;

    @Column(name = "air_humidity")
    private int airHumidity;
    private String location;
    private OffsetDateTime date;

    @Column(name = "weather_description")
    private String weatherDescription;
}
