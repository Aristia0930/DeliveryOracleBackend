package org.example.backend.gemini;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class WeatherResponse {


    private Coord coord;
    private List<Weather> weather;
    private Main main;

    @Getter
    @Setter
    public static class Coord{
        private double  lon;
        private double lat;
    }

    @Getter
    @Setter
    public static class Weather{
        private int id;
        private String main;
        private String description;
        private String icon;
    }

    @Getter
    @Setter
    public static class Main {
        private double temp;
        @JsonProperty("feels_like")
        private double feelsLike;
        @JsonProperty("temp_min")
        private double tempMin;
        @JsonProperty("temp_max")
        private double tempMax;
        private int pressure;
        private int humidity;
        @JsonProperty("sea_level")
        private int seaLevel;
        @JsonProperty("grnd_level")
        private int grndLevel;

    }



}