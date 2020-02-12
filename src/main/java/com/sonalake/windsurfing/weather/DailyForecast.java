package com.sonalake.windsurfing.weather;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

import java.util.List;
@JsonIgnoreProperties(ignoreUnknown = true)
@Data
class DailyForecast{
	private List<DailyForecastData> data;
}
