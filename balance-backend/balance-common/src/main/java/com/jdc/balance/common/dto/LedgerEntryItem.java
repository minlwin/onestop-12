package com.jdc.balance.common.dto;

import java.math.BigDecimal;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record LedgerEntryItem(
		@NotBlank(message = "Please enter item.")
		String item,
		@NotNull(message = "Please enter unit price.")
		BigDecimal unitPrice,
		@NotNull(message = "Please enter quantity.")
		Integer quantity,
		String remark) {

}
