package com.jdc.balance.api.member;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.jdc.balance.api.member.output.CurrentSubscriptionPlan;
import com.jdc.balance.api.member.service.MemberProfileService;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("member/{username}/dashboard")
public class MemberProfileApi {
	
	private final MemberProfileService profileService;
	
	@GetMapping("plan")
	@PreAuthorize("authentication.name eq #username")
	CurrentSubscriptionPlan getCurrentPlan(@PathVariable String username) {
		return profileService.getCurrentPlan(username);
	}
}
