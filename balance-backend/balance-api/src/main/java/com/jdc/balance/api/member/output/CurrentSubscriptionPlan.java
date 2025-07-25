package com.jdc.balance.api.member.output;

import java.time.LocalDate;

import com.jdc.balance.domain.entity.Member;

public record CurrentSubscriptionPlan(
		int planId,
		String planName,
		LocalDate startAt,
		LocalDate expiredAt) {

	public boolean isExpired() {
		return null != expiredAt && LocalDate.now().isAfter(expiredAt);
	}
	
	public static CurrentSubscriptionPlan from(Member entity) {
		return new CurrentSubscriptionPlan(
				entity.getSubscription().getPlan().getId(), 
				entity.getSubscription().getPlan().getName(), 
				entity.getSubscription().getStartAt(), 
				entity.getSubscription().getExpiredAt());
	}
}
