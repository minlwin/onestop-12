package com.jdc.balance.common.exception;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.AccountExpiredException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.InsufficientAuthenticationException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
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
	
	/**
	 * Authentication Exception Handler
	 * 
	 * @param e
	 * @return
	 */
	@ExceptionHandler
	@ResponseStatus(code = HttpStatus.UNAUTHORIZED)
	ErrorResponse handle(AuthenticationException e) {
		return new ErrorResponse(List.of(ErrorMessage.withMessage(
			switch(e) {
			case UsernameNotFoundException une -> "Please check your login id.";
			case BadCredentialsException bce -> "Please check your password.";
			case AccountExpiredException ace -> "Your account is expired. Please renew your subscription.";
			case InsufficientAuthenticationException ise -> "You need to login for this operation.";
			case JwtTokenInvalidationException jte -> "Your token is invalid. Please login again.";
			default -> "Authentication failure. Please check your login information.";
			}
		)));
	}
	
	@ExceptionHandler
	@ResponseStatus(code = HttpStatus.REQUEST_TIMEOUT)
	ErrorResponse handle(JwtTokenExpirationException e) {
		return new ErrorResponse(List.of(ErrorMessage.withMessage(e.getMessage())));
	}
	
	@ExceptionHandler
	@ResponseStatus(code = HttpStatus.FORBIDDEN)
	ErrorResponse handle(AccessDeniedException e) {
		return new ErrorResponse(List.of(ErrorMessage.withMessage("You have no permission for this operation.")));
	}
	
	@ExceptionHandler
	@ResponseStatus(code = HttpStatus.INTERNAL_SERVER_ERROR)
	ErrorResponse handle(Exception e) {
		e.printStackTrace();
		return new ErrorResponse(List.of(ErrorMessage.withMessage(e.getMessage())));
	}
}
