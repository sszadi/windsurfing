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
/**
 * 4. Kryteria wyboru miejsca:
 * Jeśli prędkość wiatru nie zawiera się w przedziale <5; 18> (m/s), a temperatura w przedziale <5; 35> (°C)
 * to dana lokalizacja nie nadaje się do surfingu.
 * Natomiast jeśli są w ustalonych przedziałach, wtedy wygrywa miejsce, które ma wyższą wartość obliczoną ze wzoru:
 * v * 3 + (tempLow + tempHigh)/2
 * gdzie v, to prędkość wiatru w m/s w danym dniu,
 * a tempLow i tempHigh, to odpowiednio najniższa i najwyższa temperatura prognozowana danego dnia w stopniach Celsjuszach
 */
