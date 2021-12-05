package com.up42.featureService.controller;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;
import java.util.NoSuchElementException;

import javax.validation.ConstraintViolationException;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.ServletWebRequest;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import com.up42.featureService.exception.ImageValidationException;

import lombok.extern.slf4j.Slf4j;

@ControllerAdvice
@Slf4j
public class FeatureRestControllerAdvisor  extends ResponseEntityExceptionHandler {
	
	@Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex, 
    		HttpHeaders headers, HttpStatus status, WebRequest request) {
		 Map<String, String> body = new HashMap<>();
		    ex.getBindingResult().getAllErrors().forEach((error) -> {
		        String fieldName = ((FieldError) error).getField();
		        String errorMessage = error.getDefaultMessage();
		        body.put(fieldName, errorMessage);
		    });
		    addMessageAndTimestampToResponseBody(body, "Request parameters are not valid.");
		    logErrorWithRequestPath(request.getContextPath().toString(), ex);
		    return new ResponseEntity<>(body, HttpStatus.BAD_REQUEST);
	}
	
	
	@ExceptionHandler(ConstraintViolationException.class)
	public ResponseEntity<Object> handleValidationExceptions(
			ConstraintViolationException ex, ServletWebRequest servletWebRequest) {
	    Map<String, String> body = new HashMap<>();
	    addMessageAndTimestampToResponseBody(body, "Request parameters are not valid.");
	    logErrorWithRequestPath(servletWebRequest.getRequest().getRequestURI().toString(), ex);
	    return new ResponseEntity<>(body, HttpStatus.BAD_REQUEST);
	}
	
	@ExceptionHandler(NoSuchElementException.class)
	public ResponseEntity<Object> handleNoSuchElementExceptions(
			NoSuchElementException ex, ServletWebRequest servletWebRequest) {
	    Map<String, String> body = new HashMap<>();
	    addMessageAndTimestampToResponseBody(body, "Requested feature not found.");
	    logErrorWithRequestPath(servletWebRequest.getRequest().getRequestURI().toString(), ex);
	    return new ResponseEntity<>(body, HttpStatus.NOT_FOUND);
	    //return new ResponseEntity<>(body, HttpStatus.NO_CONTENT);
	}
	
	
	@ExceptionHandler(ImageValidationException.class)
	public ResponseEntity<Object> handleImageValidationException(
			ImageValidationException ex, ServletWebRequest servletWebRequest) {
		Map<String, String> body = new HashMap<>();
		addMessageAndTimestampToResponseBody(body, ex.getMessage());
		logErrorWithRequestPath(servletWebRequest.getRequest().getRequestURI().toString(), ex);
		return new ResponseEntity<>(body, HttpStatus.UNPROCESSABLE_ENTITY);
	}
	
	@ExceptionHandler(Exception.class)
	public ResponseEntity<Object>  handleOtherExceptions(Exception ex, ServletWebRequest servletWebRequest) {
		Map<String, String> body = new HashMap<>();
		addMessageAndTimestampToResponseBody(body, ex.getMessage());
		logErrorWithRequestPath(servletWebRequest.getRequest().getRequestURI().toString(), ex);
		return new ResponseEntity<>(body, HttpStatus.INTERNAL_SERVER_ERROR);
	}
	
	private void logErrorWithRequestPath(String requestPath, Exception e) {
		log.error(
				"Error occured at endpoint: {}", 
				requestPath,
				e
				);
	}
	
	private void addMessageAndTimestampToResponseBody(Map<String, String> body, String message) {
        body.put("timestamp", LocalDateTime.now().toString());
		body.put("message", message);
	}
	
}
