package com.jdc.balance.api.management;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.jdc.balance.api.management.input.MemberSearch;
import com.jdc.balance.api.management.output.MemberListItem;
import com.jdc.balance.api.management.service.MemberService;
import com.jdc.balance.domain.PageResult;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("management/member")
public class MembersApi {
	
	private final MemberService service;

	@GetMapping
	PageResult<MemberListItem> search(MemberSearch search, 
			@RequestParam(defaultValue = "0") int page, 
			@RequestParam(defaultValue = "10") int size) {
		return service.search(search, page, size);
	}
}
