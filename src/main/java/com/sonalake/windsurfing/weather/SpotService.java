package com.sonalake.windsurfing.weather;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
class SpotService {
	SpotResponse findBestSpotToWindsurfing(LocalDate date) {
		List<Spot> spotsToCheckUp = spotRepository.findAll();
		List<ForecastResponse> forecasts = new ArrayList<>();
		for (Spot spot : spotsToCheckUp) {
			//todo wielowątkowo?
			ForecastResponse forecast = forecastDataRetriever.getForecast(spot.getLatitude(), spot.getLongitude(), date);
			forecasts.add(forecast);
		}
		log.info("forecast {}", forecasts);
		return null;
	}

	private final ForecastDataRetriever forecastDataRetriever;
	private final SpotRepository spotRepository;
}
