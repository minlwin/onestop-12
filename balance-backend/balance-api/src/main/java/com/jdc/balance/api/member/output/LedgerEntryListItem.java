package com.jdc.balance.api.member.output;

import java.math.BigDecimal;
import java.time.LocalDate;

import com.jdc.balance.domain.embeddable.LedgerEntryPk;
import com.jdc.balance.domain.entity.Ledger.Type;
import com.jdc.balance.domain.entity.LedgerEntry;
import com.jdc.balance.domain.entity.LedgerEntry_;
import com.jdc.balance.domain.entity.Ledger_;

import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Root;

public record LedgerEntryListItem(
		LedgerEntryPk id,
		String ledgerName,
		Type type,
		LocalDate issueAt,
		String particular,
		BigDecimal lastBalance,
		BigDecimal amount) {

	public BigDecimal getBalance() {
		return  type == Type.Credit ? lastBalance.add(amount) : lastBalance.subtract(amount);
	}
	
	public static void select(CriteriaQuery<LedgerEntryListItem> cq, Root<LedgerEntry> root) {
		cq.multiselect(
				root.get(LedgerEntry_.id),
				root.get(LedgerEntry_.ledger).get(Ledger_.name),
				root.get(LedgerEntry_.ledger).get(Ledger_.type),
				root.get(LedgerEntry_.issueAt),
				root.get(LedgerEntry_.particular),
				root.get(LedgerEntry_.lastBalance),
				root.get(LedgerEntry_.amount));
	}

}
