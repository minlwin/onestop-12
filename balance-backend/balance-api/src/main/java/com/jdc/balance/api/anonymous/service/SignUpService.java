package com.jdc.balance.api.anonymous.service;

import java.time.LocalDate;
import java.time.LocalDateTime;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.jdc.balance.api.anonymous.input.SignUpForm;
import com.jdc.balance.common.dto.ErrorMessage;
import com.jdc.balance.common.exception.ApiBusinessException;
import com.jdc.balance.domain.embeddable.SubscriptionPk;
import com.jdc.balance.domain.entity.Account;
import com.jdc.balance.domain.entity.Account.Role;
import com.jdc.balance.domain.entity.Member;
import com.jdc.balance.domain.entity.Subscription;
import com.jdc.balance.domain.entity.Subscription.Status;
import com.jdc.balance.domain.entity.Subscription.Usage;
import com.jdc.balance.domain.repo.AccountRepo;
import com.jdc.balance.domain.repo.MemberRepo;
import com.jdc.balance.domain.repo.SubscriptionPlanRepo;
import com.jdc.balance.domain.repo.SubscriptionRepo;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class SignUpService {
	
	private final MemberRepo memberRepo;
	private final AccountRepo accountRepo;
	private final SubscriptionRepo subscriptionRepo;
	private final PasswordEncoder passwordEncoder;
	
	private final SubscriptionPlanRepo planRepo;

	@Transactional
	public Member signUp(SignUpForm form) {
		
		if(accountRepo.findById(form.email()).isPresent()) {
			throw new ApiBusinessException(new ErrorMessage("email", "Your email has been already used."));
		}
		
		var defaultPlans = planRepo.findByDefaultPlanAndActive(true, true);
		if(defaultPlans.isEmpty()) {
			throw new ApiBusinessException(ErrorMessage.withMessage("The system is not available at this moment. Please try again later."));
		}

		var account = new Account();
		account.setName(form.name());
		account.setEmail(form.email());
		account.setPassword(passwordEncoder.encode(form.password()));
		account.setRole(Role.Member);
		account.setExpiredAt(LocalDate.now());
		
		var member = new Member();
		member.setAccount(account);
		member.setEnabledDate(LocalDate.now());
		member = memberRepo.create(member);
		
		var defaultPlan = defaultPlans.getFirst();
		account.setExpiredAt(LocalDate.now().plusMonths(defaultPlan.getMonths()));
		
		var pk = new SubscriptionPk();
		pk.setAppliedAt(LocalDate.now());
		pk.setPlanId(defaultPlan.getId());
		pk.setMemberId(member.getId());
		var subscription = new Subscription();
		
		subscription.setId(pk);
		subscription.setMember(member);
		subscription.setPlan(defaultPlan);
		subscription.setUsage(Usage.Urgent);
		
		subscription.setStartAt(LocalDate.now());
		subscription.setExpiredAt(LocalDate.now().plusMonths(defaultPlan.getMonths()));

		subscription.setStatus(Status.Approved);
		subscription.setStatusChangeAt(LocalDateTime.now());
		
		subscription = subscriptionRepo.create(subscription);
		member.setSubscription(subscription);
		
		return member;
	}

}
