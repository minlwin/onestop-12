package com.jdc.balance.api.management.output;

import com.jdc.balance.domain.entity.SubscriptionPlan;
import com.jdc.balance.domain.entity.SubscriptionPlan_;
import com.jdc.balance.domain.entity.Subscription_;

import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.JoinType;
import jakarta.persistence.criteria.Root;

public record SubscriptionPlanListItem(
		int id,
		String name,
		int months,
		int fees,
		Integer maxLedgers,
		Integer dailyEntry,
		Integer monthlyEntry,
		boolean defaultPlan,
		boolean active,
		long subscription) {

	public static void select(CriteriaBuilder cb, CriteriaQuery<SubscriptionPlanListItem> cq,
			Root<SubscriptionPlan> root) {

		var subscription = root.join(SubscriptionPlan_.subscription, JoinType.LEFT);
		
		cq.multiselect(
			root.get(SubscriptionPlan_.id),
			root.get(SubscriptionPlan_.name),
			root.get(SubscriptionPlan_.months),
			root.get(SubscriptionPlan_.fees),
			root.get(SubscriptionPlan_.maxLedgers),
			root.get(SubscriptionPlan_.dailyEntry),
			root.get(SubscriptionPlan_.monthlyEntry),
			root.get(SubscriptionPlan_.defaultPlan),
			root.get(SubscriptionPlan_.active),
			cb.count(subscription.get(Subscription_.id))
		);
		
		cq.groupBy(
			root.get(SubscriptionPlan_.id),
			root.get(SubscriptionPlan_.name),
			root.get(SubscriptionPlan_.months),
			root.get(SubscriptionPlan_.fees),
			root.get(SubscriptionPlan_.maxLedgers),
			root.get(SubscriptionPlan_.dailyEntry),
			root.get(SubscriptionPlan_.monthlyEntry),
			root.get(SubscriptionPlan_.defaultPlan),
			root.get(SubscriptionPlan_.active)				
		);
	}

}
