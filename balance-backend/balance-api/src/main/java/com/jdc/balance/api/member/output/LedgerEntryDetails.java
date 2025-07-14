package com.jdc.balance.api.member.output;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.function.Function;

import com.jdc.balance.common.dto.LedgerEntryItem;
import com.jdc.balance.domain.embeddable.LedgerEntryPk;
import com.jdc.balance.domain.entity.Ledger.Type;
import com.jdc.balance.domain.entity.LedgerEntry;

public record LedgerEntryDetails(
		LedgerEntryPk id,
		String ledgerName,
		Type type,
		LocalDate issueAt,
		String particular,
		BigDecimal lastBalance,
		BigDecimal amount,
		List<LedgerEntryItem> items) {

	public static LedgerEntryDetails from(LedgerEntry entity, 
			Function<String, List<LedgerEntryItem>> itemMapper) {
		return new LedgerEntryDetails(
				entity.getId(),
				entity.getLedger().getName(),
				entity.getLedger().getType(),
				entity.getIssueAt(),
				entity.getParticular(),
				entity.getLastBalance(),
				entity.getAmount(),
				itemMapper.apply(entity.getItems()));
	}
}
