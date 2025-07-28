package com.jdc.balance.api.member.output;

import java.math.BigDecimal;
import java.time.LocalDate;

public record MemberProgress(
		LocalDate date,
		BigDecimal debit,
		BigDecimal credit) {
	
	public LocalDate getName() {
		return date;
	}

	public static MemberProgress of(LocalDate date, MemberProgressSelect select) {
		return new MemberProgress(date, select.debit(), select.credit());
	}
}
