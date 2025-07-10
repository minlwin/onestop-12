package com.jdc.balance.api.member.output;

import com.jdc.balance.domain.entity.PaymentMethod;
import com.jdc.balance.domain.entity.PaymentMethod_;

import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Root;

public record PaymentMethodListItem(
		int id,
		String name,
		String accountNo,
		String accountName) {

	public static void select(CriteriaQuery<PaymentMethodListItem> cq, Root<PaymentMethod> root) {
		cq.multiselect(
			root.get(PaymentMethod_.id),
			root.get(PaymentMethod_.name),
			root.get(PaymentMethod_.accountNo),
			root.get(PaymentMethod_.accountName)
		);
	}

}
