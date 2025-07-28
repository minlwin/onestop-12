package com.jdc.balance.api.management;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.jdc.balance.api.management.service.AdminDashboardService;
import com.jdc.balance.common.dto.YearMonthData;
import com.jdc.balance.domain.entity.Subscription.Status;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("management/dashboard")
public class DashboardApi {
	
	private final AdminDashboardService service;

	@GetMapping("years")
	List<Integer> getYears() {
		return service.getYears();
	}
	
	@GetMapping("summary")
	Map<Status, Map<String, Long>> getSummary(YearMonthData data) {
		return service.getSummary(data);
	}
	
	@GetMapping("progress")
	Map<LocalDate, Long> getProgress(YearMonthData data) {
		return service.getProgress(data);
	}
	
}
