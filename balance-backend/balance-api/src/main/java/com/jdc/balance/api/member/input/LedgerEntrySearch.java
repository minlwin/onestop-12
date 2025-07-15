package com.jdc.balance.api.member.input;

import static com.jdc.balance.common.utils.EntityOperations.likeString;

import java.time.LocalDate;
import java.util.ArrayList;

import org.springframework.util.StringUtils;

import com.jdc.balance.domain.embeddable.LedgerPk_;
import com.jdc.balance.domain.entity.Account_;
import com.jdc.balance.domain.entity.Ledger.Type;
import com.jdc.balance.domain.entity.LedgerEntry;
import com.jdc.balance.domain.entity.LedgerEntry_;
import com.jdc.balance.domain.entity.Ledger_;
import com.jdc.balance.domain.entity.Member_;

import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;

public record LedgerEntrySearch(
		Type type,
		String code,
		LocalDate from,
		LocalDate to,
		String keyword) {

	public Predicate[] where(CriteriaBuilder cb, Root<LedgerEntry> root, String username) {
		
		var params = new ArrayList<Predicate>();
		params.add(cb.equal(root.get(LedgerEntry_.ledger).get(Ledger_.member).get(Member_.account).get(Account_.email), username));
		
		
		if(StringUtils.hasLength(code)) {
			params.add(cb.like(cb.lower(root.get(LedgerEntry_.ledger).get(Ledger_.id).get(LedgerPk_.code)), likeString(code)));
		}
		
		if(type != null) {
			params.add(cb.equal(root.get(LedgerEntry_.ledger).get(Ledger_.type), type));
		}
		
		if(from != null) {
			params.add(cb.greaterThanOrEqualTo(root.get(LedgerEntry_.issueAt), from));
		}
		
		if(to != null) {
			params.add(cb.lessThanOrEqualTo(root.get(LedgerEntry_.issueAt), to));
		}
		
		if(StringUtils.hasLength(keyword)) {
			params.add(cb.or(
				cb.like(cb.lower(root.get(LedgerEntry_.particular)), likeString(keyword)),
				cb.like(cb.lower(root.get(LedgerEntry_.ledger).get(Ledger_.name)), likeString(keyword))
			));
		}
		
		return params.toArray(size -> new Predicate[size]);
	}

}
