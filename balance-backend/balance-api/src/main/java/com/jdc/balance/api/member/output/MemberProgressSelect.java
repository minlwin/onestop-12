package com.jdc.balance.api.member.output;

import java.math.BigDecimal;

import com.jdc.balance.domain.embeddable.LedgerEntryPk_;
import com.jdc.balance.domain.entity.Ledger.Type;
import com.jdc.balance.domain.entity.LedgerEntry;
import com.jdc.balance.domain.entity.LedgerEntry_;
import com.jdc.balance.domain.entity.Ledger_;

import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Root;

public record MemberProgressSelect(
		long memberId,
		BigDecimal debit,
		BigDecimal credit) {

	public static void select(CriteriaBuilder cb, CriteriaQuery<MemberProgressSelect> cq, Root<LedgerEntry> root) {
		
		var debitSum = cb.sum(cb.<BigDecimal>selectCase()
				.when(cb.equal(root.get(LedgerEntry_.ledger).get(Ledger_.type), Type.Debit), root.get(LedgerEntry_.amount))
				.otherwise(BigDecimal.ZERO));
		
		var creditSum = cb.sum(cb.<BigDecimal>selectCase()
				.when(cb.equal(root.get(LedgerEntry_.ledger).get(Ledger_.type), Type.Credit), root.get(LedgerEntry_.amount))
				.otherwise(BigDecimal.ZERO));

		cq.multiselect(
			root.get(LedgerEntry_.id).get(LedgerEntryPk_.memberId),
			debitSum,
			creditSum
		);
		
		cq.groupBy(root.get(LedgerEntry_.id).get(LedgerEntryPk_.memberId));
	}
}
