package com.senlatest.weatheranalyzer.service.impl;

import com.senlatest.weatheranalyzer.client.OpenStreetMapClient;
import com.senlatest.weatheranalyzer.client.model.LocationInfo;
import com.senlatest.weatheranalyzer.exception.DuplicateRecordException;
import com.senlatest.weatheranalyzer.exception.NotFoundException;
import com.senlatest.weatheranalyzer.model.entity.Location;
import com.senlatest.weatheranalyzer.model.mapper.LocationMapper;
import com.senlatest.weatheranalyzer.model.request.LocationRequestDto;
import com.senlatest.weatheranalyzer.model.response.LocationResponseDto;
import com.senlatest.weatheranalyzer.repository.LocationsRepository;
import com.senlatest.weatheranalyzer.service.LocationService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class LocationServiceImpl implements LocationService {

    private final LocationMapper locationMapper;
    private final LocationsRepository locationsRepository;
    private final OpenStreetMapClient openStreetMapClient;

    @Override
    @Transactional(readOnly = true)
    public LocationResponseDto getById(UUID locationId) {
        Location location = locationsRepository.findById(locationId).orElseThrow(
                () -> new NotFoundException("Location with id = " + locationId + " wasn't found")
        );
        return locationMapper.toResponse(location);
    }

    @Override
    @Transactional(readOnly = true)
    public List<LocationResponseDto> getAll() {
        return locationsRepository.findAll().stream()
                .map(locationMapper::toResponse)
                .toList();
    }

    @Override
    @Transactional
    public LocationResponseDto save(LocationRequestDto locationRequestDto) {
        if (locationsRepository.existsByCity(locationRequestDto.getCity())) {
            throw new DuplicateRecordException("Location with city = " + locationRequestDto.getCity() + " already exists");
        }
        LocationInfo locationInfo = openStreetMapClient.getLocationByCityName(locationRequestDto.getCity());
        Location location = locationMapper.toEntity(locationRequestDto);
        location.setLongitude(locationInfo.getLon());
        location.setLatitude(locationInfo.getLat());
        location.setDisplayName(locationInfo.getDisplay_name());

        Location savedLocation = locationsRepository.save(locationMapper.toEntity(locationRequestDto));
        return locationMapper.toResponse(savedLocation);
    }

    @Override
    @Transactional
    public LocationResponseDto update(UUID locationId, LocationRequestDto locationRequestDto) {
        if (!locationsRepository.existsById(locationId)) {
            throw new NotFoundException("Location with id = " + locationId + " wasn't found");
        }
        Location location = locationsRepository.save(locationMapper.toEntity(locationRequestDto));
        return locationMapper.toResponse(location);
    }

    @Override
    @Transactional
    public void deleteById(UUID locationId) {
        if (!locationsRepository.existsById(locationId)) {
            throw new NotFoundException("Location with id = " + locationId + " wasn't found");
        }
        locationsRepository.deleteById(locationId);
    }
}
