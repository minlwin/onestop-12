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
import com.jdc.balance.domain.repo.MemberRepo;

@Aspect
@Configuration
public class LimitValidationAspects {
	
	@Autowired
	private MemberRepo memberRepo;
	@Autowired
	private LedgerEntryRepo entryRepo;
	
	@Pointcut("within(com.jdc.balance.api.*.service.*)")
	public void serviceClasses() {}

	@Pointcut("@annotation(com.jdc.balance.common.limit.LimitEntry)")
	public void requireEntryLimit() {}
	
	@Pointcut("@annotation(com.jdc.balance.common.limit.LimitLedger)")
	public void requireLedgerLimit() {}
	
	@Before("serviceClasses() && requireEntryLimit()")
	public void checkEntryLimit() {
		var member = getMember();
		var plan = member.getPlan();
		
		// Check Daily Limit
		var dailyEntry = entryRepo.countForCheck(member.getId(), LocalDate.now());
		if(plan.getDailyEntry() <= dailyEntry) {
			throw new ApiBusinessException(ErrorMessage.withMessage(""));
		}
		
		// Check Monthly Limit
		var monthlyEntry = entryRepo.countForCheck(member.getId(), LocalDate.now().minusMonths(1));
		if(plan.getMonthlyEntry() <= monthlyEntry) {
			throw new ApiBusinessException(ErrorMessage.withMessage(""));
		}		
	}
	
	@Before("serviceClasses() && requireLedgerLimit()")
	public void checkLedgerLimit() {
		var member = getMember();
		
	}

	private Member getMember() {
		return safeCall(memberRepo.findByAccountEmail(SecurityContextHolder.getContext().getAuthentication().getName())
				, null, null);
	}

}
