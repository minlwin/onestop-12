package com.jdc.balance.api.member.output;

import java.math.BigDecimal;

import com.jdc.balance.domain.embeddable.LedgerPk;
import com.jdc.balance.domain.entity.Ledger.Type;

public record LedgerListItem(
		LedgerPk id,
		Type type,
		String name,
		String description,
		int entries,
		BigDecimal total) {

}
