package com.sonalake.windsurfing.exception;

public class ExternalServiceException extends RuntimeException {
	public ExternalServiceException(String serviceName, Exception ex) {
		super(serviceName, ex);
	}
}
