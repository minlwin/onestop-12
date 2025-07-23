package com.jdc.balance.api.member.service;

import static com.jdc.balance.common.utils.EntityOperations.safeCall;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jdc.balance.api.member.output.CurrentSubscriptionPlan;
import com.jdc.balance.domain.repo.MemberRepo;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class MemberProfileService {
	
	private final MemberRepo memberRepo;
	
	public CurrentSubscriptionPlan getCurrentPlan(String username) {
		return safeCall(memberRepo.findByAccountEmail(username)
			.map(CurrentSubscriptionPlan::from), "Member", username);
	}

}
