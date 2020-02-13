package com.sonalake.windsurfing.forecast;

import com.sonalake.windsurfing.forecast.DailyForecast;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Data
class ForecastResponse {
	private DailyForecast daily;
}
