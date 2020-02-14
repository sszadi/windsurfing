package com.sonalake.windsurfing.spot;

import com.sonalake.windsurfing.forecast.ForecastData;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.rules.SpringClassRule;
import org.springframework.test.context.junit4.rules.SpringMethodRule;

import java.util.Arrays;
import java.util.Collection;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;


@RunWith(Parameterized.class)
@SpringBootTest
public class WindsurfingSpotCalculatorTest {


	@Rule
	public final SpringMethodRule methodRule = new SpringMethodRule();

	@Parameterized.Parameter
	public Map<String, ForecastData> forecastMap;

	@Parameterized.Parameter(1)
	public SpotResponse expectedSpotResponse;

	@Autowired
	private WindsurfingSpotCalculator windsurfingSpotCalculator;

	@Parameterized.Parameters
	public static Collection input() {
		return Arrays.asList(new Object[][]{
			{
				Map.of("Fuerteventura", ForecastData.builder().windSpeed(4F).temperatureMin(5F).temperatureMax(35F).build(),
						"Red Sea", ForecastData.builder().windSpeed(5F).temperatureMin(4F).temperatureMax(35F).build()),
				SpotResponse.builder().spotName(null).forecast(null).build(),
			},
			{
				Map.of("Caribbean", ForecastData.builder().windSpeed(18F).temperatureMin(5F).temperatureMax(36F).build(),
						"Sardinia", ForecastData.builder().windSpeed(7F).temperatureMin(15F).temperatureMax(35F).build()),
				SpotResponse.builder().spotName("Sardinia").forecast(ForecastData.builder().windSpeed(7F).temperatureMin(15F).temperatureMax(35F).build()).build(),
			},
			{
				Map.of("Phillipines", ForecastData.builder().windSpeed(4F).temperatureMin(4F).temperatureMax(36F).build(),
						"Morocco", ForecastData.builder().windSpeed(6F).temperatureMin(15F).temperatureMax(35F).build(),
						"Venezuela", ForecastData.builder().windSpeed(18F).temperatureMin(15F).temperatureMax(35F).build()),
				SpotResponse.builder().spotName("Venezuela").forecast(ForecastData.builder().windSpeed(18F).temperatureMin(15F).temperatureMax(35F).build()).build(),
			}
		});
	}

	@ClassRule
	public static final SpringClassRule CLASS_RULE = new SpringClassRule();

	@Test
	public void shouldCalculateBestSpot() {
		// given

		// when
		SpotResponse spotResponse = windsurfingSpotCalculator.calculateBestSpot(forecastMap);

		// then
		assertEquals(expectedSpotResponse, spotResponse);
	}

}
