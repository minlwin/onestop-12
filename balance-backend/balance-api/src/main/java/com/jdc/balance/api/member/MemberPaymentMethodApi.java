package com.jdc.balance.api.member;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.jdc.balance.api.member.output.PaymentMethodListItem;
import com.jdc.balance.api.member.service.MemberPaymentMethodService;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("member/payment")
public class MemberPaymentMethodApi {
	
	private final MemberPaymentMethodService service;

	@GetMapping
	List<PaymentMethodListItem> search() {
		return service.getAvailablePayments();
	}
}
