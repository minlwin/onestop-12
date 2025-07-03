package com.jdc.balance.common.security.promotion;

public record PromotionPeriod(
		int value,
		PeriodUnit unit) {

	public enum PeriodUnit {
		Day, Month
	}
}
