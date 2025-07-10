package com.jdc.balance.api.member.output;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Optional;

import com.jdc.balance.domain.embeddable.SubscriptionPk;
import com.jdc.balance.domain.entity.Subscription;
import com.jdc.balance.domain.entity.Subscription.Status;
import com.jdc.balance.domain.entity.Subscription.Usage;

public record SubscriptionDetails(
        SubscriptionPk id,
        String previousPlan,
        LocalDate expiredAt,
        String planName,
        LocalDate planStartAt,
        int fees,
        int paymentId,
        String paymentName,
        String accountNo,
        String accountName,
        String paymentSlip,
        Usage usage,
        Status status,
        String reason,
        LocalDateTime statusChangeAt) {
	
	public static SubscriptionDetails from(Subscription entity) {
		return new Builder()
				.id(entity.getId())
				.previousPlan(Optional.ofNullable(entity.getMember().getPlan()).map(a -> a.getName()).orElse(null))
				.expiredAt(entity.getMember().getAccount().getExpiredAt())
				.planName(entity.getPlan().getName())
				.planStartAt(entity.getStartAt())
				.fees(entity.getPaymentAmount())
				.paymentId(entity.getPayment().getId())
				.paymentName(entity.getPayment().getName())
				.accountNo(entity.getPayment().getAccountNo())
				.accountName(entity.getPayment().getAccountName())
				.paymentSlip(entity.getPaymentSlip())
				.usage(entity.getUsage())
				.status(entity.getStatus())
				.reason(entity.getReason())
				.statusChangeAt(entity.getStatusChangeAt())
				.build();
	}
	
    public static class Builder {
        private SubscriptionPk id;
        private String previousPlan;
        private LocalDate expiredAt;
        private String planName;
        private LocalDate planStartAt;
        private int fees;
        private int paymentId;
        private String paymentName;
        private String accountNo;
        private String accountName;
        private String paymentSlip;
        private Usage usage;
        private Status status;
        private String reason;
        private LocalDateTime statusChangeAt;

        public Builder id(SubscriptionPk id) {
            this.id = id;
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

        public Builder planName(String planName) {
            this.planName = planName;
            return this;
        }

        public Builder planStartAt(LocalDate planStartAt) {
            this.planStartAt = planStartAt;
            return this;
        }

        public Builder fees(int fees) {
            this.fees = fees;
            return this;
        }

        public Builder paymentId(int paymentId) {
            this.paymentId = paymentId;
            return this;
        }

        public Builder paymentName(String paymentName) {
            this.paymentName = paymentName;
            return this;
        }

        public Builder accountNo(String accountNo) {
            this.accountNo = accountNo;
            return this;
        }

        public Builder accountName(String accountName) {
            this.accountName = accountName;
            return this;
        }

        public Builder paymentSlip(String paymentSlip) {
            this.paymentSlip = paymentSlip;
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

        public Builder reason(String reason) {
            this.reason = reason;
            return this;
        }

        public Builder statusChangeAt(LocalDateTime statusChangeAt) {
            this.statusChangeAt = statusChangeAt;
            return this;
        }

        public SubscriptionDetails build() {
            return new SubscriptionDetails(
                id,
                previousPlan,
                expiredAt,
                planName,
                planStartAt,
                fees,
                paymentId,
                paymentName,
                accountNo,
                accountName,
                paymentSlip,
                usage,
                status,
                reason,
                statusChangeAt
            );
        }
    }

}
