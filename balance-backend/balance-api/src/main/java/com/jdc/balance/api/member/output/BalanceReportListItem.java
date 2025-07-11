package com.jdc.balance.api.member.output;

import java.math.BigDecimal;

import com.jdc.balance.domain.embeddable.LedgerEntryPk;
import com.jdc.balance.domain.entity.Ledger.Type;
import com.jdc.balance.domain.entity.LedgerEntry;
import com.jdc.balance.domain.entity.LedgerEntry_;
import com.jdc.balance.domain.entity.Ledger_;

import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Root;

public record BalanceReportListItem(
		LedgerEntryPk id,
		String ledger, 
		String particular,
		BigDecimal debit,
		BigDecimal credit,
		BigDecimal balance) {

	public static void select(CriteriaBuilder cb, CriteriaQuery<BalanceReportListItem> cq, Root<LedgerEntry> root) {
		
		var ledger = root.get(LedgerEntry_.ledger);
		
		var debit = cb.selectCase()
				.when(
					cb.equal(ledger.get(Ledger_.type), Type.Debit), 
					root.get(LedgerEntry_.amount))
				.otherwise(BigDecimal.ZERO);
		
		var credit = cb.selectCase()
				.when(
					cb.equal(ledger.get(Ledger_.type), Type.Credit), 
					root.get(LedgerEntry_.amount))
				.otherwise(BigDecimal.ZERO);
		
		var balance = cb.selectCase()
				.when(
					cb.equal(ledger.get(Ledger_.type), Type.Credit), 
					cb.sum(root.get(LedgerEntry_.lastBalance), root.get(LedgerEntry_.amount)))
				.otherwise(cb.diff(root.get(LedgerEntry_.lastBalance), root.get(LedgerEntry_.amount)));
		

		cq.multiselect(
			root.get(LedgerEntry_.id),
			ledger.get(Ledger_.name),
			root.get(LedgerEntry_.particular),
			debit,
			credit,
			balance
		);
	}

}
