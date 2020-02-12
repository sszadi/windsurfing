package com.sonalake.windsurfing.weather;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
class ForecastResponse {
	private DailyForecast daily;
}
