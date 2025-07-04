package com.jdc.balance.common.exception;

import org.springframework.security.core.AuthenticationException;

public class JwtTokenInvalidationException extends AuthenticationException{

	private static final long serialVersionUID = 1L;

	public JwtTokenInvalidationException(String msg) {
		super(msg);
	}

	public JwtTokenInvalidationException(String msg, Throwable cause) {
		super(msg, cause);
	}
	
}
