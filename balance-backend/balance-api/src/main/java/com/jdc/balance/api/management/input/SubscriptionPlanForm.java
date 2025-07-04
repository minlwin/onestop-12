package com.jdc.balance.api.management.input;

import jakarta.validation.constraints.NotBlank;

public record SubscriptionPlanForm(
		@NotBlank(message = "Please enter plan name.")
		String name,
		int months,
		int fees,
		Integer maxLedgers,
		Integer dailyEntry,
		Integer monthlyEntry,
		boolean defaultPaln,
		boolean active) {

}
