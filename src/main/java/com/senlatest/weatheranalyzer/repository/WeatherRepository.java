package com.senlatest.weatheranalyzer.repository;

import com.senlatest.weatheranalyzer.model.entity.Weather;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.OffsetDateTime;
import java.util.Optional;
import java.util.UUID;

public interface WeatherRepository extends JpaRepository<Weather, UUID> {

    @Query(nativeQuery = true,
            value = "SELECT AVG(pressure) as pressure, AVG(temperature) as temperature, AVG(wind_speed) as windSpeed, " +
                    "AVG(air_humidity) as airHumidity FROM weather JOIN locations ON weather.location_id = locations.id " +
                    "WHERE DATE(date) >= DATE(:from) AND DATE(date) <= DATE(:to) AND city = :city")
        Optional<Weather> findAverageWeatherForPeriod(OffsetDateTime from, OffsetDateTime to, String city);


    void deleteAllByDateAfterAndDateBefore(OffsetDateTime from, OffsetDateTime to);

    @Query(nativeQuery = true, value = "UPDATE weather SET temperature = :#{#weather.temperature}, " +
            "wind_speed = :#{#weather.windSpeed}, pressure = :#{#weather.pressure}," +
            "weather_description = :#{#weather.weatherDescription}, " +
            "air_humidity = :#{#weather.airHumidity}, date = :#{#weather.date} " +
            "WHERE date(date) = DATE(:#{#weather.date})")
    @Modifying
    void updateByDate(@Param("weather") Weather weather);

    @Query(nativeQuery = true, value = "SELECT * FROM weather JOIN locations ON weather.location_id = locations.id " +
            "WHERE DATE(date) = DATE(:date) AND locations.city = :city")
    Optional<Weather> findByDateAndCity(OffsetDateTime date, String city);
}
/*
package com.senlatest.weatheranalyzer.repository;

import com.senlatest.weatheranalyzer.model.entity.Weather;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.OffsetDateTime;
import java.util.Optional;
import java.util.UUID;

public interface WeatherRepository extends JpaRepository<Weather, UUID> {

    @Query(nativeQuery = true,
            value = "SELECT AVG(pressure) as pressure, AVG(temperature) as temperature, AVG(wind_speed) as windSpeed, " +
                    "AVG(air_humidity) as airHumidity FROM weather JOIN locations ON weather.location_id = locations.id " +
                    "WHERE DATE(date) >= DATE(:from) AND DATE(date) <= DATE(:to) AND city = :city")
        Optional<Weather> findAverageWeatherForPeriod(OffsetDateTime from, OffsetDateTime to, String city);


    void deleteAllByDateAfterAndDateBefore(OffsetDateTime from, OffsetDateTime to);

    @Query(nativeQuery = true, value = "UPDATE weather SET temperature = :#{#weather.temperature}, " +
            "wind_speed = :#{#weather.windSpeed}, pressure = :#{#weather.pressure}," +
            "weather_description = :#{#weather.weatherDescription}, " +
            "air_humidity = :#{#weather.airHumidity}, date = :#{#weather.date} " +
            "WHERE date(date) = DATE(:#{#weather.date})")
    @Modifying
    void updateByDate(@Param("weather") Weather weather);

    @Query(nativeQuery = true, value = "SELECT id, temperature, wind_speed, pressure, air_humidity, date, weather_description FROM weather JOIN locations ON weather.location_id = locations.id " +
            "WHERE DATE(date) = DATE(:date) AND locations.city = :city")
    Optional<Weather> findByDateAndCity(OffsetDateTime date, String city);
}/*
    private UUID id;
    private float temperature;

    @Column(name = "wind_speed")
    private float windSpeed;
    private int pressure;

    @Column(name = "air_humidity")
    private int airHumidity;
    private OffsetDateTime date;

    @Column(name = "weather_description")
    private String weatherDescription;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "location_id")
    private Location location;

 */