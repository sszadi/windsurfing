package com.sonalake.windsurfing;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;

@SpringBootApplication
public class WindsurfingApplication {

	public static void main(String[] args) {
		SpringApplication.run(WindsurfingApplication.class, args);
	}

	@Bean
	public RestTemplate restTemplate(RestTemplateBuilder builder) {
		return builder.build();
	}
}


//https://api.darksky.net/forecast/aa1321e9abac62c00bd7c75d5d5e83f8/37.8267,-122.4233
