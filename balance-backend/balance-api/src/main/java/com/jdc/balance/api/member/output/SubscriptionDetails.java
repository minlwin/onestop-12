package com.jdc.balance.api.member.output;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Optional;

import com.jdc.balance.common.dto.PlanInfo;
import com.jdc.balance.domain.embeddable.SubscriptionPk;
import com.jdc.balance.domain.entity.Subscription;
import com.jdc.balance.domain.entity.Subscription.Status;
import com.jdc.balance.domain.entity.Subscription.Usage;

public record SubscriptionDetails(
        SubscriptionPk id,
        PlanInfo plan,
        PlanInfo previousPlan,
        LocalDate currentStartAt,
        LocalDate currentExpiredAt,
        LocalDate prevAppliedAt,
        LocalDate prevStartAt,
        LocalDate prevEndAt,
        int fees,
        String payment,
        String accountNo,
        String accountName,
        String paymentSlip,
        Usage usage,
        Status status,
        String reason,
        LocalDateTime statusChangeAt) {
	
	public LocalDate getCurrentAppliedAt() {
		return id.getAppliedAt();
	}

	public static SubscriptionDetails from(Subscription entity) {
		
		return new SubscriptionDetails(
				entity.getId(),
				new PlanInfo(entity.getPlan()),
				Optional.ofNullable(entity.getPreviousPlan()).map(PlanInfo::new).orElse(null),
				entity.getStartAt(),
				entity.getExpiredAt(),
				entity.getPreviousPlanAppliedAt(),
				entity.getPreviousPlanStartAt(),
				entity.getPreviousPlanExpiredAt(),
				entity.getPaymentAmount(),
				Optional.ofNullable(entity.getPayment()).map(a -> a.getName()).orElse(""),
				Optional.ofNullable(entity.getPayment()).map(a -> a.getAccountNo()).orElse(""),
				Optional.ofNullable(entity.getPayment()).map(a -> a.getAccountName()).orElse(""),
				entity.getPaymentSlip(),
				entity.getUsage(),
				entity.getStatus(),
				entity.getReason(),
				entity.getStatusChangeAt());
	}
	
}
