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
import com.jdc.balance.api.member.service.SubscriptionService;
import com.jdc.balance.common.dto.ModificationResult;
import com.jdc.balance.domain.PageResult;
import com.jdc.balance.domain.embeddable.SubscriptionPk;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RequestMapping("member/{username}/subscription")
@RestController("memberSubscriptionApi")
public class SubscriptionApi {
	
	private final SubscriptionService service;
	
	@Value("${app.subscription.slip-directory}")
	private String slipDirectory;
	
	@GetMapping
	@PreAuthorize("authentication.name eq #username")
	PageResult<SubscriptionListItem> search(
			@PathVariable String username, 
			SubscriptionSearch search, 
			@RequestParam(defaultValue = "0") int page, 
			@RequestParam(defaultValue = "10") int size) {
		return service.search(search, page, size);
	}
	
	@PostMapping
	@PreAuthorize("authentication.name eq #username")
	ModificationResult<SubscriptionPk> create(
			@Validated SubscriptionForm form, 
			@PathVariable String username, 
			HttpServletRequest req) {
		var slipDirectoryPath = req.getServletContext().getRealPath(slipDirectory);
		return service.create(username, form, Path.of(slipDirectoryPath));
	}
	
	@GetMapping("{code}")
	@PreAuthorize("authentication.name eq #username")
	SubscriptionDetails findById(
			@PathVariable String username, 
			@PathVariable String code) {
		return service.findById(code);
	}
}
