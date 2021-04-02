package com.financial.controller.exception;

import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.List;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import com.financial.service.exception.NotFoundException;

@ControllerAdvice
public class GlobalException extends ResponseEntityExceptionHandler{
	
	@ExceptionHandler(NotFoundException.class)
	public ResponseEntity<ApiError> handlerNotFoundException(NotFoundException ex){
		ApiError error = new ApiError(HttpStatus.NOT_FOUND.value(), ex.getMessage(), OffsetDateTime.now());
		return ResponseEntity.status(HttpStatus.NOT_FOUND).body(error);
	}
	
	@Override
	protected ResponseEntity<Object> handleMethodArgumentNotValid(
			MethodArgumentNotValidException ex,
			HttpHeaders headers, HttpStatus status, WebRequest request) {
		
		List<String> errors = new ArrayList<>();
		
		ex.getBindingResult().getAllErrors().forEach(error -> {errors.add(error.getDefaultMessage());
		});
		
		String defaultMessage = "Campo(s) inválido(s).";
		ApiErrorList error = new ApiErrorList(HttpStatus.BAD_REQUEST.value(), defaultMessage, OffsetDateTime.now(), errors);
		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error);
	}

}
