package com.sonalake.windsurfing.spot;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;

@RestController
@RequestMapping("/api/windsurfing")
@RequiredArgsConstructor
@Slf4j
class SpotRestController {

	private final SpotService spotService;

	@GetMapping("spot")
	public SpotResponse findBestPlaceToWindsurfing(
		@RequestParam(value = "date") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date) {
		log.info("Find best spot to windsurfing on {}", date);
		SpotResponse spotResponse = spotService.findBestSpotToWindsurfing(date);
		log.info("Best spot to windsurfing on {}: {}", date, spotResponse);
		return spotResponse;

	}
}
