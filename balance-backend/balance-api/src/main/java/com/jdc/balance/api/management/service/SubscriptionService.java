package com.jdc.balance.api.management.service;

import static com.jdc.balance.common.utils.EntityOperations.safeCall;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.function.Function;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jdc.balance.api.management.input.SubscriptionSearch;
import com.jdc.balance.api.management.input.SubscriptionStatusUpdateForm;
import com.jdc.balance.api.management.output.SubscriptionDetails;
import com.jdc.balance.api.management.output.SubscriptionListItem;
import com.jdc.balance.common.dto.ModificationResult;
import com.jdc.balance.domain.PageResult;
import com.jdc.balance.domain.embeddable.SubscriptionPk;
import com.jdc.balance.domain.embeddable.SubscriptionPk_;
import com.jdc.balance.domain.entity.Subscription;
import com.jdc.balance.domain.entity.Subscription.Status;
import com.jdc.balance.domain.entity.Subscription.Usage;
import com.jdc.balance.domain.entity.Subscription_;
import com.jdc.balance.domain.repo.SubscriptionRepo;

import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
@PreAuthorize("hasAuthority('Admin')")
public class SubscriptionService {

	private final SubscriptionRepo repo;
	
	@Transactional
	public ModificationResult<SubscriptionPk> update(String code, SubscriptionStatusUpdateForm form) {
		
		var entity = safeCall(repo.findById(SubscriptionPk.from(code)), "Subscription", code);

		entity.setStatus(form.status());
		entity.setReason(form.message());
		entity.setStatusChangeAt(LocalDateTime.now());
		
		// Handle Expired At Value
		if((form.status() == Status.Approved && entity.getUsage() == Usage.Urgent) || 
				(form.status() == Status.Approved && entity.getMember().getAccount().getExpiredAt().isBefore(LocalDate.now())) ) {
			var member = entity.getMember();
			member.setSubscription(entity);
			entity.setStartAt(LocalDate.now());
			member.getAccount().setExpiredAt(LocalDate.now().plusMonths(entity.getPlan().getMonths()));
		}
		
		return ModificationResult.success(entity.getId());
	}

	public SubscriptionDetails findById(String code) {
		var entity = safeCall(repo.findById(SubscriptionPk.from(code)), "Subscription", code);
		return SubscriptionDetails.from(entity);
	}

	public PageResult<SubscriptionListItem> search(SubscriptionSearch search, int page, int size) {
		return repo.search(queryFunc(search), countFunc(search), page, size);
	}
	
	private Function<CriteriaBuilder, CriteriaQuery<SubscriptionListItem>> queryFunc(SubscriptionSearch search) {
		return cb -> {
			var cq = cb.createQuery(SubscriptionListItem.class);
			var root = cq.from(Subscription.class);
			SubscriptionListItem.select(cq, root);
			cq.where(search.where(cb, root));
			cq.orderBy(cb.desc(root.get(Subscription_.id).get(SubscriptionPk_.appliedAt)));
			return cq;
		};
	}

	private Function<CriteriaBuilder, CriteriaQuery<Long>> countFunc(SubscriptionSearch search) {
		return cb -> {
			var cq = cb.createQuery(Long.class);
			var root = cq.from(Subscription.class);
			cq.where(search.where(cb, root));
			cq.select(cb.count(root.get(Subscription_.id)));
			return cq;
		};
	}
	

}
