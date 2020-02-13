package com.sonalake.windsurfing.forecast;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;
@JsonIgnoreProperties(ignoreUnknown = true)
@Data
public class ForecastData {
	private Float temperatureMax;
	private Float temperatureMin;
	private Float windSpeed;
	private double uvIndex;
	private float precipProbability;
	private String summary;
	private double sunsetTime;
}
