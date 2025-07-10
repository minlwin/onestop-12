package com.jdc.balance.api.member.input;

import java.time.LocalDate;

public record SubscriptionSearch(
		Integer planId,
		LocalDate appliedFrom,
		LocalDate appliedTo) {

}
