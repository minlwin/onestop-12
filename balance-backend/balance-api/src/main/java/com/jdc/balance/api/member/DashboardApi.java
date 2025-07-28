package com.jdc.balance.api.member;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.jdc.balance.api.member.service.DashboardService;
import com.jdc.balance.common.dto.YearMonthData;
import com.jdc.balance.domain.entity.Ledger.Type;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RestController("memberDashboardApi")
@RequestMapping("member/{username}/dashboard")
public class DashboardApi {
	
	private final DashboardService service;

	@GetMapping("years")
	@PreAuthorize("authentication.name eq #username")
	List<Integer> getYears(@PathVariable String username) {
		return service.getYears(username);
	}

	@GetMapping("summary")
	@PreAuthorize("authentication.name eq #username")
	Map<String, Map<String, BigDecimal>> getSummary(@PathVariable String username, YearMonthData data) {
		return service.getSummary(username, data);
	}
	
	@GetMapping("progress")
	@PreAuthorize("authentication.name eq #username")
	Map<Type, Map<LocalDate, BigDecimal>> getProgress(@PathVariable String username, YearMonthData data) {
		return service.getProgress(username, data);
	}

}
