package com.senlatest.weatheranalyzer.model;

import java.util.List;

public class WeatherData {
    private String count;
    private List<WeatherInfo> data;

    // Геттеры и сеттеры для count и data

    public static class WeatherInfo {
        private String city_name;
        private String state_code;
        private String country_code;
        private String timezone;
        private double lat;
        private double lon;
        private String station;
        private List<String> sources;
        private int vis;
        private int rh;
        private int dewpt;
        private int wind_dir;
        private String wind_cdir;
        private String wind_cdir_full;
        private double wind_speed;
        private int gust;
        private double temp;
        private double app_temp;
        private int clouds;
        private Weather weather;
        private String datetime;
        private String ob_time;
        private long ts;
        private String sunrise;
        private String sunset;
        private double slp;
        private int pres;
        private int aqi;
        private double uv;
        private double solar_rad;
        private double ghi;
        private double dni;
        private double dhi;
        private int elev_angle;
        private int hour_angle;
        private String pod;
        private int precip;
        private int snow;

        // Геттеры и сеттеры для всех полей
    }

    public static class Weather {
        private String icon;
        private int code;
        private String description;

        // Геттеры и сеттеры для всех полей
    }
}

