package com.jdc.balance.api.management.output;

import com.jdc.balance.domain.entity.SubscriptionPlan;

public record PlanInfo(
		int id,
		String name,
		int months,
		int fees,
		Integer maxLedgers,
		Integer dailyEntry,
		Integer monthlyEntry) {
	
	public PlanInfo(SubscriptionPlan entity) {
		this(entity.getId(), entity.getName(), entity.getMonths(), entity.getFees(), entity.getMaxLedgers(), entity.getDailyEntry(), entity.getMonthlyEntry());
	}

}
