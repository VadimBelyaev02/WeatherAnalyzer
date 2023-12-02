package com.senlatest.weatheranalyzer.repository;

import com.senlatest.weatheranalyzer.model.entity.Weather;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.time.OffsetDateTime;
import java.util.Optional;
import java.util.UUID;

public interface WeatherRepository extends JpaRepository<Weather, UUID> {

    @Query(nativeQuery = true, value = "SELECT * from weather ORDER BY date limit 1")
    Optional<Weather> findLast();


    @Query(nativeQuery = true,
            value = "SELECT AVG(pressure) as pressure, AVG(temperature) as temperature, AVG(wind_speed) as windSpeed, " +
                    "AVG(air_humidity) as airHumidity, date FROM weather WHERE city = :city " +
                    "AND data >= :from AND DATE(date) <= DATE(:to)")
    Optional<Weather> findAverageWeatherForPeriod(OffsetDateTime from, OffsetDateTime to, String city);


    void deleteAllByDateAfterAndDateBefore(OffsetDateTime from, OffsetDateTime to);

    @Query(nativeQuery = true, value = "UPDATE weather SET temperature = :#{#weather.temperature}, " +
            "wind_speed = :#{#weather.windSpeed}, pressure = :#{#weather.pressure}," +
            "weather_description = :#{#weather.weatherDescription}, location = :#{#weather.location}," +
            "air_humidity = :#{#weather.airHumidity}, date = :#{#weather.date} " +
            "WHERE date = :#{#weather.date}")
    @Modifying
    void updateByDate(Weather weather);

    @Query(nativeQuery = true, value = "SELECT * FROM weather WHERE DATE(date) = DATE(:date)")
    Optional<Weather> findByDate(OffsetDateTime date);
}