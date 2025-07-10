package com.jdc.balance.api.member;

import java.nio.file.Path;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.jdc.balance.api.member.input.SubscriptionForm;
import com.jdc.balance.api.member.input.SubscriptionSearch;
import com.jdc.balance.api.member.output.SubscriptionDetails;
import com.jdc.balance.api.member.output.SubscriptionListItem;
import com.jdc.balance.api.member.service.MemberSubscriptionService;
import com.jdc.balance.domain.PageResult;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RequestMapping("member/subscription")
@RestController("memberSubscriptionApi")
public class SubscriptionApi {
	
	private final MemberSubscriptionService service;
	
	@Value("${app.subscription.slip-directory}")
	private String slipDirectory;
	
	@GetMapping
	PageResult<SubscriptionListItem> search(SubscriptionSearch search, 
			@RequestParam(defaultValue = "0") int page, 
			@RequestParam(defaultValue = "10") int size) {
		return service.search(search, page, size);
	}
	
	@PostMapping
	@PreAuthorize("authentication.name eq #form.username")
	SubscriptionDetails create(@Validated SubscriptionForm form, HttpServletRequest req) {
		var slipDirectoryPath = req.getServletContext().getRealPath(slipDirectory);
		return service.create(form, Path.of(slipDirectoryPath));
	}
	
	@GetMapping("{code}")
	SubscriptionDetails findById(@PathVariable String code) {
		return service.findById(code);
	}
}
