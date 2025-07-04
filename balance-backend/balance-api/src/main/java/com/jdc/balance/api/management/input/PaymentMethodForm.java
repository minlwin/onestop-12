package com.jdc.balance.api.management.input;

import jakarta.validation.constraints.NotBlank;

public record PaymentMethodForm(
		@NotBlank(message = "Please enter payment method name.")
		String name, 
		@NotBlank(message = "Please enter account number.")
		String accountNo,
		@NotBlank(message = "Please enter account name.")
		String accountName,
		boolean active) {

}
