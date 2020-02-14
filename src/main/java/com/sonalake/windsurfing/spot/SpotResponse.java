package com.sonalake.windsurfing.spot;

import com.sonalake.windsurfing.forecast.ForecastData;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
class SpotResponse {

	private String spotName;
	private ForecastData forecast;

}
