package com.jdc.balance.api.member.service;

import java.time.LocalDate;
import java.time.YearMonth;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class CutOffDayService {

	@Value("${app.cutoff.day.value}")
	private int cutOffDay;
	
	public boolean canEdit(LocalDate date) {
		var cutOffDate = YearMonth.now().atDay(cutOffDay);
		return date.isBefore(cutOffDate);
	}
}
