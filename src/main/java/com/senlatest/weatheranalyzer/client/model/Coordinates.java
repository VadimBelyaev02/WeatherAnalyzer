package com.senlatest.weatheranalyzer.client.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@AllArgsConstructor
@Data
@Builder
public class Coordinates {
    private double longitude;
    private double latitude;
}
