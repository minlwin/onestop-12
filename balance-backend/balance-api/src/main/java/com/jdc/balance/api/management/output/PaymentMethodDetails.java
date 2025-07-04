package com.jdc.balance.api.management.output;

import com.jdc.balance.domain.entity.PaymentMethod;

public record PaymentMethodDetails(
		int id,
		String name,
		String accountNo,
		String accountName,
		boolean active) {

	public static PaymentMethodDetails from(PaymentMethod entity) {
		return new PaymentMethodDetails(
				entity.getId(), 
				entity.getName(), 
				entity.getAccountNo(), 
				entity.getAccountName(), 
				entity.isActive());
	}

}
