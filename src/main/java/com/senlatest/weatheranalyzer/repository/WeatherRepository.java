package com.senlatest.weatheranalyzer.repository;

import com.senlatest.weatheranalyzer.model.entity.Weather;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.Instant;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface WeatherRepository extends JpaRepository<Weather, UUID> {

    @Query(nativeQuery = true, value = "SELECT * from weather ORDER BY date limit 1")
    Optional<Weather> findLast();


    @Query(nativeQuery = true,
            value = "SELECT AVG(pressure) as pressure, AVG(temperature) as temperature, AVG(wind_speed) as windSpeed, " +
                    "AVG(air_humidity) as airHumidity, date FROM weather WHERE city = :city AND data >= :from AND date <= :to")
    Optional<Weather> findAverageWeatherForPeriod(Instant from, Instant to, String city);

    void deleteAllByDateAfterAndDateBefore(Instant from, Instant to);

    void deleteByDate(Instant date);

    void updateByDate(Instant date);
}

/*
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
 */