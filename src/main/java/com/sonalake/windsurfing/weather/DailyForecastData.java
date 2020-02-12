package com.sonalake.windsurfing.weather;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;

import java.time.LocalDateTime;
//@JsonIgnoreProperties(ignoreUnknown = true)
class DailyForecastData {
	private double temperatureMin;
	private double temperatureMax;

	@JsonDeserialize(using = LocalDateTimeDeserializer.class)
	private LocalDateTime temperatureMinTime;

	@JsonDeserialize(using = LocalDateTimeDeserializer.class)
	private LocalDateTime temperatureMaxTime;
}
