package com.jdc.balance.common.exception;

import com.jdc.balance.common.dto.ErrorMessage;

public class ApiBusinessException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	private ErrorMessage filedError;

	public ApiBusinessException(ErrorMessage filedError) {
		super();
		this.filedError = filedError;
	}
	
	public ApiBusinessException(String message) {
		super(message);
		this.filedError = ErrorMessage.withMessage(message);
	}
	
	public ErrorMessage getFiledError() {
		return filedError;
	}
	
}
