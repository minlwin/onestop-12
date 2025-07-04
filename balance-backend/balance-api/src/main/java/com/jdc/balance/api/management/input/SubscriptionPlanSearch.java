package com.jdc.balance.api.management.input;

public record SubscriptionPlanSearch(
		Boolean active,
		String keyword,
		Integer monthFrom,
		Integer monthTo) {

}
