package com.jdc.balance.common.exception;

import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.jdc.balance.common.dto.ErrorMessage;
import com.jdc.balance.common.dto.ErrorResponse;

@RestControllerAdvice
public class BalanceApiExceptionHandlers {

	/**
	 * Exception Handler for Bean Validation
	 * @param e
	 */
	@ExceptionHandler
	ErrorResponse handle(MethodArgumentNotValidException e) {
		var messages = e.getBindingResult().getFieldErrors()
			.stream()
			.map(a -> new ErrorMessage(a.getField(), a.getDefaultMessage()))
			.toList();
		return new ErrorResponse(messages);
	}
}
