package com.jdc.balance.api.member;

import java.util.List;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.jdc.balance.api.member.output.PaymentMethodListItem;
import com.jdc.balance.api.member.service.PaymentMethodService;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RequestMapping("member/{username}/payment")
@RestController("memberPaymentMethodApi")
public class PaymentMethodApi {
	
	private final PaymentMethodService service;

	@GetMapping
	@PreAuthorize("authentication.name eq #username")
	List<PaymentMethodListItem> search(
			@PathVariable String username) {
		return service.getAvailablePayments();
	}
}
