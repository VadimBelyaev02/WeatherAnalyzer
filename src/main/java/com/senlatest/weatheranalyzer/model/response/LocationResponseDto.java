package com.senlatest.weatheranalyzer.model.response;

import java.util.UUID;

public record LocationResponseDto(
        UUID id,
        String city,
        String displayName,
        double latitude,
        double longitude
) {
}
