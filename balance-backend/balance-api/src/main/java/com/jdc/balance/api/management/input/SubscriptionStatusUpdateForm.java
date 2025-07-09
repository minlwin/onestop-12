package com.jdc.balance.api.management.input;

import com.jdc.balance.domain.entity.Subscription.Status;

import jakarta.validation.constraints.NotNull;

public record SubscriptionStatusUpdateForm(
		@NotNull(message = "Please select status.")
		Status status,
		String message) {

}
