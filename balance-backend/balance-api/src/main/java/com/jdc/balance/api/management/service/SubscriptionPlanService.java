package com.jdc.balance.api.management.service;

import static com.jdc.balance.common.utils.EntityOperations.safeCall;

import java.util.List;
import java.util.function.Function;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jdc.balance.api.management.input.SubscriptionPlanForm;
import com.jdc.balance.api.management.input.SubscriptionPlanSearch;
import com.jdc.balance.api.management.output.SubscriptionPlanDetails;
import com.jdc.balance.api.management.output.SubscriptionPlanListItem;
import com.jdc.balance.domain.entity.SubscriptionPlan;
import com.jdc.balance.domain.entity.SubscriptionPlan_;
import com.jdc.balance.domain.repo.SubscriptionPlanRepo;

import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class SubscriptionPlanService {
	
	private static final String ENITTY_TYPE = "Subscription plan";
	
	private final SubscriptionPlanRepo repo;

	@Transactional
	public SubscriptionPlanDetails create(SubscriptionPlanForm form) {
		
		if(form.active() && form.defaultPlan()) {
			var activeDefaultPlans = repo.findByDefaultPlanAndActive(true, true);
			for(var plan : activeDefaultPlans) {
				plan.setDefaultPlan(false);
			}
		}
		
		var entity = repo.create(form.entity());
		return SubscriptionPlanDetails.from(entity);
	}

	@Transactional
	public SubscriptionPlanDetails update(int id, SubscriptionPlanForm form) {
		
		if(form.active() && form.defaultPlan()) {
			var activeDefaultPlans = repo.findByDefaultPlanAndActive(true, true);
			for(var plan : activeDefaultPlans) {
				if(id != plan.getId()) {
					plan.setDefaultPlan(false);
				}
			}
		}
		
		var entity = safeCall(repo.findById(id), ENITTY_TYPE, id);
		form.update(entity);
		return SubscriptionPlanDetails.from(entity);
	}

	public SubscriptionPlanDetails findById(int id) {
		var entity = safeCall(repo.findById(id), ENITTY_TYPE, id);
		return SubscriptionPlanDetails.from(entity);
	}

	public List<SubscriptionPlanListItem> search(SubscriptionPlanSearch search) {
		return repo.search(queryFun(search));
	}

	private Function<CriteriaBuilder, CriteriaQuery<SubscriptionPlanListItem>> queryFun(SubscriptionPlanSearch search) {
		return cb -> {
			var cq = cb.createQuery(SubscriptionPlanListItem.class);
			var root = cq.from(SubscriptionPlan.class);
			SubscriptionPlanListItem.select(cb, cq, root);
			cq.where(search.where(cb, root));
			cq.orderBy(cb.asc(root.get(SubscriptionPlan_.id)));
			return cq;
		};
	}

}
