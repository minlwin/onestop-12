package com.jdc.balance.api.management.input;

import static com.jdc.balance.common.utils.EntityOperations.likeString;

import java.time.LocalDate;
import java.util.ArrayList;

import org.springframework.util.StringUtils;

import com.jdc.balance.domain.embeddable.SubscriptionPk_;
import com.jdc.balance.domain.entity.Account_;
import com.jdc.balance.domain.entity.Member_;
import com.jdc.balance.domain.entity.Subscription;
import com.jdc.balance.domain.entity.Subscription.Status;
import com.jdc.balance.domain.entity.SubscriptionPlan_;
import com.jdc.balance.domain.entity.Subscription_;

import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;

public record SubscriptionSearch(
		Integer planId,
		Status status,
		LocalDate appliedFrom,
		LocalDate appliedTo,
		String keyword) {

	public Predicate[] where(CriteriaBuilder cb, Root<Subscription> root) {
		var list = new ArrayList<Predicate>();
		
		if(null != planId) {
			list.add(cb.equal(root.get(Subscription_.plan)
					.get(SubscriptionPlan_.id), planId));
		}
		
		if(null != status) {
			list.add(cb.equal(root.get(Subscription_.status), status));
		}
		
		if(null != appliedFrom) {
			list.add(cb.greaterThanOrEqualTo(root.get(Subscription_.id).get(SubscriptionPk_.appliedAt), 
					appliedFrom));
		}
		
		if(StringUtils.hasLength(keyword)) {
			list.add(cb.or(
					cb.like(cb.lower(root.get(Subscription_.member).get(Member_.account).get(Account_.name)), likeString(keyword)),
					cb.like(cb.lower(root.get(Subscription_.member).get(Member_.account).get(Account_.email)), likeString(keyword)),
					cb.like(cb.lower(root.get(Subscription_.member).get(Member_.phone)), likeString(keyword))
				));
		}

		return list.toArray(size -> new Predicate[size]);
	}

}
