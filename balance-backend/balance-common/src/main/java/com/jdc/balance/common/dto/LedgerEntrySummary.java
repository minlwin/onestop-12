package com.jdc.balance.common.dto;

import java.math.BigDecimal;

import com.jdc.balance.domain.entity.Ledger;

public record LedgerEntrySummary(
		Ledger ledger,
		BigDecimal total) {

}
