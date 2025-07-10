package com.jdc.balance.api.management.output;

import java.time.LocalDate;
import java.time.LocalDateTime;

import com.jdc.balance.domain.embeddable.SubscriptionPk;
import com.jdc.balance.domain.entity.Subscription;
import com.jdc.balance.domain.entity.Subscription.Status;
import com.jdc.balance.domain.entity.Subscription.Usage;

public record SubscriptionDetails(
        SubscriptionPk id,
        String planName,
        String previousPlan,
        LocalDate expiredAt,
        String paymentName,
        int paymentAmount,
        String paymentSlip,
        long memberId,
        String memberName,
        String phone,
        String email,
        Usage usage,
        Status status,
        LocalDateTime statusChangeAt,
        String reason) {
	
	public static SubscriptionDetails from(Subscription entity) {
		return new Builder()
				.id(entity.getId())
				.planName(entity.getPlan().getName())
				.previousPlan(entity.getPreviousPlan().getName())
				.expiredAt(entity.getMember().getAccount().getExpiredAt())
				.paymentName(entity.getPayment().getName())
				.paymentAmount(entity.getPaymentAmount())
				.paymentSlip(entity.getPaymentSlip())
				.memberId(entity.getMember().getId())
				.memberName(entity.getMember().getAccount().getName())
				.phone(entity.getMember().getPhone())
				.email(entity.getMember().getAccount().getEmail())
				.usage(entity.getUsage())
				.status(entity.getStatus())
				.statusChangeAt(entity.getStatusChangeAt())
				.reason(entity.getReason())
				.build();
	}


    public static class Builder {
        private SubscriptionPk id;
        private String planName;
        private String previousPlan;
        private LocalDate expiredAt;
        private String paymentName;
        private int paymentAmount;
        private String paymentSlip;
        private long memberId;
        private String memberName;
        private String phone;
        private String email;
        private Usage usage;
        private Status status;
        private LocalDateTime statusChangeAt;
        private String reason;

        public Builder id(SubscriptionPk id) {
            this.id = id;
            return this;
        }

        public Builder planName(String planName) {
            this.planName = planName;
            return this;
        }

        public Builder previousPlan(String previousPlan) {
            this.previousPlan = previousPlan;
            return this;
        }

        public Builder expiredAt(LocalDate expiredAt) {
            this.expiredAt = expiredAt;
            return this;
        }

        public Builder paymentName(String paymentName) {
            this.paymentName = paymentName;
            return this;
        }

        public Builder paymentAmount(int paymentAmount) {
            this.paymentAmount = paymentAmount;
            return this;
        }

        public Builder paymentSlip(String paymentSlip) {
            this.paymentSlip = paymentSlip;
            return this;
        }

        public Builder memberId(long memberId) {
            this.memberId = memberId;
            return this;
        }

        public Builder memberName(String memberName) {
            this.memberName = memberName;
            return this;
        }

        public Builder phone(String phone) {
            this.phone = phone;
            return this;
        }

        public Builder email(String email) {
            this.email = email;
            return this;
        }

        public Builder usage(Usage usage) {
            this.usage = usage;
            return this;
        }

        public Builder status(Status status) {
            this.status = status;
            return this;
        }

        public Builder statusChangeAt(LocalDateTime statusChangeAt) {
            this.statusChangeAt = statusChangeAt;
            return this;
        }

        public Builder reason(String reason) {
            this.reason = reason;
            return this;
        }

        public SubscriptionDetails build() {
            return new SubscriptionDetails(
                id,
                planName,
                previousPlan,
                expiredAt,
                paymentName,
                paymentAmount,
                paymentSlip,
                memberId,
                memberName,
                phone,
                email,
                usage,
                status,
                statusChangeAt,
                reason
            );
        }
    }
}
