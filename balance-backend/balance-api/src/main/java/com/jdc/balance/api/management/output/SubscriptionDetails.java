package com.jdc.balance.api.management.output;

import java.time.LocalDate;
import java.time.LocalDateTime;

import com.jdc.balance.domain.embeddable.SubscriptionPk;
import com.jdc.balance.domain.entity.Subscription;
import com.jdc.balance.domain.entity.Subscription.Status;

public record SubscriptionDetails(		
		SubscriptionPk id,
		String planName,
		int fees,
		long memberId,
		String memberName,
		String phone,
		String email,
		int lastPlanId,
		String lastPlanName,
		LocalDate expiredAt,
		String paymentName, 
		Status status,
		LocalDateTime statusChangeAt,
		String reason) {

	public static SubscriptionDetails from(Subscription entity) {
		// TODO Auto-generated method stub
		return null;
	}

}
