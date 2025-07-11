package com.jdc.balance.api.member;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.jdc.balance.api.member.input.BalanceReportSearch;
import com.jdc.balance.api.member.output.BalanceReportListItem;
import com.jdc.balance.api.member.service.BalanceReportService;
import com.jdc.balance.domain.PageResult;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("member/{username}/balance")
public class BalanceReportApi {

	private final BalanceReportService service;
	
	@GetMapping
	@PreAuthorize("authorization.name eq #username")
	PageResult<BalanceReportListItem> search(
			@PathVariable String username,
			BalanceReportSearch search,
			@RequestParam(defaultValue = "0") int page,
			@RequestParam(defaultValue = "10") int size) {
		return service.search(username, search, page, size);
	}
}
