package com.jdc.balance.common.limit;

import static com.jdc.balance.common.utils.EntityOperations.safeCall;

import java.time.LocalDate;

import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.context.SecurityContextHolder;

import com.jdc.balance.common.dto.ErrorMessage;
import com.jdc.balance.common.exception.ApiBusinessException;
import com.jdc.balance.domain.entity.Member;
import com.jdc.balance.domain.repo.LedgerEntryRepo;
import com.jdc.balance.domain.repo.LedgerRepo;
import com.jdc.balance.domain.repo.MemberRepo;

@Aspect
@Configuration
public class LimitValidationAspects {
	
	@Autowired
	private MemberRepo memberRepo;
	@Autowired
	private LedgerEntryRepo entryRepo;
	@Autowired
	private LedgerRepo ledgerRepo;
	
	@Pointcut("within(com.jdc.balance.api.*.service.*)")
	public void serviceClasses() {}

	@Pointcut("@annotation(com.jdc.balance.common.limit.LimitEntry)")
	public void requireEntryLimit() {}
	
	@Pointcut("@annotation(com.jdc.balance.common.limit.LimitLedger)")
	public void requireLedgerLimit() {}
	
	@Before("serviceClasses() && requireEntryLimit()")
	public void checkEntryLimit() {
		var member = getMember();
		var plan = member.getSubscription().getPlan();
		
		// Check Expiration
		if(LocalDate.now().isAfter(member.getAccount().getExpiredAt())) {
			throw new ApiBusinessException(ErrorMessage.withMessage("Your account has expired. Please renew your plan."));
		}
		
		// Check Daily Limit
		var dailyEntry = entryRepo.countForCheck(member.getId(), LocalDate.now());
		if(plan.getDailyEntry() <= dailyEntry) {
			throw new ApiBusinessException(ErrorMessage.withMessage("You’ve reached your daily limit. Please try again tomorrow."));
		}
		
		// Check Monthly Limit
		var monthlyEntry = entryRepo.countForCheck(member.getId(), LocalDate.now().minusMonths(1));
		if(plan.getMonthlyEntry() <= monthlyEntry) {
			throw new ApiBusinessException(ErrorMessage.withMessage("You’ve reached your monthly limit. Please consider about next plan."));
		}		
	}
	
	@Before("serviceClasses() && requireLedgerLimit()")
	public void checkLedgerLimit() {
		var member = getMember();
		
		var plan = member.getSubscription().getPlan();
		
		// Check Expiration
		if(LocalDate.now().isAfter(member.getAccount().getExpiredAt())) {
			throw new ApiBusinessException(ErrorMessage.withMessage("Your account has expired. Please renew your plan."));
		}
		
		// Check Ledger Limit		
		var ledgerCount = ledgerRepo.countForCheck(member.getId());
		
		if(ledgerCount >= plan.getMaxLedgers()) {
			throw new ApiBusinessException(ErrorMessage.withMessage("You’ve reached your ledger limit. Please consider about next plan."));
		}
	}

	private Member getMember() {
		return safeCall(memberRepo.findByAccountEmail(SecurityContextHolder.getContext().getAuthentication().getName())
				, null, null);
	}

}
