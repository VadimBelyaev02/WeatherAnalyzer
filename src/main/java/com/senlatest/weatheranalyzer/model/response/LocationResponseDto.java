package com.senlatest.weatheranalyzer.model.response;

import lombok.Builder;

import java.util.UUID;

@Builder
public record LocationResponseDto(
        UUID id,
        String city,
        String displayName
) {
}
