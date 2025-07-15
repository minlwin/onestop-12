package com.jdc.balance.api.member.output;

import java.math.BigDecimal;

import com.jdc.balance.domain.embeddable.LedgerEntryPk_;
import com.jdc.balance.domain.embeddable.LedgerPk;
import com.jdc.balance.domain.entity.Ledger;
import com.jdc.balance.domain.entity.Ledger.Type;
import com.jdc.balance.domain.entity.LedgerEntry_;
import com.jdc.balance.domain.entity.Ledger_;

import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.JoinType;
import jakarta.persistence.criteria.Root;

public record LedgerListItem(
		LedgerPk id,
		Type type,
		String name,
		String description,
		long entries,
		BigDecimal total) {

	public static void select(CriteriaBuilder cb, CriteriaQuery<LedgerListItem> cq, Root<Ledger> root) {
		
		var entries = root.join(Ledger_.entries, JoinType.LEFT);
		
		cq.multiselect(
				root.get(Ledger_.id),
				root.get(Ledger_.type),
				root.get(Ledger_.name),
				root.get(Ledger_.description),
				cb.count(entries.get(LedgerEntry_.id).get(LedgerEntryPk_.code)),
				cb.sum(entries.get(LedgerEntry_.amount)));
		
		cq.groupBy(
				root.get(Ledger_.id),
				root.get(Ledger_.type),
				root.get(Ledger_.name),
				root.get(Ledger_.description));
	}

}
