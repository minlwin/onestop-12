package com.jdc.balance.api.management.output;

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
		String planName,
		int fees,
		long memberId,
		String memberName,
		int lastPlanId,
		String lastPlanName,
		LocalDate expiredAt,
		String paymentName, 
		Usage usage,
		Status status,
		LocalDateTime statusChangeAt,
		String reason) {

	public static void select(CriteriaQuery<SubscriptionListItem> cq, Root<Subscription> root) {
		
		var payment = root.join(Subscription_.payment, JoinType.LEFT);
		var previousPlan = root.join(Subscription_.previousPlan, JoinType.LEFT);
		
		cq.multiselect(
			root.get(Subscription_.id),
			root.get(Subscription_.plan).get(SubscriptionPlan_.name),
			root.get(Subscription_.paymentAmount),
			root.get(Subscription_.member).get(Member_.id),
			root.get(Subscription_.member).get(Member_.account).get(Account_.name),
			previousPlan.get(SubscriptionPlan_.id),
			previousPlan.get(SubscriptionPlan_.name),
			root.get(Subscription_.member).get(Member_.account).get(Account_.expiredAt),
			payment.get(PaymentMethod_.name),
			root.get(Subscription_.usage),
			root.get(Subscription_.status),
			root.get(Subscription_.statusChangeAt),
			root.get(Subscription_.reason)
		);
	}

}
