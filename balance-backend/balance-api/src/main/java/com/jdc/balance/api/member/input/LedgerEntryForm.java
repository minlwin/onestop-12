package com.jdc.balance.api.member.input;

import java.math.BigDecimal;
import java.util.List;

import com.jdc.balance.common.dto.LedgerEntryItem;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;

public record LedgerEntryForm(
		@NotBlank(message = "Please select ledger.")
		String code,
		@NotBlank(message = "Please enter particular message.")
		String particular,
		@NotEmpty(message = "Please enter at least one item.")
		List<@Valid LedgerEntryItem> items) {

	public BigDecimal amount() {
		return items.stream()
				.map(item -> item.unitPrice().multiply(BigDecimal.valueOf(item.quantity())))
				.reduce(BigDecimal.ZERO, BigDecimal::add);
	}

}
