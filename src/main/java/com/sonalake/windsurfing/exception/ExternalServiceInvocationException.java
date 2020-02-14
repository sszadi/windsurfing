package com.sonalake.windsurfing.exception;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public class ExternalServiceInvocationException extends RuntimeException {

	private String serviceName;
	private int statusCode;

}
