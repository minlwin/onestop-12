package com.jdc.balance.common.dto;

import java.util.List;

public record ErrorResponse(
		List<ErrorMessage> messages) {

}
