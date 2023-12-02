package com.senlatest.weatheranalyzer.model.mapper;

import com.senlatest.weatheranalyzer.model.entity.Location;
import com.senlatest.weatheranalyzer.model.request.LocationRequestDto;
import com.senlatest.weatheranalyzer.model.response.LocationResponseDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper
public interface LocationMapper {

    LocationResponseDto toResponse(Location location);

    @Mapping(target = "weather", ignore = true)
    @Mapping(target = "longitude", ignore = true)
    @Mapping(target = "latitude", ignore = true)
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "displayName", ignore = true)
    Location toEntity(LocationRequestDto locationRequestDto);

    @Mapping(target = "weather", ignore = true)
    @Mapping(target = "longitude", ignore = true)
    @Mapping(target = "latitude", ignore = true)
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "displayName", ignore = true)
    void updateEntityFromRequestDto(LocationRequestDto locationRequestDto, @MappingTarget Location location);

}
