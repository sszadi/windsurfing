package com.sonalake.windsurfing.weather;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpStatusCodeException;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDate;
import java.util.Map;

@RequiredArgsConstructor
@Service
@Slf4j
class ForecastDataRetriever {

	ForecastResponse getForecast(double latitude, double longitude, LocalDate date) {
		try {
			return restTemplate.getForObject(apiUrl, ForecastResponse.class, buildParams(latitude, longitude, date));
		} catch (HttpStatusCodeException httpStatusException) {
			//todo obsluga bledow
			log.info("Darksky only return HTTPStatus code");
			throw new IllegalArgumentException();
//			throw new ExternalServiceInvocationException(FORECAST_IO_SERVICE_NAME, httpStatusEx.getRawStatusCode());
		} catch (Exception ex) {
			//todo This is thrown when can't even get to API (e.g. network error)!
			log.info("nanan");
			throw new IllegalArgumentException();
		}
	}

	private static final String KEY = "key";
	private static final String LATITUDE = "latitude";
	private static final String LONGITUDE = "longitude";
	private static final String TIME = "time";

	private Map<String, Object> buildParams(double latitude, double longitude, LocalDate date) {
		return Map.of(KEY, apiKey, LATITUDE, latitude, LONGITUDE, longitude, TIME, date);
	}

	@Value("darksky.url")
	private String apiUrl;
	@Value("darksky.key")
	private String apiKey;
	private final RestTemplate restTemplate;
}
