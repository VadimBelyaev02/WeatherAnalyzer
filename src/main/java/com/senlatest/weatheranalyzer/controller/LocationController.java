package com.senlatest.weatheranalyzer.controller;

import com.senlatest.weatheranalyzer.logging.annotation.Log;
import com.senlatest.weatheranalyzer.model.request.LocationRequestDto;
import com.senlatest.weatheranalyzer.model.response.LocationResponseDto;
import com.senlatest.weatheranalyzer.service.LocationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@Log
@RequiredArgsConstructor
@RestController
@RequestMapping("/api/locations")
public class LocationController {

    private final LocationService locationService;

    @GetMapping("/{id}")
    public ResponseEntity<LocationResponseDto> getLocation(@PathVariable("id") UUID locationId) {
        LocationResponseDto locationResponseDto = locationService.getById(locationId);
        return ResponseEntity.ok(locationResponseDto);
    }

    @GetMapping
    public ResponseEntity<List<LocationResponseDto>> getAllLocations() {
        List<LocationResponseDto> locations = locationService.getAll();
        return ResponseEntity.ok(locations);
    }

    @PostMapping
    public ResponseEntity<LocationResponseDto> postLocation(@RequestBody LocationRequestDto locationRequestDto) {
        LocationResponseDto locationResponseDto = locationService.save(locationRequestDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(locationResponseDto);
    }


    @PutMapping("/{id}")
    public ResponseEntity<LocationResponseDto> putLocation(@PathVariable("id") UUID id,
                                                           @RequestBody LocationRequestDto locationRequestDto) {
        LocationResponseDto locationResponseDto = locationService.update(id, locationRequestDto);
        return ResponseEntity.ok(locationResponseDto);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteLocation(@PathVariable("id") UUID id) {
        locationService.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
