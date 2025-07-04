package com.jdc.balance.common.utils;

import java.util.Optional;

import com.jdc.balance.common.exception.ApiBusinessException;

public class EntityOperations {

	public static <T, ID> T safeCall(Optional<T> optional, String data, ID id) {
		return optional.orElseThrow(() -> 
			new ApiBusinessException("There is no %s with id : %s.".formatted(data, id)));
	}
	
	public static String likeString(String value) {
		return value.toLowerCase().concat("%");
	}
}
