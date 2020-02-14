package com.sonalake.windsurfing.forecast;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@JsonIgnoreProperties(ignoreUnknown = true)
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ForecastData {
	private Float temperatureMax;
	private Float temperatureMin;
	private Float windSpeed;
	private double uvIndex;
	private float precipProbability;
	private String summary;
	private double sunsetTime;
}
