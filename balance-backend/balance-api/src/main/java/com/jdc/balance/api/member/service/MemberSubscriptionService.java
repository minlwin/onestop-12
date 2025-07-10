package com.jdc.balance.api.member.service;

import static com.jdc.balance.common.utils.EntityOperations.safeCall;

import java.nio.file.Path;
import java.time.LocalDate;
import java.time.LocalDateTime;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jdc.balance.api.member.input.SubscriptionForm;
import com.jdc.balance.api.member.input.SubscriptionSearch;
import com.jdc.balance.api.member.output.SubscriptionDetails;
import com.jdc.balance.api.member.output.SubscriptionListItem;
import com.jdc.balance.domain.PageResult;
import com.jdc.balance.domain.embeddable.SubscriptionPk;
import com.jdc.balance.domain.entity.Subscription;
import com.jdc.balance.domain.entity.Subscription.Status;
import com.jdc.balance.domain.repo.MemberRepo;
import com.jdc.balance.domain.repo.PaymentMethodRepo;
import com.jdc.balance.domain.repo.SubscriptionPlanRepo;
import com.jdc.balance.domain.repo.SubscriptionRepo;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
@PreAuthorize("hasAuthority('Member')")
public class MemberSubscriptionService {
	
	private final MemberRepo memberRepo;
	private final PaymentMethodRepo paymentMethodRepo;
	private final SubscriptionPlanRepo planRepo;
	private final SubscriptionRepo subscriptionRepo;
	
	private final PaymentSlipStorageService storageService;

	@Transactional
	public SubscriptionDetails create(SubscriptionForm form, Path slipDirectory) {
		
		// Create Subscription
		var plan = safeCall(planRepo.findById(form.planId()), "Subscription Plan", form.planId());
		var paymentMethod = safeCall(paymentMethodRepo.findById(form.paymentId()), "Payment Method", form.paymentId());
		var member = safeCall(memberRepo.findByAccountEmail(form.username()), "Member", form.username());
		
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
		
		var slipFileName = storageService.save(slipDirectory, id.getCode(), form.slip());
		subscription.setPaymentSlip(slipFileName);
		subscription.setPaymentAmount(plan.getFees());
		
		subscription.setStatus(Status.Pending);
		subscription.setStatusChangeAt(LocalDateTime.now());
		
		subscription = subscriptionRepo.save(subscription);
		
		return SubscriptionDetails.from(subscription);
	}

	public SubscriptionDetails findById(String code) {
		var entity = safeCall(subscriptionRepo.findById(SubscriptionPk.from(code)), "Subscription", code);
		return SubscriptionDetails.from(entity);
	}

	public PageResult<SubscriptionListItem> search(SubscriptionSearch search, int page, int size) {
		// TODO Auto-generated method stub
		return null;
	}

}
