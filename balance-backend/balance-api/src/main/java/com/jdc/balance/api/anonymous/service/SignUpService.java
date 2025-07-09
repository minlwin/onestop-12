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
		
		var account = new Account();
		account.setName(form.name());
		account.setEmail(form.email());
		account.setPassword(passwordEncoder.encode(form.password()));
		account.setRole(Role.Member);
		account.setExpiredAt(LocalDate.now());
		
		var member = new Member();
		member.setAccount(account);
		member.setEnabledDate(LocalDate.now());
		
		var defaultPlans = planRepo.findByDefaultPlanAndActive(true, true);
		
		if(!defaultPlans.isEmpty()) {
			var defaultPlan = defaultPlans.getFirst();
			member.setPlan(defaultPlan);
			account.setExpiredAt(LocalDate.now().plusMonths(defaultPlan.getMonths()));
			
			var pk = new SubscriptionPk();
			pk.setAppliedAt(LocalDate.now());
			pk.setPlanId(defaultPlan.getId());

			var subscription = new Subscription();
			subscription.setId(pk);
			subscription.setMember(member);
			subscription.setPlan(defaultPlan);
			
			subscription.setStartAt(LocalDate.now());
			subscription.setStatus(Status.Approved);
			subscription.setStatusChangeAt(LocalDateTime.now());
			
			subscriptionRepo.create(subscription);
		}
		
		return memberRepo.create(member);
	}

}
