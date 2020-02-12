package com.sonalake.windsurfing.weather;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpStatusCodeException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Map;
import java.util.Objects;

@RequiredArgsConstructor
@Service
@Slf4j
class ForecastRestClient {

	DailyForecast getForecast(double latitude, double longitude, LocalDate date) {
		try {
			HttpHeaders headers = new HttpHeaders();
			headers.setContentType(org.springframework.http.MediaType.APPLICATION_JSON);
			HttpEntity entity = new HttpEntity<>(null, headers);
			ResponseEntity<ForecastResponse> response = restTemplate.exchange(getUriBuilder().buildAndExpand(buildParams(latitude, longitude, date)).toUri(), HttpMethod.GET, entity, ForecastResponse.class);

			ForecastResponse forecastResponse = response.getBody();
			if (response.getStatusCode() == HttpStatus.OK && Objects.nonNull(forecastResponse)) {
				log.debug("Forecast response for latitude {}, longitude {}, date {} = {}", latitude, longitude, date, forecastResponse);
				return forecastResponse.getDaily();
			}

			return new DailyForecast();
		} catch (HttpStatusCodeException httpStatusException) {
			//todo obsluga bledow
			log.info("Darksky only return HTTPStatus code");
			throw new IllegalArgumentException();
//			throw new ExternalServiceInvocationException(FORECAST_IO_SERVICE_NAME, httpStatusEx.getRawStatusCode());
		} catch (Exception ex) {
			//todo This is thrown when can't even get to API (e.g. network error)!
			log.info("nanan", ex);
			throw new IllegalArgumentException();
		}
	}

	private static final String DATE = "date";

	private UriComponentsBuilder getUriBuilder() {
		return UriComponentsBuilder.fromHttpUrl(apiUrl)
			.path(apiKey)
			.path("{latitude},{longitude},{date}");
	}

	private static final String LATITUDE = "latitude";
	private static final String LONGITUDE = "longitude";

	private Map<String, Object> buildParams(double latitude, double longitude, LocalDate date) {
		return Map.of(LATITUDE, latitude, LONGITUDE, longitude, DATE, date.atStartOfDay(ZoneId.systemDefault()).toEpochSecond());
	}

	@Value("${darksky.url}")
	private String apiUrl;
	@Value("${darksky.key}")
	private String apiKey;
	private final RestTemplate restTemplate;
}
