package com.sonalake.windsurfing.forecast;

import com.sonalake.windsurfing.exception.ExternalServiceException;
import com.sonalake.windsurfing.exception.ExternalServiceInvocationException;
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
import java.util.List;
import java.util.Map;
import java.util.Objects;

@RequiredArgsConstructor
@Service
@Slf4j
public class ForecastRestClient {

	public ForecastData getForecast(double latitude, double longitude, LocalDate date) {
		try {
			HttpHeaders headers = new HttpHeaders();
			headers.setContentType(org.springframework.http.MediaType.APPLICATION_JSON);
			HttpEntity entity = new HttpEntity<>(null, headers);
			ResponseEntity<ForecastResponse> response = restTemplate.exchange(getUriBuilder().buildAndExpand(buildParams(latitude, longitude, date)).toUri(), HttpMethod.GET, entity, ForecastResponse.class);

			ForecastResponse forecastResponse = response.getBody();
			if (response.getStatusCode() == HttpStatus.OK && Objects.nonNull(forecastResponse) && Objects.nonNull(forecastResponse.getDaily())) {
				List<ForecastData> forecastData = forecastResponse.getDaily().getData();
				log.info("Forecast response for latitude {}, longitude {}, date {} = {}", latitude, longitude, date, forecastData);
				return forecastData
					.stream().findFirst().orElseThrow(IllegalAccessError::new);
			}

			return new ForecastData();
		} catch (HttpStatusCodeException httpStatusException) {
			log.error("Service invocation failed", httpStatusException);
			throw new ExternalServiceInvocationException(SERVICE_NAME, httpStatusException.getRawStatusCode());
		} catch (Exception ex) {
			log.error("External service exception", ex);
			throw new ExternalServiceException(SERVICE_NAME, ex);
		}
	}

	private static final String DATE = "date";
	private static final String SERVICE_NAME = "FORECAST";

	private UriComponentsBuilder getUriBuilder() {
		return UriComponentsBuilder.fromHttpUrl(apiUrl)
			.path(apiKey)
			.path("{latitude},{longitude},{date}")
			.queryParam("units", "si");
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
