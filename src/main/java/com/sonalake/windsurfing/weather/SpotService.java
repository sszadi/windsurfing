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
		List<DailyForecast> forecasts = new ArrayList<>();
		for (Spot spot : spotsToCheckUp) {
			//todo wielowątkowo?
			DailyForecast forecast = forecastDataRetriever.getForecast(spot.getLatitude(), spot.getLongitude(), date);
			forecasts.add(forecast);
		}
		log.info("forecast {}", forecasts.get(0));
		return SpotResponse.builder().build();
	}

	private final ForecastRestClient forecastDataRetriever;
	private final SpotRepository spotRepository;
}

/**
 * 4. Kryteria wyboru miejsca:
 * Jeśli prędkość wiatru nie zawiera się w przedziale <5; 18> (m/s), a temperatura w przedziale <5; 35> (°C) to dana lokalizacja nie nadaje się do surfingu. Natomiast jeśli są w ustalonych przedziałach, wtedy wygrywa miejsce, które ma wyższą wartość obliczoną ze wzoru:
 * v * 3 + (tempLow + tempHigh)/2
 *
 * gdzie v, to prędkość wiatru w m/s w danym dniu, a tempLow i tempHigh, to odpowiednio najniższa i najwyższa temperatura prognozowana danego dnia w stopniach Celsjuszach - potrzebne parametry możesz pobrać z klucza daily w odpowiedzi Darksky.
 */
