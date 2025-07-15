package com.jdc.balance.api.member;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.jdc.balance.api.member.input.LedgerEntryForm;
import com.jdc.balance.api.member.input.LedgerEntrySearch;
import com.jdc.balance.api.member.output.LedgerEntryDetails;
import com.jdc.balance.api.member.output.LedgerEntryListItem;
import com.jdc.balance.api.member.service.LedgerEntryService;
import com.jdc.balance.common.dto.ModificationResult;
import com.jdc.balance.domain.PageResult;
import com.jdc.balance.domain.embeddable.LedgerEntryPk;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("member/{username}/entry")
public class LedgerEntryApi {
	
	private final LedgerEntryService service;

	@GetMapping
	@PreAuthorize("authentication.name eq #username")
	PageResult<LedgerEntryListItem> search(
			@PathVariable String username,
			LedgerEntrySearch search, 
			@RequestParam(defaultValue = "0") int page, 
			@RequestParam(defaultValue = "10") int size) {
		return service.search(username, search, page, size);
	}
	
	@GetMapping("{requestedId}")
	@PreAuthorize("authentication.name eq #username")
	LedgerEntryDetails findById(
			@PathVariable String username, 
			@PathVariable String requestedId) {
		return service.findById(username, requestedId);
	}
	
	@PostMapping
	@PreAuthorize("authentication.name eq #username")
	ModificationResult<LedgerEntryPk> create(
			@PathVariable String username,
			@RequestBody @Validated LedgerEntryForm form) {
		return service.create(username, form);
	}
	
	@PutMapping("{requestedId}")
	@PreAuthorize("authentication.name eq #susername")
	ModificationResult<LedgerEntryPk> update(
			@PathVariable String username,
			@PathVariable String requestedId,
			@RequestBody @Validated LedgerEntryForm form) {
		return service.update(username, requestedId, form);
	}
	
}
