package com.jdc.balance.api.member.output;

import java.time.LocalDateTime;

import com.jdc.balance.domain.embeddable.SubscriptionPk;
import com.jdc.balance.domain.entity.Subscription.Status;

public record SubscriptionListItem(
		SubscriptionPk id,
		int planId,
		String planName,
		int paymentId,
		String paymentName,
		Status status,
		String reason,
		LocalDateTime statusChangeAt) {

}
