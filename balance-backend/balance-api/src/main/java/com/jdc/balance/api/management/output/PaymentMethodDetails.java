package com.jdc.balance.api.management.output;

public record PaymentMethodDetails(
		int id,
		String name,
		String accountNo,
		String accountName,
		boolean active) {

}
