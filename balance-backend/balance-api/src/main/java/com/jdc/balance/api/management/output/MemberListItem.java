package com.jdc.balance.api.management.output;

import java.time.LocalDate;

import com.jdc.balance.domain.entity.Account_;
import com.jdc.balance.domain.entity.Member;
import com.jdc.balance.domain.entity.Member_;
import com.jdc.balance.domain.entity.SubscriptionPlan_;
import com.jdc.balance.domain.entity.Subscription_;

import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Root;

public record MemberListItem(
		long id,
		String name,
		String email,
		String phone,
		String address,
		LocalDate enabledAt,
		LocalDate expiredAt,
		int planId,
		String planName) {

	public static void select(CriteriaQuery<MemberListItem> cq, Root<Member> root) {
		cq.multiselect(
			root.get(Member_.id),
			root.get(Member_.account).get(Account_.name),
			root.get(Member_.account).get(Account_.email),
			root.get(Member_.phone),
			root.get(Member_.address),
			root.get(Member_.enabledDate),
			root.get(Member_.account).get(Account_.expiredAt),
			root.get(Member_.subscription).get(Subscription_.plan).get(SubscriptionPlan_.id),
			root.get(Member_.subscription).get(Subscription_.plan).get(SubscriptionPlan_.name)
		);
	}

}
