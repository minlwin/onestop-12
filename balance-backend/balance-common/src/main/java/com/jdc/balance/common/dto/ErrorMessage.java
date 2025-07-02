package com.jdc.balance.common.dto;

public record ErrorMessage(
		String field,
		String message) {

	public static ErrorMessage withMessage(String message) {
		return new ErrorMessage(null, message);
	}
}
