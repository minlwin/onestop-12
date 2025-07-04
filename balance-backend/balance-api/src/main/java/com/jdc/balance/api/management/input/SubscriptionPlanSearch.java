package com.jdc.balance.api.management.input;

import static com.jdc.balance.common.utils.EntityOperations.likeString;

import java.util.ArrayList;

import org.springframework.util.StringUtils;

import com.jdc.balance.domain.entity.SubscriptionPlan;
import com.jdc.balance.domain.entity.SubscriptionPlan_;

import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;

public record SubscriptionPlanSearch(
		Boolean active,
		String keyword,
		Integer monthFrom,
		Integer monthTo) {

	public Predicate[] where(CriteriaBuilder cb, Root<SubscriptionPlan> root) {
		
		var predicates = new ArrayList<Predicate>();
		
		if(null != active) {
			predicates.add(cb.equal(root.get(SubscriptionPlan_.active), active));
		}
		
		if(null != monthFrom) {
			predicates.add(cb.ge(root.get(SubscriptionPlan_.months), monthFrom));
		}

		if(null != monthTo) {
			predicates.add(cb.le(root.get(SubscriptionPlan_.months), monthTo));
		}
		
		if(StringUtils.hasLength(keyword)) {
			predicates.add(cb.like(cb.lower(root.get(SubscriptionPlan_.name)), likeString(keyword)));
		}
		
		return predicates.toArray(size -> new Predicate[size]);
	}

}
