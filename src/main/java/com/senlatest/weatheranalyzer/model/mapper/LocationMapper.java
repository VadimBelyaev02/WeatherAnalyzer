package com.senlatest.weatheranalyzer.model.mapper;

import com.senlatest.weatheranalyzer.model.entity.Location;
import com.senlatest.weatheranalyzer.model.request.LocationRequestDto;
import com.senlatest.weatheranalyzer.model.response.LocationResponseDto;
import org.mapstruct.Mapper;

@Mapper
public interface LocationMapper {


    LocationResponseDto toResponse(Location location);

    Location toEntity(LocationRequestDto locationRequestDto);
}
