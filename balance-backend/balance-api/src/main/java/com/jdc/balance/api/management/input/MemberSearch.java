package com.jdc.balance.api.management.input;

import static com.jdc.balance.common.utils.EntityOperations.likeString;

import java.time.LocalDate;
import java.util.ArrayList;

import org.springframework.util.StringUtils;

import com.jdc.balance.domain.entity.Account_;
import com.jdc.balance.domain.entity.Member;
import com.jdc.balance.domain.entity.Member_;
import com.jdc.balance.domain.entity.SubscriptionPlan_;

import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;

public record MemberSearch(
		Integer planId,
		LocalDate expiredFrom, 
		LocalDate expiredTo, 
		String keyword) {

	public Predicate[] where(CriteriaBuilder cb, Root<Member> root) {
		
		var list = new ArrayList<Predicate>();
		
		if(null != planId) {
			list.add(cb.equal(root.get(Member_.plan)
					.get(SubscriptionPlan_.id), planId));
		}
		
		if(null != expiredFrom) {
			list.add(cb.greaterThanOrEqualTo(root.get(Member_.account)
					.get(Account_.expiredAt), expiredFrom));
		}
		
		if(null != expiredTo) {
			list.add(cb.lessThanOrEqualTo(root.get(Member_.account)
					.get(Account_.expiredAt), expiredTo));
		}
		
		if(StringUtils.hasLength(keyword)) {
			list.add(cb.or(
				cb.like(cb.lower(root.get(Member_.account).get(Account_.name)), likeString(keyword)),
				cb.like(cb.lower(root.get(Member_.account).get(Account_.email)), likeString(keyword)),
				cb.like(cb.lower(root.get(Member_.phone)), likeString(keyword))
			));
		}
		
		return list.toArray(size -> new Predicate[size]);
	}

}
