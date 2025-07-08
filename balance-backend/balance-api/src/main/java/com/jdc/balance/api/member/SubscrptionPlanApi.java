package com.jdc.balance.api.member;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.jdc.balance.api.management.output.SubscriptionPlanListItem;
import com.jdc.balance.api.member.service.MemberSubscriptionPlanService;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RequestMapping("member/plan")
@RestController("memberSubscrptionPlanApi")
public class SubscrptionPlanApi {
	
	private final MemberSubscriptionPlanService service;

	@GetMapping
	List<SubscriptionPlanListItem> search() {
		return service.getAvailableServices();
	}
}
