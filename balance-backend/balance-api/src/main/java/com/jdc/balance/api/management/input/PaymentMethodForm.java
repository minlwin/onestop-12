package com.jdc.balance.api.management.input;

import com.jdc.balance.domain.entity.PaymentMethod;

import jakarta.validation.constraints.NotBlank;

public record PaymentMethodForm(
		@NotBlank(message = "Please enter payment method name.")
		String name, 
		@NotBlank(message = "Please enter account number.")
		String accountNo,
		@NotBlank(message = "Please enter account name.")
		String accountName,
		boolean active) {

	public PaymentMethod entity() {
		var entity = new PaymentMethod();
		update(entity);
		return entity;
	}

	public void update(PaymentMethod entity) {
		entity.setName(name);
		entity.setAccountNo(accountNo);
		entity.setAccountName(accountName);
		entity.setActive(active);
	}

}
