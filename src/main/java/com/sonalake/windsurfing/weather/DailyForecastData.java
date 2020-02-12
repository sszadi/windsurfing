package com.sonalake.windsurfing.weather;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;
@JsonIgnoreProperties(ignoreUnknown = true)
@Data
class DailyForecastData {
	private double temperatureMax;
	private double temperatureMin;
	private double temperature;
	private double windSpeed;
	private double uvIndex;
	private float precipProbability;
	private String summary;
	private double sunsetTime;
}
