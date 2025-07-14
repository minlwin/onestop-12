package com.jdc.balance.api.member.output;

import com.jdc.balance.domain.entity.SubscriptionPlan;
import com.jdc.balance.domain.entity.SubscriptionPlan_;

import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Root;

public record SubscriptionPlanListItem(
		int id,
		String name,
		int months,
		int fees,
		Integer maxLedgers,
		Integer dailyEntry,
		Integer monthlyEntry,
		boolean defaultPlan) {

	public static void select(CriteriaQuery<SubscriptionPlanListItem> cq, Root<SubscriptionPlan> root) {
		cq.multiselect(
				root.get(SubscriptionPlan_.id),
				root.get(SubscriptionPlan_.name),
				root.get(SubscriptionPlan_.months),
				root.get(SubscriptionPlan_.fees),
				root.get(SubscriptionPlan_.maxLedgers),
				root.get(SubscriptionPlan_.dailyEntry),
				root.get(SubscriptionPlan_.monthlyEntry),
				root.get(SubscriptionPlan_.defaultPlan)
		);
	}
}
