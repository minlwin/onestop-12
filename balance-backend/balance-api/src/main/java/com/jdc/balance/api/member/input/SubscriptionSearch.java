package com.jdc.balance.api.member.input;

import java.time.LocalDate;

public record SubscriptionSearch(
		LocalDate appliedFrom,
		LocalDate appliedTo) {

}
