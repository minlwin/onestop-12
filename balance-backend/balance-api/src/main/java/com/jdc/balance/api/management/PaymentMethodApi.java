package com.jdc.balance.api.management;

import java.util.List;

import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.jdc.balance.api.management.input.PaymentMethodForm;
import com.jdc.balance.api.management.input.PaymentMethodSearch;
import com.jdc.balance.api.management.output.PaymentMethodDetails;
import com.jdc.balance.api.management.output.PaymentMethodListItem;
import com.jdc.balance.api.management.service.PaymentMethodService;
import com.jdc.balance.common.dto.ModificationResult;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("management/payment")
public class PaymentMethodApi {
	
	private final PaymentMethodService service;

	@GetMapping
	List<PaymentMethodListItem> search(PaymentMethodSearch search) {
		return service.search(search);
	}
	
	@PostMapping
	ModificationResult<Integer> create(@Validated @RequestBody PaymentMethodForm form) {
		return service.create(form);
	}
	
	@PutMapping("{id}")
	ModificationResult<Integer> update(@PathVariable int id, @Validated @RequestBody PaymentMethodForm form) {
		return service.update(id, form);
	}
	
	@GetMapping("{id}")
	PaymentMethodDetails findById(@PathVariable int id) {
		return service.findById(id);
	}
}
