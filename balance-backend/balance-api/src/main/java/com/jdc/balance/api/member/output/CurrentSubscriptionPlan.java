package com.jdc.balance.api.member.output;

import java.time.LocalDate;

import com.jdc.balance.domain.entity.Member;

public record CurrentSubscriptionPlan(
		int planId,
		String planName,
		LocalDate startAt,
		LocalDate expiredAt) {

	public boolean isExpired() {
		return LocalDate.now().isAfter(expiredAt);
	}
	
	public static CurrentSubscriptionPlan from(Member entity) {
		return new CurrentSubscriptionPlan(
				entity.getPlan().getId(), 
				entity.getPlan().getName(), 
				entity.getAccount().getExpiredAt().minusMonths(entity.getPlan().getMonths()), 
				entity.getAccount().getExpiredAt());
	}
}
