package com.jdc.balance.api.member.input;

import static com.jdc.balance.common.utils.EntityOperations.likeString;

import java.util.ArrayList;

import org.springframework.util.StringUtils;

import com.jdc.balance.domain.entity.Account_;
import com.jdc.balance.domain.entity.Ledger;
import com.jdc.balance.domain.entity.Ledger.Type;
import com.jdc.balance.domain.entity.Ledger_;
import com.jdc.balance.domain.entity.Member_;

import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;

public record LedgerSearch(
		Type type,
		String keyword) {

	public Predicate[] where(CriteriaBuilder cb, Root<Ledger> root, String username) {
		
		var params = new ArrayList<Predicate>();
		params.add(cb.equal(root.get(Ledger_.member).get(Member_.account).get(Account_.email), username));
		
		
		if(type != null) {
			params.add(cb.equal(root.get(Ledger_.type), type));
		}
		
		if(StringUtils.hasLength(keyword)) {
			params.add(cb.or(
				cb.like(cb.lower(root.get(Ledger_.name)), likeString(keyword)),
				cb.like(cb.lower(root.get(Ledger_.description)), likeString(keyword))
			));
		}
		
		return params.toArray(size -> new Predicate[size]);
	}

}
