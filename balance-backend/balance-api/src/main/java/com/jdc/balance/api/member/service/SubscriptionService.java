package com.jdc.balance.api.member.service;

import static com.jdc.balance.common.utils.EntityOperations.safeCall;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.function.Function;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jdc.balance.api.member.input.SubscriptionForm;
import com.jdc.balance.api.member.input.SubscriptionSearch;
import com.jdc.balance.api.member.output.SubscriptionDetails;
import com.jdc.balance.api.member.output.SubscriptionListItem;
import com.jdc.balance.common.dto.ModificationResult;
import com.jdc.balance.domain.PageResult;
import com.jdc.balance.domain.embeddable.SubscriptionPk;
import com.jdc.balance.domain.embeddable.SubscriptionPk_;
import com.jdc.balance.domain.entity.Subscription;
import com.jdc.balance.domain.entity.Subscription.Status;
import com.jdc.balance.domain.entity.Subscription_;
import com.jdc.balance.domain.repo.MemberRepo;
import com.jdc.balance.domain.repo.PaymentMethodRepo;
import com.jdc.balance.domain.repo.SubscriptionPlanRepo;
import com.jdc.balance.domain.repo.SubscriptionRepo;

import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import lombok.RequiredArgsConstructor;

@Service("memberSubscriptionService")
@RequiredArgsConstructor
@Transactional(readOnly = true)
@PreAuthorize("hasAuthority('Member')")
public class SubscriptionService {
	
	private final MemberRepo memberRepo;
	private final PaymentMethodRepo paymentMethodRepo;
	private final SubscriptionPlanRepo planRepo;
	private final SubscriptionRepo subscriptionRepo;
	
	private final PaymentSlipStorageService storageService;

	@Transactional
	public ModificationResult<SubscriptionPk> create(String username, SubscriptionForm form) {
		
		// Create Subscription
		var plan = safeCall(planRepo.findById(form.planId()), "Subscription Plan", form.planId());
		var paymentMethod = safeCall(paymentMethodRepo.findById(form.paymentId()), "Payment Method", form.paymentId());
		var member = safeCall(memberRepo.findByAccountEmail(username), "Member", username);
		
		var id = new SubscriptionPk();
		id.setAppliedAt(LocalDate.now());
		id.setPlanId(form.planId());
		id.setMemberId(member.getId());
		
		var subscription = new Subscription();
		subscription.setPlan(plan);
		subscription.setPayment(paymentMethod);
		subscription.setMember(member);
		subscription.setUsage(form.usage());
		subscription.setPreviousPlan(member.getPlan());
		
		var slipFileName = storageService.save(id.getCode(), form.slip());
		subscription.setPaymentSlip(slipFileName);
		subscription.setPaymentAmount(plan.getFees());
		
		subscription.setStatus(Status.Pending);
		subscription.setStatusChangeAt(LocalDateTime.now());
		
		subscription = subscriptionRepo.save(subscription);
		
		return ModificationResult.success(subscription.getId());
	}

	public SubscriptionDetails findById(String code) {
		var entity = safeCall(subscriptionRepo.findById(SubscriptionPk.from(code)), "Subscription", code);
		return SubscriptionDetails.from(entity);
	}

	public PageResult<SubscriptionListItem> search(String username, SubscriptionSearch search, int page, int size) {
		return subscriptionRepo.search(queryFunc(search, username), countFunc(search, username), page, size);
	}

	private Function<CriteriaBuilder, CriteriaQuery<SubscriptionListItem>> queryFunc(SubscriptionSearch search, String username) {
		return cb -> {
			var cq = cb.createQuery(SubscriptionListItem.class);
			
			var root = cq.from(Subscription.class);
			SubscriptionListItem.select(cq, root);
			cq.where(search.where(cb, root, username));
			
			cq.orderBy(
				cb.desc(root.get(Subscription_.id).get(SubscriptionPk_.appliedAt)), 
				cb.asc(root.get(Subscription_.id).get(SubscriptionPk_.planId))
			);
			
			return cq;
		};
	}

	private Function<CriteriaBuilder, CriteriaQuery<Long>> countFunc(SubscriptionSearch search, String username) {
		return cb -> {
			var cq = cb.createQuery(Long.class);
			
			var root = cq.from(Subscription.class);
			cq.where(search.where(cb, root, username));
			cq.select(cb.count(root.get(Subscription_.id)));
			
			return cq;
		};
	}
}
