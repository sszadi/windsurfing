package com.sonalake.windsurfing.spot;

import com.sonalake.windsurfing.forecast.ForecastData;
import com.sonalake.windsurfing.spot.SpotResponse;
import org.apache.commons.lang3.Range;
import org.springframework.stereotype.Component;

import java.util.AbstractMap;
import java.util.Comparator;
import java.util.Map;
import java.util.function.Predicate;

@Component
class WindsurfingSpotCalculator {

	SpotResponse calculateBestSpot(Map<String, ForecastData> forecasts) {
		Map.Entry<String, ForecastData> result =
			forecasts.entrySet().stream()
				.filter(isSpotRelevant())
				.max(Comparator.comparing(this::pickTheBestValue))
				.orElse(new AbstractMap.SimpleEntry<>(null, null));

		return SpotResponse.builder().spotName(result.getKey()).forecast(result.getValue()).build();
	}

	private static final int WIND_SPEED_FACTOR = 3;
	private static final int MIN_TEMPERATURE = 0;
	private static final int MAX_TEMPERATURE = 35;
	private static final float MIN_WIND_SPEED = 5F;
	private static final float MAX_WIND_SPEED = 18F;

	private Predicate<Map.Entry<String, ForecastData>> isSpotRelevant() {
		Range<Float> windSpeedRange = Range.between(MIN_WIND_SPEED, MAX_WIND_SPEED);
		return f -> {
			ForecastData data = f.getValue();
			return windSpeedRange.contains(data.getWindSpeed())
				&& data.getTemperatureMin() >= MIN_TEMPERATURE && data.getTemperatureMax() <= MAX_TEMPERATURE;
		};
	}

	private float pickTheBestValue(Map.Entry<String, ForecastData> entry) {
		Float windSpeed = entry.getValue().getWindSpeed();
		Float minTemperature = entry.getValue().getTemperatureMin();
		Float maxTemperature = entry.getValue().getTemperatureMax();
		return windSpeed * WIND_SPEED_FACTOR + getMeanTemperature(minTemperature, maxTemperature);
	}

	private float getMeanTemperature(Float minTemperature, Float maxTemperature) {
		return (minTemperature + maxTemperature) / 2;
	}
}
