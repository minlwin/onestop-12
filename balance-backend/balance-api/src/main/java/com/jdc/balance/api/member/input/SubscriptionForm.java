package com.jdc.balance.api.member.input;

import org.springframework.web.multipart.MultipartFile;

import com.jdc.balance.domain.entity.Subscription.Usage;

import jakarta.validation.constraints.NotNull;

public record SubscriptionForm(
		@NotNull(message = "Please select plan.")
		Integer planId,
		@NotNull(message = "Please select payment.")
		Integer paymentId,
		@NotNull(message = "Please select payment slip.")
		MultipartFile slip,
		@NotNull(message = "Please select usage type.")
		Usage usage) {

}
