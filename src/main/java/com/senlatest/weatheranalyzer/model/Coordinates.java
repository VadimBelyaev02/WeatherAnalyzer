package com.senlatest.weatheranalyzer.model;

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
