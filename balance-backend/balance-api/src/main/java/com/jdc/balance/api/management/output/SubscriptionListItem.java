package com.jdc.balance.api.management.output;

import java.time.LocalDate;
import java.time.LocalDateTime;

import com.jdc.balance.domain.embeddable.SubscriptionPk;
import com.jdc.balance.domain.entity.Account_;
import com.jdc.balance.domain.entity.Member_;
import com.jdc.balance.domain.entity.PaymentMethod_;
import com.jdc.balance.domain.entity.Subscription;
import com.jdc.balance.domain.entity.Subscription.Status;
import com.jdc.balance.domain.entity.SubscriptionPlan_;
import com.jdc.balance.domain.entity.Subscription_;

import jakarta.persistence.criteria.CriteriaQuery;
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
		Status status,
		LocalDateTime statusChangeAt,
		String reason) {

	public static void select(CriteriaQuery<SubscriptionListItem> cq, Root<Subscription> root) {
		cq.multiselect(
			root.get(Subscription_.id),
			root.get(Subscription_.plan).get(SubscriptionPlan_.name),
			root.get(Subscription_.paymentAmount),
			root.get(Subscription_.member).get(Member_.id),
			root.get(Subscription_.member).get(Member_.account).get(Account_.name),
			root.get(Subscription_.member).get(Member_.plan).get(SubscriptionPlan_.id),
			root.get(Subscription_.member).get(Member_.plan).get(SubscriptionPlan_.name),
			root.get(Subscription_.member).get(Member_.account).get(Account_.expiredAt),
			root.get(Subscription_.payment).get(PaymentMethod_.name),
			root.get(Subscription_.status),
			root.get(Subscription_.statusChangeAt),
			root.get(Subscription_.reason)
		);
	}

}
