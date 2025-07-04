package com.jdc.balance.api.management.output;

public record PaymentMethodListItem(
		int id,
		String name,
		String accountNo,
		String accountName,
		boolean active,
		long payments) {

}
