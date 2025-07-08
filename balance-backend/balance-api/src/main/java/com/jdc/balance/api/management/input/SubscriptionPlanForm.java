package com.jdc.balance.api.management.input;

import com.jdc.balance.domain.entity.SubscriptionPlan;

import jakarta.validation.constraints.NotBlank;

public record SubscriptionPlanForm(
		@NotBlank(message = "Please enter plan name.")
		String name,
		int months,
		int fees,
		Integer maxLedgers,
		Integer dailyEntry,
		Integer monthlyEntry,
		boolean defaultPlan,
		boolean active) {

	public SubscriptionPlan entity() {
		var entity = new SubscriptionPlan();
		update(entity);
		return entity;
	}

	public void update(SubscriptionPlan entity) {
		entity.setName(name);
		entity.setMonths(months);
		entity.setFees(fees);
		entity.setMaxLedgers(maxLedgers);
		entity.setDailyEntry(dailyEntry);
		entity.setMonthlyEntry(monthlyEntry);
		entity.setDefaultPlan(defaultPlan);
		entity.setActive(active);
	}

}
