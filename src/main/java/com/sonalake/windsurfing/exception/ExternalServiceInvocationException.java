package com.sonalake.windsurfing.exception;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@AllArgsConstructor
public class ExternalServiceInvocationException extends RuntimeException {

	private String serviceName;
	private int statusCode;

}
