package com.sonalake.windsurfing;


import com.sonalake.windsurfing.exception.ExternalServiceException;
import com.sonalake.windsurfing.exception.ExternalServiceInvocationException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@EnableWebMvc
@ControllerAdvice
class RestExceptionHandler extends ResponseEntityExceptionHandler {

	@ExceptionHandler({ExternalServiceInvocationException.class, ExternalServiceException.class})
	protected ResponseEntity<Object> handleEntityNotFound(
		Exception ex, WebRequest request) {
		return handleExceptionInternal(ex, null, new HttpHeaders(), HttpStatus.SERVICE_UNAVAILABLE, request);
	}
}
