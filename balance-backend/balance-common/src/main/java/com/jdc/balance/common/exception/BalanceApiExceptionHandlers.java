package com.jdc.balance.common.exception;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.jdc.balance.common.dto.ErrorMessage;
import com.jdc.balance.common.dto.ErrorResponse;

@RestControllerAdvice
public class BalanceApiExceptionHandlers {

	/**
	 * Exception Handler for Bean Validation
	 * 
	 * @param e
	 */
	@ExceptionHandler
	@ResponseStatus(code = HttpStatus.BAD_REQUEST)
	ErrorResponse handle(MethodArgumentNotValidException e) {
		var messages = e.getBindingResult().getFieldErrors()
			.stream()
			.map(a -> new ErrorMessage(a.getField(), a.getDefaultMessage()))
			.toList();
		return new ErrorResponse(messages);
	}
	
	
	/**
	 * Exception Handler for Business Exception
	 * 
	 * @param e
	 * @return
	 */
	@ExceptionHandler
	@ResponseStatus(code = HttpStatus.NOT_ACCEPTABLE)
	ErrorResponse handle(ApiBusinessException e) {
		return new ErrorResponse(List.of(e.getFiledError()));
	}
}
