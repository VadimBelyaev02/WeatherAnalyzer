package com.senlatest.weatheranalyzer.client.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@AllArgsConstructor
@Builder
@Data
@NoArgsConstructor
public class ForecastDay {

    private String city_name;
    private String state_code;
    private String country_code;
    private double lat;
    private double lon;
    private String timezone;
    private List<Forecast> data;

    @Data
    public static class Forecast {
        private String ts;
        private String timestamp_local;
        private String timestamp_utc;
        private String datetime;
        private double snow;
        private int snow_depth;
        private float precip;
        private float temp;
        private float dewpt;
        private float max_temp;
        private float min_temp;
        private float app_max_temp;
        private float app_min_temp;
        private int rh;
        private int clouds;
        private InlineModel weather;
        private double slp;
        private int pres;
        private double uv;
        private String max_dhi;
        private double vis;
        private int pop;
        private double moon_phase;
        private long sunrise_ts;
        private long sunset_ts;
        private long moonrise_ts;
        private long moonset_ts;
        private String pod;
        private float wind_spd;
        private int wind_dir;
        private String wind_cdir;
        private String wind_cdir_full;
    }

    @Data
    public static class InlineModel {
        private String icon;
        private int code;
        private String description;
    }

}
