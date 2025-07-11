package com.jdc.balance.api.member.service;

import java.util.List;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jdc.balance.api.management.output.SubscriptionPlanListItem;
import com.jdc.balance.domain.entity.SubscriptionPlan;
import com.jdc.balance.domain.entity.SubscriptionPlan_;
import com.jdc.balance.domain.repo.SubscriptionPlanRepo;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@PreAuthorize("hasAuthority('Member')")
public class SubscriptionPlanService {
	
	private final SubscriptionPlanRepo repo;

	@Transactional(readOnly = true)
	public List<SubscriptionPlanListItem> getAvailableServices() {
		return repo.search(cb -> {
			var cq = cb.createQuery(SubscriptionPlanListItem.class);
			var root = cq.from(SubscriptionPlan.class);
			SubscriptionPlanListItem.select(cb, cq, root);
			cq.where(cb.isTrue(root.get(SubscriptionPlan_.active)));
			return cq;
		});
	}

}
