package com.jdc.balance.api.member;

import java.util.List;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.jdc.balance.api.member.input.LedgerForm;
import com.jdc.balance.api.member.input.LedgerSearch;
import com.jdc.balance.api.member.output.LedgerListItem;
import com.jdc.balance.api.member.service.LedgerService;
import com.jdc.balance.common.dto.ModificationResult;
import com.jdc.balance.domain.embeddable.LedgerPk;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("member/{username}/ledger")
public class LedgerApi {
	
	private final LedgerService service;

	@GetMapping
	@PreAuthorize("authentication.name eq #username")
	List<LedgerListItem> search(
			@PathVariable String username, 
			LedgerSearch search) {
		return service.search(username, search);
	}
	
	@PostMapping
	@PreAuthorize("authentication.name eq #username")
	ModificationResult<LedgerPk> create(
			@PathVariable String username, 
			@RequestBody @Validated LedgerForm form) {
		return service.create(username, form);
	}

	@PutMapping("{code}")
	@PreAuthorize("authentication.name eq #username")
	ModificationResult<LedgerPk> update(
			@PathVariable String username, 
			@RequestBody @Validated LedgerForm form) {
		return service.update(username, form);
	}
}
