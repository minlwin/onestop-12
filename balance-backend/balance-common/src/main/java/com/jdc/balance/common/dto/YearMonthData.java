package com.jdc.balance.common.dto;

import java.time.LocalDate;

public record YearMonthData(
		Type type,
		Integer year,
		Integer month) {

	public enum Type {
		Yearly, Monthly
	}

	public LocalDate getStartDate() {
		return type == Type.Yearly ? LocalDate.of(year, 1, 1) : LocalDate.of(year, month, 1);
	}
	
	public LocalDate getEndDate() {
		var startDate = getStartDate();
		return type == Type.Yearly ? startDate.plusYears(1) : startDate.plusMonths(1);
	}

	public LocalDate next(LocalDate date) {
		return type == Type.Yearly ? date.plusMonths(1) : date.plusDays(1);
	}	
}
