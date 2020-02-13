package com.sonalake.windsurfing.spot;

import com.sonalake.windsurfing.forecast.ForecastData;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
class SpotResponse {

	private String spotName;
	private ForecastData forecast;

}
