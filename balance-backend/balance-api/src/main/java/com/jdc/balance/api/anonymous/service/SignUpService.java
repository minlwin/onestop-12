package com.jdc.balance.api.anonymous.service;

import java.time.LocalDate;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.jdc.balance.api.anonymous.input.SignUpForm;
import com.jdc.balance.common.dto.ErrorMessage;
import com.jdc.balance.common.exception.ApiBusinessException;
import com.jdc.balance.common.security.promotion.PromotionPeriod;
import com.jdc.balance.common.security.promotion.PromotionPeriodService;
import com.jdc.balance.domain.entity.Account;
import com.jdc.balance.domain.entity.Account.Role;
import com.jdc.balance.domain.entity.Member;
import com.jdc.balance.domain.repo.AccountRepo;
import com.jdc.balance.domain.repo.MemberRepo;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class SignUpService {
	
	private final MemberRepo memberRepo;
	private final AccountRepo accountRepo;
	private final PasswordEncoder passwordEncoder;
	private final PromotionPeriodService promotionPeriodService;

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
		
		promotionPeriodService.getPromotionPeriod().ifPresent(promotion -> {
			if(promotion.unit() == PromotionPeriod.PeriodUnit.Day) {
				account.setExpiredAt(LocalDate.now().plusDays(promotion.value()));
			} else {
				account.setExpiredAt(LocalDate.now().plusMonths(promotion.value()));
			}
		});

		var member = new Member();
		member.setAccount(account);
		
		return memberRepo.save(member);
	}

}
