package com.senlatest.weatheranalyzer.repository;

import com.senlatest.weatheranalyzer.model.entity.Location;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface LocationsRepository extends JpaRepository<Location, UUID> {

    Optional<Location> findByCity(String city);

    boolean existsByCity(String city);
}
