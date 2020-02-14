package com.sonalake.windsurfing.spot;

import com.sonalake.windsurfing.forecast.ForecastData;
import com.sonalake.windsurfing.forecast.ForecastRestClient;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@Slf4j
@RequiredArgsConstructor
class SpotService {

	SpotResponse findBestSpotToWindsurfing(LocalDate date) {
		List<Spot> spotsToCheckUp = spotRepository.findAll();
		log.debug("Find best spot to windsurfing from: {}", spotsToCheckUp);
		Map<String, ForecastData> forecasts = spotsToCheckUp.stream().collect(Collectors.toMap
			(Spot::getName, s -> forecastDataRetriever.getForecast(s.getLatitude(), s.getLongitude(), date)));
		return bestSpotCalculator.calculateBestSpot(forecasts);
	}

	private final ForecastRestClient forecastDataRetriever;
	private final WindsurfingSpotCalculator bestSpotCalculator;
	private final SpotRepository spotRepository;
}
//todo pola na gorze klasy
