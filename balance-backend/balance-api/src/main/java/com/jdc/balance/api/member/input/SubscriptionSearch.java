package com.jdc.balance.api.member.input;

import java.time.LocalDate;
import java.util.ArrayList;

import com.jdc.balance.domain.embeddable.SubscriptionPk_;
import com.jdc.balance.domain.entity.Account_;
import com.jdc.balance.domain.entity.Member_;
import com.jdc.balance.domain.entity.Subscription;
import com.jdc.balance.domain.entity.SubscriptionPlan_;
import com.jdc.balance.domain.entity.Subscription_;

import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;

public record SubscriptionSearch(
		Integer planId,
		LocalDate appliedFrom,
		LocalDate appliedTo) {

	public Predicate[] where(CriteriaBuilder cb, Root<Subscription> root, String username) {
		
		var params = new ArrayList<Predicate>();
		params.add(cb.equal(root.get(Subscription_.member).get(Member_.account).get(Account_.email), username));
		
		if(planId != null) {
			params.add(cb.equal(root.get(Subscription_.plan).get(SubscriptionPlan_.id), planId));
		}
		
		if(appliedFrom != null) {
			params.add(cb.greaterThanOrEqualTo(root.get(Subscription_.id).get(SubscriptionPk_.appliedAt), appliedFrom));
		}
		
		if(appliedTo != null) {
			params.add(cb.lessThanOrEqualTo(root.get(Subscription_.id).get(SubscriptionPk_.appliedAt), appliedTo));
		}
		
		return params.toArray(size -> new Predicate[size]);
	}

}
