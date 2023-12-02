package com.senlatest.weatheranalyzer.client.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@AllArgsConstructor
@Data
@Builder
@NoArgsConstructor
public class LocationInfo {
    private String place_id;
    private String licence;
    private String osm_type;
    private long osm_id;
    private double lat;
    private double lon;
    private String classType;
    private String type;
    private int place_rank;
    private double importance;
    private String addresstype;
    private String name;
    private String display_name;
    private List<String> boundingbox;
}

