package com.sonalake.windsurfing.spot;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sonalake.windsurfing.exception.ExternalServiceException;
import com.sonalake.windsurfing.exception.ExternalServiceInvocationException;
import com.sonalake.windsurfing.forecast.ForecastData;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@WebMvcTest(SpotRestController.class)
public class SpotRestControllerTest {


	public static final String DATE = "2020-04-23";

	@Test
	public void whenGetStationStateThenReturnJsonArray()
		throws Exception {
		// given
		SpotResponse expectedSpotResponse = SpotResponse.builder()
			.spotName(SPOT_NAME)
			.forecast(
				ForecastData.builder()
					.temperatureMax(34F)
					.temperatureMin(26F)
					.build()
			).build();
		given(service.findBestSpotToWindsurfing(getLocalDate())).willReturn(expectedSpotResponse);

		// when
		MvcResult result = mvc.perform(get("/api/windsurfing/spot")
			.param("date", DATE)
			.contentType(MediaType.APPLICATION_JSON))
			.andExpect(status().isOk())
			.andReturn();

		//  then
		ObjectMapper mapper = new ObjectMapper();
		SpotResponse receivedSpotResult = mapper.readValue(result.getResponse().getContentAsString(), SpotResponse.class);
		assertThat(receivedSpotResult).isEqualTo(expectedSpotResponse);
	}

	@Test
	public void shouldReturnServiceUnavailableCodeForServiceInvocationException() throws Exception {
		when(service.findBestSpotToWindsurfing(any())).thenThrow(new ExternalServiceInvocationException("test", 500));

		mvc.perform(get("/api/windsurfing/spot")
			.param("date", DATE)
			.contentType(MediaType.APPLICATION_JSON))
			.andExpect(status().isServiceUnavailable());
	}

	@Test
	public void shouldReturnServiceUnavailableCodeForServiceException() throws Exception {
		when(service.findBestSpotToWindsurfing(any())).thenThrow(new ExternalServiceException("test", new Exception()));

		mvc.perform(get("/api/windsurfing/spot")
			.param("date", DATE)
			.contentType(MediaType.APPLICATION_JSON))
			.andExpect(status().isServiceUnavailable());
	}

	private static final String SPOT_NAME = "Hawaii";

	private LocalDate getLocalDate() {
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
		return LocalDate.parse(SpotRestControllerTest.DATE, formatter);
	}
	@Autowired
	private MockMvc mvc;
	@MockBean
	private SpotService service;
}
