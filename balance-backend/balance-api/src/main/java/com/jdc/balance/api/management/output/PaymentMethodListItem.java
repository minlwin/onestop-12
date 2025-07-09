package com.jdc.balance.api.management.output;

import com.jdc.balance.domain.embeddable.SubscriptionPk_;
import com.jdc.balance.domain.entity.PaymentMethod;
import com.jdc.balance.domain.entity.PaymentMethod_;
import com.jdc.balance.domain.entity.Subscription_;

import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.JoinType;
import jakarta.persistence.criteria.Root;

public record PaymentMethodListItem(
		int id,
		String name,
		String accountNo,
		String accountName,
		boolean active,
		long payments) {

	public static void select(CriteriaBuilder cb, CriteriaQuery<PaymentMethodListItem> cq, Root<PaymentMethod> root) {
		
		var subscription = root.join(PaymentMethod_.subscription, JoinType.LEFT);
		
		cq.multiselect(
			root.get(PaymentMethod_.id),
			root.get(PaymentMethod_.name),
			root.get(PaymentMethod_.accountNo),
			root.get(PaymentMethod_.accountName),
			root.get(PaymentMethod_.active),
			cb.count(subscription.get(Subscription_.id).get(SubscriptionPk_.planId))
		);
		
		cq.groupBy(
			root.get(PaymentMethod_.id),
			root.get(PaymentMethod_.name),
			root.get(PaymentMethod_.accountNo),
			root.get(PaymentMethod_.accountName),
			root.get(PaymentMethod_.active)
		);
		
		cq.distinct(true);
	}

}
