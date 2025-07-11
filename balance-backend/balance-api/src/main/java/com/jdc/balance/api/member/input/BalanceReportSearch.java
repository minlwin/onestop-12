package com.jdc.balance.api.member.input;

import java.time.LocalDate;
import java.util.ArrayList;

import com.jdc.balance.domain.entity.Account_;
import com.jdc.balance.domain.entity.LedgerEntry;
import com.jdc.balance.domain.entity.LedgerEntry_;
import com.jdc.balance.domain.entity.Member_;

import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;

public record BalanceReportSearch(
		LocalDate from,
		LocalDate to) {

	public Predicate[] where(String username, CriteriaBuilder cb, Root<LedgerEntry> root) {
		
		var params = new ArrayList<Predicate>();
		params.add(cb.equal(root.get(LedgerEntry_.member).get(Member_.account).get(Account_.email), username));
		
		if(null != from) {
			params.add(cb.greaterThanOrEqualTo(root.get(LedgerEntry_.issueAt), from));
		}
		
		if(null != to) {
			params.add(cb.lessThanOrEqualTo(root.get(LedgerEntry_.issueAt), to));
		}
		
		return params.toArray(size -> new Predicate[size]);
	}

}
