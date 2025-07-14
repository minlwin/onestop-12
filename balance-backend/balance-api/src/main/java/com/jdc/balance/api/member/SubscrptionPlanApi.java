package com.jdc.balance.api.member;

import java.util.List;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.jdc.balance.api.member.output.SubscriptionPlanListItem;
import com.jdc.balance.api.member.service.SubscriptionPlanService;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RequestMapping("member/{username}/plan")
@RestController("memberSubscrptionPlanApi")
public class SubscrptionPlanApi {
	
	private final SubscriptionPlanService service;

	@GetMapping
	@PreAuthorize("authentication.name eq #username")
	List<SubscriptionPlanListItem> search(@PathVariable String username) {
		return service.getAvailableServices();
	}
}
