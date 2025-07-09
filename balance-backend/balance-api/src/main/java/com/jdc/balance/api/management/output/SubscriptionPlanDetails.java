package com.jdc.balance.api.management.output;

import com.jdc.balance.domain.entity.SubscriptionPlan;

public record SubscriptionPlanDetails(
		int id,
		String name,
		int months,
		int fees,
		Integer maxLedgers,
		Integer dailyEntry,
		Integer monthlyEntry,
		boolean defaultPlan,
		boolean active) {

	public static SubscriptionPlanDetails from(SubscriptionPlan entity) {
		return new SubscriptionPlanDetails(
			entity.getId(),
			entity.getName(),
			entity.getMonths(),
			entity.getFees(),
			entity.getMaxLedgers(),
			entity.getDailyEntry(),
			entity.getMonthlyEntry(),
			entity.isDefaultPlan(),
			entity.isActive()
		);
	}

}
