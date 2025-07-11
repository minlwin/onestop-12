package com.jdc.balance.common.dto;

import java.math.BigDecimal;

public record LedgerEntryItem(
		String item,
		BigDecimal unitPrice,
		int quantity,
		String remark) {

}
