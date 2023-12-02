package com.senlatest.weatheranalyzer.repository;

import com.senlatest.weatheranalyzer.model.entity.Weather;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.OffsetDateTime;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

public interface WeatherRepository extends JpaRepository<Weather, UUID> {
    @Query(nativeQuery = true,
            value = "SELECT AVG(pressure) as pressure, ROUND(AVG(temperature), 2) as temperature, ROUND(AVG(wind_speed), 2) as wind_speed, " +
                    "AVG(air_humidity) as air_humidity FROM weather " +
                    "JOIN locations ON weather.location_id = locations.id " +
                    "WHERE DATE(date) >= DATE(:from) AND DATE(date) <= DATE(:to) AND city = :city")
    Optional<Map<String, Object>> findAverageWeatherForPeriod(OffsetDateTime from, OffsetDateTime to, String city);

    @Query(nativeQuery = true, value = "DELETE FROM weather " +
            "WHERE location_id IN (SELECT id FROM locations WHERE DATE(date) <= DATE(:to)  AND DATE(date) >= DATE(:from)" +
            " AND city = :city);")
    @Modifying
    void deleteAllByDateAfterAndDateBeforeAndCity(OffsetDateTime from, OffsetDateTime to, String city);

    @Query(nativeQuery = true, value = "UPDATE weather SET temperature = :#{#weather.temperature}, " +
            "wind_speed = :#{#weather.windSpeed}, pressure = :#{#weather.pressure}," +
            "weather_description = :#{#weather.weatherDescription}, " +
            "air_humidity = :#{#weather.airHumidity}, date = :#{#weather.date} " +
            "WHERE date(date) = DATE(:#{#weather.date})")
    @Modifying
    void updateByDate(@Param("weather") Weather weather);

    @Query(nativeQuery = true, value = "SELECT weather.id, temperature, wind_speed, pressure, air_humidity, date, " +
            "weather_description, location_id FROM weather JOIN locations ON weather.location_id = locations.id " +
            "WHERE DATE(date) = DATE(:date) AND locations.city = :city")
    Optional<Weather> findByDateAndCity(OffsetDateTime date, String city);
}