package com.sonalake.windsurfing.forecast;

import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Data
class ForecastResponse {
	private DailyForecast daily;
}
