package com.jdc.balance.api.member;

import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.jdc.balance.api.management.output.SubscriptionDetails;
import com.jdc.balance.api.member.input.SubscriptionForm;
import com.jdc.balance.api.member.input.SubscriptionSearch;
import com.jdc.balance.api.member.output.SubscriptionListItem;
import com.jdc.balance.domain.PageResult;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RequestMapping("member/subscription")
@RestController("memberSubscriptionApi")
public class SubscriptionApi {
	
	@GetMapping
	PageResult<SubscriptionListItem> search(SubscriptionSearch search, 
			@RequestParam(defaultValue = "0") int page, 
			@RequestParam(defaultValue = "10") int size) {
		return null;
	}
	
	@PostMapping
	SubscriptionDetails create(@Validated @RequestBody SubscriptionForm form) {
		return null;
	}
	
	@GetMapping("{code}")
	SubscriptionDetails findById(@PathVariable String code) {
		return null;
	}
}
