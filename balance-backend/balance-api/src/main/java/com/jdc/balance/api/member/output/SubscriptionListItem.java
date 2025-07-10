package com.jdc.balance.api.member.output;

import java.time.LocalDate;
import java.time.LocalDateTime;

import com.jdc.balance.domain.embeddable.SubscriptionPk;
import com.jdc.balance.domain.entity.Account_;
import com.jdc.balance.domain.entity.Member_;
import com.jdc.balance.domain.entity.PaymentMethod_;
import com.jdc.balance.domain.entity.Subscription;
import com.jdc.balance.domain.entity.Subscription.Status;
import com.jdc.balance.domain.entity.Subscription.Usage;
import com.jdc.balance.domain.entity.SubscriptionPlan_;
import com.jdc.balance.domain.entity.Subscription_;

import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.JoinType;
import jakarta.persistence.criteria.Root;

public record SubscriptionListItem(
		SubscriptionPk id,
		String previousPlan,
		LocalDate expiredAt,
		String planName,
		String paymentName,
		Usage usage,
		Status status,
		String reason,
		LocalDateTime statusChangeAt) {

	public static void select(CriteriaQuery<SubscriptionListItem> cq, Root<Subscription> root) {
		
		var payment = root.join(Subscription_.payment, JoinType.LEFT);
		var previousPlan = root.join(Subscription_.previousPlan, JoinType.LEFT);

		cq.multiselect(
			root.get(Subscription_.id),
			previousPlan.get(SubscriptionPlan_.name),
			root.get(Subscription_.member).get(Member_.account).get(Account_.expiredAt),
			root.get(Subscription_.plan).get(SubscriptionPlan_.name),
			payment.get(PaymentMethod_.name),
			root.get(Subscription_.status),
			root.get(Subscription_.reason),
			root.get(Subscription_.statusChangeAt)
		);
	}

}
