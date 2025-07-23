package com.jdc.balance.api.management.output;

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
        int paymentAmount,
        String paymentName,
        String paymentSlip,
        long memberId,
        String memberName,
        String phone,
        String email,
        Usage usage,
        Status status,
        LocalDateTime statusChangeAt,
        String reason, 
        String createdBy,
        LocalDateTime createdAt,
        String updatedBy,
        LocalDateTime updatedAt) {

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
				entity.getPaymentSlip(),
				entity.getMember().getId(),
				entity.getMember().getAccount().getName(),
				entity.getMember().getPhone(),
				entity.getMember().getAccount().getEmail(),
				entity.getUsage(),
				entity.getStatus(),
				entity.getStatusChangeAt(),
				entity.getReason(),
				entity.getCreatedBy(),
				entity.getCreatedAt(),
				entity.getUpdatedBy(),
				entity.getUpdatedAt()
		);
	}
	

}
