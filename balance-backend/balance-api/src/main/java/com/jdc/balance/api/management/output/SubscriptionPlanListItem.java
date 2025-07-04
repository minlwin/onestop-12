package com.jdc.balance.api.management.output;

public record SubscriptionPlanListItem(
		int id,
		String name,
		int months,
		int fees,
		Integer maxLedgers,
		Integer dailyEntry,
		Integer monthlyEntry,
		boolean defaultPaln,
		boolean active,
		long subscription) {

}
