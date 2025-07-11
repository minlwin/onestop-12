package com.jdc.balance.common.dto;

public record ModificationResult<ID>(
		boolean success,
		ID id, 
		String message) {

	public static<T> ModificationResult<T> success(T id) {
		return new ModificationResult<T>(true, id, null);
	}

	public static<T> ModificationResult<T> fails(String message) {
		return new ModificationResult<T>(false, null, message);
	}
}
